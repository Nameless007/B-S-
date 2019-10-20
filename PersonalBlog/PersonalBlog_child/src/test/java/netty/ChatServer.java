package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

//聊天程序服务器端
public class ChatServer {
    private int port; //服务器端端口号

    public ChatServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
//                            ChannelPipeline pipeline = ch.pipeline(); //得到 Pipeline 链
////                            //往 Pipeline 链中添加一个解码器
////                            pipeline.addLast("decoder", new StringDecoder());
////                            //往 Pipeline 链中添加一个编码器
////                            pipeline.addLast("encoder", new StringEncoder());
////                            //往 Pipeline 链中添加一个自定义的业务处理对象
////                            pipeline.addLast("handler", new ChatServerHandler());
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("http-codec", new HttpServerCodec());
                            pipeline.addLast("aggregator", new HttpObjectAggregator(65535));
                            pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                            pipeline.addLast("handler", new WebSocketHandler());
//                            pipeline.addLast("handler2", new ChatServerHandler());

                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)      //设置线程队列中等待连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true);  //保持活动连接状态
            System.out.println("Netty Chat Server 启动......");
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            System.out.println("Netty Chat Server 关闭......");
        }
    }

    public static void main(String[] args) throws Exception {
        new ChatServer(18000).run();
    }
}