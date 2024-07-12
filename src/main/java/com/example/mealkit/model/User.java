package com.example.mealkit.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userIdx;

    private String name;
    private String userId;
    private String pwd;
    private String email;
    private String phone;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;
}
