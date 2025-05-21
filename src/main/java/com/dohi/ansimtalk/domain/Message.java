package com.dohi.ansimtalk.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Message {
//@Generated~~ -> key값 db에 위임함 ㅇㅇ
    @Id @GeneratedValue
    @Column(name = "message_id")
    private Long id;
//Long-> DB에서는 bigint -> int 보다 4byte많아서 관리 수월 (용량차이는 10%)
    private Long sendid;//보내는사람
    private Long receiveid;//받는 사람
    //메세지 타입 -> text
     private String mtype;
    private String messagetext;

}
