package com.jacklin.haoke.im.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacklin.haoke.im.dao.MessageDAO;
import com.jacklin.haoke.im.pojo.Message;
import com.jacklin.haoke.im.pojo.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author laiqilin
 * @version 1.0
 * @date 2019/10/29 下午2:51
 * webSocket处理器，处理消息
 */
@Component
public class MessageHandler extends TextWebSocketHandler {
    @Autowired
    private MessageDAO messageDAO;

    private static final ObjectMapper MAPPER = new ObjectMapper();
    //用户存放用户的在线session
    public static final Map<Long, WebSocketSession> SESSION_MAP = new HashMap<>();


    /**
     * 开启连接
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 将当前用户的session放置到map中，后面会使用相应的session通信
        Long uid = (Long) session.getAttributes().get("uid");
        SESSION_MAP.put(uid, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        Long uid = (Long) session.getAttributes().get("uid");
        //将消息序列化成JSON串
        JsonNode jsonNode = MAPPER.readTree(textMessage.getPayload());
        Long toId = jsonNode.get("toId").asLong();
        String msg = jsonNode.get("msg").asText();

        //构造消息对象
        Message message = Message.builder()
                .from(UserData.USER_MAP.get(uid))
                .to(UserData.USER_MAP.get(toId))
                .msg(msg)
                .build();
        //将消息保存到mongodb数据库中
        message = this.messageDAO.saveMessage(message);
        //判断to用户是否在线
        WebSocketSession socketSession = SESSION_MAP.get(toId);
        if (socketSession != null && socketSession.isOpen()) {
            //TODO 根据前端的格式对接
            //响应消息
            socketSession.sendMessage(new TextMessage(MAPPER.writeValueAsString(message)));
            //更改消息状态
            this.messageDAO.updateMessageState(message.getId(), 2);
        }
    }

    /**
     * 断开连接
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long uid = (Long) session.getAttributes().get("uid");
        //用户断开移除用户Session
        SESSION_MAP.remove(uid);
    }
}
