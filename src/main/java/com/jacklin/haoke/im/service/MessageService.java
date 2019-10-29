package com.jacklin.haoke.im.service;

import com.jacklin.haoke.im.dao.MessageDAO;
import com.jacklin.haoke.im.pojo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author laiqilin
 * @version 1.0
 * @date 2019/10/29 下午7:14
 */
@Service
public class MessageService {

    @Autowired
    private MessageDAO messageDAO;

    /**
     * 查询消息列表
     *
     * @param fromId
     * @param toId
     * @param page
     * @param rows
     * @return
     */
    public List<Message> queryMessageList(Long fromId, Long toId, Integer page, Integer rows) {
        List<Message> listByFromAndTo = this.messageDAO.findListByFromAndTo(fromId, toId, page, rows);
        listByFromAndTo.forEach(message -> {
            if (message.getStatus() == 1) {
                //修改信息为已读状态
                this.messageDAO.updateMessageState(message.getId(), 2);
            }
        });
        return listByFromAndTo;
    }
}
