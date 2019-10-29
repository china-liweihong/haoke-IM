package com.jacklin.haoke.im.dao;

import com.jacklin.haoke.im.pojo.Message;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author laiqilin
 * @version 1.0
 * @date 2019/10/29 上午11:13
 */
public interface MessageDAO {


    /**
     * 查询点对点聊天记录
     *
     * @param fromId
     * @param toId
     * @param page
     * @param rows
     * @return
     */
    List<Message> findListByFromAndTo(Long fromId, Long toId, Integer page, Integer
            rows);

    /**
     * 根据id查询数据
     *
     * @param id
     * @return
     */
    Message findMessageById(String id);

    /**
     * 更新消息状态
     *
     * @param id
     * @param status
     * @return
     */
    UpdateResult updateMessageState(ObjectId id, Integer status);

    /**
     * 新增消息
     *
     * @param message
     * @return
     */
    Message saveMessage(Message message);


    /**
     * 根据id删除消息
     *
     * @param id
     * @return
     */
    DeleteResult deleteMessage(String id);

}
