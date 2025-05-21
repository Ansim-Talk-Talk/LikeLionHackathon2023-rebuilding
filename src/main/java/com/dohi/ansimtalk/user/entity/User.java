package com.dohi.ansimtalk.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    //피보호자 보호자
    private String type;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    public enum UserRole {
        WAIT,
        PROTECTOR, //보호자
        PROTECTED  //피보호자
    }
}
