package com.dohi.ansimtalk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
//@Generated~~ -> key값 db에 위임함 ㅇㅇ
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
//Long-> DB에서는 bigint -> int 보다 4byte많아서 관리 수월 (용량차이는 10%)
    private String name;
    //피보호자 보호자
    private String type;
//phone-> sql -> 고유번호() -> 존재할경우 -> 바로 로그인 ()
    private String phone;
//-> null -> X  연결하는곳으로 가고 -> nullX -> 로그인 되게
//p_id 있으면 type 피보호자 -> p_id(보호자 ) if
    //보호자는 p_id에 자신의 id가 등록이 될경우 그 둥록한 id값을 p_id로 저장 (코드아래에
    private Long partner;
    //랜덤 코드
    private String code;


}
