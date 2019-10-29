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
import org.springframework.stereotype.Component;

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
        return template.find(null, Message.class);
    }

    @Override
    public Message findMessageById(String id) {
        return null;
    }

    @Override
    public UpdateResult updateMessageState(ObjectId id, Integer status) {
        return null;
    }

    @Override
    public Message saveMessage(Message message) {
        return null;
    }

    @Override
    public DeleteResult deleteMessage(String id) {
        return null;
    }
}
