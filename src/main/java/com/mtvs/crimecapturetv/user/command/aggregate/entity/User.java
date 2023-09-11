package com.mtvs.crimecapturetv.user.command.aggregate.entity;

import com.mtvs.crimecapturetv.global.common.entity.BaseEntity;
import com.mtvs.crimecapturetv.user.command.aggregate.entity.enumtype.UserRole;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "User_TB")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no")
    private Long No;

    @Column(name = "name")
    private String name;

    @Column(name = "id")
    private String id;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
