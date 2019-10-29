package com.jacklin.haoke.im.controller;

import com.jacklin.haoke.im.pojo.Message;
import com.jacklin.haoke.im.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author laiqilin
 * @version 1.0
 * @date 2019/10/29 下午7:14
 * 消息服务
 */
@RestController
@RequestMapping("message")
@CrossOrigin
public class MessageController {


    @Autowired
    private MessageService messageService;

    /**
     * 拉取消息列表
     *
     * @param fromId
     * @param toId
     * @param page
     * @param rows
     * @return
     */
    @GetMapping
    public List<Message> queryMessageList(@RequestParam("fromId") Long fromId,
                                          @RequestParam("toId") Long toId,
                                          @RequestParam(value = "page",
                                                  defaultValue = "1") Integer page,
                                          @RequestParam(value = "rows",
                                                  defaultValue = "10") Integer rows) {
        return this.messageService.queryMessageList(fromId, toId, page, rows);
    }
}
