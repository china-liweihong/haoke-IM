package com.jacklin.haoke.im.dao.impl;

import com.jacklin.haoke.im.dao.MessageDAO;
import com.jacklin.haoke.im.pojo.Message;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author laiqilin
 * @version 1.0
 * @date 2019/10/29 上午11:16
 */
@Component
public class MessageDAOImpl implements MessageDAO {

    @Autowired
    private MongoTemplate template;

    /**
     * 点到点消息
     *
     * @param fromId
     * @param toId
     * @param page
     * @param rows
     * @return
     */
    @Override
    public List<Message> findListByFromAndTo(Long fromId, Long toId, Integer page, Integer rows) {
        //formId到toId
        Criteria fromList = Criteria.where("from.id").is(fromId).and("to.id").is(toId);
        //toId到fromId
        Criteria toList = Criteria.where("from.id").is(toId).and("to.id").is(fromId);
        Criteria criteria = new Criteria().orOperator(fromList, toList);
        PageRequest pageRequest = PageRequest.of(page - 1, rows,
                Sort.by(Sort.Direction.ASC, "send_date"));

        Query query = new Query(criteria).with(pageRequest);
        return template.find(query, Message.class);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Message findMessageById(String id) {
        return this.template.findById(new ObjectId(id), Message.class);
    }

    /**
     * 根据id更新会话是否已读状态
     *
     * @param id
     * @param status
     * @return
     */
    @Override
    public UpdateResult updateMessageState(ObjectId id, Integer status) {

        Query query = Query.query(Criteria.where("id").is(id));
        Update update = Update.update("status", status);
        //更新发送时间
        if (status.intValue() == 1) {
            update.set("send_date", new Date());
            //更新阅读时间
        } else if (status.intValue() == 2) {
            update.set("read_date", new Date());
        }
        return this.template.updateFirst(query, update, Message.class);
    }

    /**
     * 保存发出的消息
     *
     * @param message
     * @return
     */
    @Override
    public Message saveMessage(Message message) {
        message.setId(ObjectId.get());
        message.setSendDate(new Date());
        message.setStatus(1);
        return this.template.save(message);
    }

    /**
     * 根据id删除消息
     *
     * @param id
     * @return
     */
    @Override
    public DeleteResult deleteMessage(String id) {
        return this.template.remove(Query.query(Criteria.where("id").is(id)), Message.class);
    }
}
