package com.jacklin.haoke.im.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * @author laiqilin
 * @version 1.0
 * @date 2019/10/29 上午10:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "message") //默认数据库的表名是类名首字符小写，指定后就使用指定的字符串
public class Message {

    @Id
    private ObjectId id;

    private String msg;
    /**
     * 1-未读 2 - 已读
     */
    @Indexed //添加索引
    private Integer status;

    @Field("send_date") //指定mongodb数据库字段名
    @Indexed
    private Date sendDate;
    @Field("read_date")
    private Date readDate;
    @Indexed
    private User from;
    @Indexed
    private User to;
}
