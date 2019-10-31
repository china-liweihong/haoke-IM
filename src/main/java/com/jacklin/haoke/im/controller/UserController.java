package com.jacklin.haoke.im.controller;

import com.jacklin.haoke.im.pojo.Message;
import com.jacklin.haoke.im.pojo.User;
import com.jacklin.haoke.im.pojo.UserData;
import com.jacklin.haoke.im.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author laiqilin
 * @version 1.0
 * @date 2019/10/29 下午7:21
 * 前端模拟用户
 */
@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    private MessageService messageService;


    /**
     * 查询用户列表
     * @param fromId
     * @return
     */
    @GetMapping
    public List<Map<String, Object>> queryUserList(@RequestParam("fromId") Long
                                                           fromId) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Long, User> userEntry : UserData.USER_MAP.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", userEntry.getValue().getId());
            map.put("avatar", "http://haoke-images.58mlm.cn/haoke-images/2019/10/29/head.jpg!style");
            map.put("from_user", fromId);
            map.put("info_type", null);
            map.put("to_user", map.get("id"));
            map.put("username", userEntry.getValue().getUsername());
            // 获取最后一条消息
            List<Message> messages = this.messageService.queryMessageList(fromId,
                    userEntry.getValue().getId(), 1, 1);
            if (messages != null && !messages.isEmpty()) {
                Message message = messages.get(0);
                map.put("chat_msg", message.getMsg());
                map.put("chat_time", message.getSendDate().getTime());
            }
            result.add(map);
        }
        return result;
    }


}
