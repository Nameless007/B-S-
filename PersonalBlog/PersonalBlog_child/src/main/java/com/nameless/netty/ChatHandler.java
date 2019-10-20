package com.nameless.netty;

import com.alibaba.fastjson.JSON;
import com.nameless.model.Record;
import com.nameless.model.vo.NameAndMsg;
import com.nameless.service.RecordService;
import com.nameless.util.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;


/**
 * 处理消息的handler
 * TextWebSocketFrame: 在netty中，是用于为websocket专门处理文本的对象，frame是消息的载体
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    // 用来保存所有的客户端连接
    private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:MM");

    // 当Channel中有新的事件消息会自动调用
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 当接收到数据后会自动调用

        // 获取客户端发送过来的文本消息
        String text = msg.text();
        System.out.println("接收到消息数据为：" + text);

        Message message = JSON.parseObject(text, Message.class);

        // 通过SpringUtil工具类获取Spring上下文容器
        RecordService recordService = SpringUtil.getBean(RecordService.class);

        switch (message.getType()) {
            // 处理客户端连接的消息
            case 0:
                // 建立用户与通道的关联
                String userid = message.getRecord().getUserid();
                UserChannelMap.put(userid, ctx.channel());
                System.out.println("建立用户:" + userid + "与通道" + ctx.channel().id() + "的关联");
                UserChannelMap.print();
                break;
            // 处理客户端发送好友消息
            case 1:
                System.out.println("接收到用户消息");
                // 将聊天消息保存到数据库
                Record record = message.getRecord();
                record = recordService.addRecord(record);
                if (record==null){
                    System.out.println("消息写入数据库失败！！！");
                }
                // 如果发送消息好友在线，可以直接将消息发送给好友
                Channel channel = UserChannelMap.get(record.getFriendid());
                if(channel != null) {
                    NameAndMsg vo = new NameAndMsg();
                    vo.setMsg(record.getMessage());
                    vo.setName(record.getUserid());
                    vo.setId(record.getId());
                    channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(vo)));
                }
                else {
                    // 如果不在线，暂时不发送
                    System.out.println("用户" + record.getFriendid() + "不在线");
                }
                break;
            // 处理客户端的签收消息
            case 2:
                // 将消息记录设置为已读
                recordService.updateStatusHasRead(message.getRecord().getId());
                break;
            case 3:
                // 接收心跳消息
                System.out.println("接收到心跳消息:" + JSON.toJSONString(message));
                break;
        }

    }

    // 当有新的客户端连接服务器之后，会自动调用这个方法
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        // 将新的通道加入到clients
        clients.add(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        UserChannelMap.removeByChannelId(ctx.channel().id().asLongText());
        ctx.channel().close();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("关闭通道");
        UserChannelMap.removeByChannelId(ctx.channel().id().asLongText());
        UserChannelMap.print();
    }
}