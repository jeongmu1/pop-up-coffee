package com.db8.popupcoffee.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationInfo {

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
}
