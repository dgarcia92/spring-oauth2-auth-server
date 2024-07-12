package com.cotsoft.security.auth_server.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "clients")
@Getter
@Setter
@ToString
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String appName;

    @Column(nullable = false, unique = true, length = 60)
    private String clientId;

    @Column(nullable = false, length = 100)
    private String clientSecret;

    @Column(nullable = false)
    private String redirectUri;

    @Column(nullable = false)
    private String callbackUri;

    private String scope;
    private String description;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Boolean isActive = true;

}
