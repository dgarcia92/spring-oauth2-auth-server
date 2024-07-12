package com.cotsoft.security.auth_server.services;

import com.cotsoft.security.auth_server.entities.ClientEntity;
import com.cotsoft.security.auth_server.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Primary
public class CustomRegisteredClientRepository  implements RegisteredClientRepository {

    private final ClientRepository clientRepository;


    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String clientId) {
       return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {

        System.out.println("Client ID: " + clientId);

        ClientEntity clientEntity = clientRepository.findByClientId(clientId);

        if (clientEntity == null) {
            throw new IllegalArgumentException("Client not found: " + clientId);
        }

        if (!clientEntity.getIsActive()) {
            throw new IllegalArgumentException("Client is not active: " + clientId);
        }


        System.out.println(clientEntity.toString());

        if (clientEntity.getType().equals("AUTHORIZATION_CODE")){

            return RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId(clientEntity.getClientId())
                    .clientSecret(clientEntity.getClientSecret())
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .redirectUri(clientEntity.getRedirectUri())
                    //.redirectUri("http://127.0.0.1:8080/authorized")  //http://127.0.0.1:8080/login/oauth2/code/client-app
                    .postLogoutRedirectUri("http://127.0.0.1:8080/")
                    .scope("read")
                    .scope("write")
                    .scope(OidcScopes.OPENID)
                    .scope(OidcScopes.PROFILE)
                    .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                    .build();

        }
        else if (clientEntity.getType().equals("CLIENT_CREDENTIALS")) {

            return RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId(clientEntity.getClientId())
                    .clientSecret(clientEntity.getClientSecret())
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .scope("read")
                    .scope("write")
                    .scope(OidcScopes.OPENID)
                    .scope(OidcScopes.PROFILE)
                    .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                    .build();
        }

        return null;
    }
}
