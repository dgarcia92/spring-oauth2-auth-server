package com.cotsoft.security.auth_server.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClientDTO {
    private String appName;
    private String homepage;
    private String appDescription;
    private String callbackUrl;
    private String[] scope;
}
