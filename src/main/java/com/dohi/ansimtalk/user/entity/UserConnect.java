package com.dohi.ansimtalk.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter

@AllArgsConstructor
@NoArgsConstructor
public class UserConnect {

    @Id
    @GeneratedValue
    private Long  id;

    @OneToOne
    @JoinColumn(nullable = false, name = "prorecter_user")
    private User prorecterUser;

    @OneToOne
    @JoinColumn(nullable = false, name = "prorected_user")
    private User prorectedUser;

}
