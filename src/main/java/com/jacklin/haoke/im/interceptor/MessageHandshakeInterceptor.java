package com.jacklin.haoke.im.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author laiqilin
 * @version 1.0
 * @date 2019/10/29 下午3:12
 */

@Component
public class MessageHandshakeInterceptor implements HandshakeInterceptor {
    /**
     * 握手前
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        System.out.println("----------开始握手！------------");
        //处理请求路径为 ---> /ws/{uid}
        String path = serverHttpRequest.getURI().getPath();
        String[] split = StringUtils.split(path, "/");
        //请求路径切割后不等于2的排除掉
        if (split.length != 2) {
            return false;
        }
        //第二个参数不是数字排除掉
        if (!StringUtils.isNumeric(split[1])) {
            return false;
        }
        map.put("uid", Long.valueOf(split[1]));
        return true;

    }

    /**
     * 握手后
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("----------握手完成！------------");
    }
}
