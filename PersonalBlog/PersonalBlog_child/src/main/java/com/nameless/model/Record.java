package com.nameless.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;


/**
 * 消息记录
 */
@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name="record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String userid;

    private String friendid;

    private Integer  haveread;

    private Date createtime;

    private Integer havedelete;

    private String message;
}
