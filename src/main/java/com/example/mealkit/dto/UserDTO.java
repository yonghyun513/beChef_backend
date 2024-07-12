package com.example.mealkit.dto;

import com.example.mealkit.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String userId;
    private String pwd;
    private String email;
    private String phone;
    private String address;
    private Role role;
}
