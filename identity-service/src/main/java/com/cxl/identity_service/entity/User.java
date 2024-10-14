package com.cxl.identity_service.entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
//    @Column(name = "userName",unique = true,columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")// độc quyền k phân biệt chữ hoa chữ thường
    String userName;
    String passWord;
    String firstName;
    String lastName;
    LocalDate dbo;

    @ManyToMany
    Set<Role> roles;
}
