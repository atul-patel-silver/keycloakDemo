package com.datmt.keycloak.springbootauth.service;

import com.datmt.keycloak.springbootauth.config.KeycloakProvider;
import com.datmt.keycloak.springbootauth.http.requests.CreateUserRequest;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.common.util.CollectionUtil;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import java.util.Collection;
import java.util.List;


@Service
public class KeycloakAdminClientService {
    @Value("${keycloak.realm}")
    public String realm;

    private final KeycloakProvider kcProvider;


    public KeycloakAdminClientService(KeycloakProvider keycloakProvider) {
        this.kcProvider = keycloakProvider;
    }

    public Response createKeycloakUser(CreateUserRequest user) {
        UserRepresentation userRepresentation = mapUserRepo(user);
        UsersResource usersResource = kcProvider.getInstance().realm(realm).users();
        Response response = usersResource.create(userRepresentation);

        if (response.getStatus() == 201) {
            //If you want to save the user to your other database, do it here, for example:
//            User localUser = new User();
//            localUser.setFirstName(kcUser.getFirstName());
//            localUser.setLastName(kcUser.getLastName());
//            localUser.setEmail(user.getEmail());
//            localUser.setCreatedDate(Timestamp.from(Instant.now()));
//            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
//            usersResource.get(userId).sendVerifyEmail();
//            userRepository.save(localUser);
        }

        return response;

    }

    private UserRepresentation mapUserRepo(CreateUserRequest user){
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getEmail());
        kcUser.setFirstName(user.getFirstname());
        kcUser.setLastName(user.getLastname());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);
        List<CredentialRepresentation> creds=new ArrayList<>();
        CredentialRepresentation cred = new CredentialRepresentation();
        cred.setTemporary(false);
        cred.setValue(user.getPassword());
        creds.add(cred);
        kcUser.setCredentials(creds);
        return kcUser;
    }

    //fetch all user

    public  List<CreateUserRequest>  getUser(){
        List<UserRepresentation> userRepresentations = kcProvider.getInstance().realm(realm).users().list();

        return mapUsers(userRepresentations);
    }

    private List<CreateUserRequest> mapUsers(List<UserRepresentation> userRepresentations) {

        List<CreateUserRequest> users=new ArrayList<>();

        if(!userRepresentations.isEmpty()){
            userRepresentations.forEach(userRepo->{
                users.add(mapUser(userRepo));
            });
        }

        return users;
    }

    private CreateUserRequest mapUser(UserRepresentation userRepresentation){
        CreateUserRequest user=new CreateUserRequest();
        user.setFirstname(userRepresentation.getFirstName());
        user.setLastname(userRepresentation.getLastName());
        user.setUsername(userRepresentation.getUsername());
        user.setEmail(userRepresentation.getEmail());
        return user;
    }


}
