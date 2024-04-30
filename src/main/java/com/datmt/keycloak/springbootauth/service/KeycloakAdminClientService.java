package com.datmt.keycloak.springbootauth.service;

import com.datmt.keycloak.springbootauth.config.KeycloakProvider;
import com.datmt.keycloak.springbootauth.http.requests.CreateUserRequest;
import com.datmt.keycloak.springbootauth.mapper.UserMapper;
import org.keycloak.admin.client.resource.UsersResource;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.ws.rs.core.Response;

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
        UserRepresentation userRepresentation = UserMapper.mapUserRepo(user);
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


    //fetch all user

    public  List<CreateUserRequest>  getUser(){
        List<UserRepresentation> userRepresentations = kcProvider.getInstance().realm(realm).users().list();
        return UserMapper.mapUsers(userRepresentations);
    }




    public CreateUserRequest getUserById(String userId){
        UserRepresentation representation = kcProvider.getInstance().realm(realm).users().get(userId).toRepresentation();
        return UserMapper.mapUser(representation);
    }

    public boolean updateUser(CreateUserRequest user){
        boolean f=false;
        try{
            kcProvider.getInstance().
                    realm(realm)
                    .users()
                    .get(user.getId()).update(UserMapper.mapUserRepo(user));
            f=true;
        }
        catch (Exception e){
          f=false;
        }
        return f;
    }

    public  boolean deleteUser(String id){
        boolean  f=false;
        try{

            kcProvider.getInstance().realm(realm).users().delete(id);
            f=true;
        }
        catch (Exception e){
            f=false;
        }
        return f;
    }





}
