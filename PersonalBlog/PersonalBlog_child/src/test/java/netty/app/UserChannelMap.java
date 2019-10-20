package netty.app;



import io.netty.channel.Channel;


import java.util.HashMap;



public class UserChannelMap {
    public static HashMap<String, Channel> userChannelMap = new HashMap<>();
    public static void put(String userid, Channel channel) {
        userChannelMap.put(userid, channel);
    }
    public static Channel get(String userid) {
        return userChannelMap.get(userid);
    }

    /**
     *  根据通道 id 移除用户与 channel 的关联
     * @param channelId  通道的 id
     */
    public static void removeByChannelId(String channelId) {
//        if(!StringUtils.isNotBlank(channelId)) {
//            return;
//        }
        for (String s : userChannelMap.keySet()) {
            Channel channel = userChannelMap.get(s);
            if(channelId.equals(channel.id().asLongText())) {
                System.out.println(" 客户端连接断开 , 取消用户 " + s + " 与通道 " + channelId +
                        " 的关联 ");
                userChannelMap.remove(s);
                break;
            }
        }
    }

}
