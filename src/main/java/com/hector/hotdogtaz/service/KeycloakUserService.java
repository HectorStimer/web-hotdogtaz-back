package com.hector.hotdogtaz.service;

import com.hector.hotdogtaz.model.User;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeycloakUserService {

    private final Keycloak keycloak;

    @Value("${keycloak.app.realm}")
    private String realm;

    public KeycloakUserService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public void createUser(User user, String password) {
        RealmResource realmResource = keycloak.realm(realm);


        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(user.getEmail());
        keycloakUser.setEmail(user.getEmail());
        keycloakUser.setFirstName(user.getName());
        keycloakUser.setEnabled(true);
        keycloakUser.setEmailVerified(true);


        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        keycloakUser.setCredentials(List.of(credential));


        Response response = realmResource.users().create(keycloakUser);

        if (response.getStatus() != 201) {
            String body = response.readEntity(String.class);
            throw new RuntimeException("Erro ao criar usuário no Keycloak. Status: "
                    + response.getStatus() + " Body: " + body);
        }

        String keycloakUserId = response.getLocation().getPath()
                .replaceAll(".*/([^/]+)$", "$1");

        // atribui a role
        String roleName = user.getType().name(); // ADMIN ou CLERK
        RoleRepresentation role = realmResource.roles().get(roleName).toRepresentation();
        realmResource.users().get(keycloakUserId).roles().realmLevel().add(List.of(role));
    }

    public void deleteUser(String email) {
        RealmResource realmResource = keycloak.realm(realm);
        realmResource.users().search(email).forEach(u ->
                realmResource.users().get(u.getId()).remove()
        );
    }

    public void updateUserStatus(String email, boolean enabled) {
        RealmResource realmResource = keycloak.realm(realm);
        realmResource.users().search(email).forEach(u -> {
            u.setEnabled(enabled);
            realmResource.users().get(u.getId()).update(u);
        });
    }
}