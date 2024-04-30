package com.datmt.keycloak.springbootauth.mapper;

import com.datmt.keycloak.springbootauth.http.requests.CreateUserRequest;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserRepresentation mapUserRepo(CreateUserRequest user){
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setId(user.getId());
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


    public static List<CreateUserRequest> mapUsers(List<UserRepresentation> userRepresentations) {

        List<CreateUserRequest> users=new ArrayList<>();

        if(!userRepresentations.isEmpty()){
            userRepresentations.forEach(userRepo->{
                users.add(mapUser(userRepo));
            });
        }

        return users;
    }

    public static CreateUserRequest mapUser(UserRepresentation userRepresentation){
        CreateUserRequest user=new CreateUserRequest();
        user.setId(userRepresentation.getId());
        user.setFirstname(userRepresentation.getFirstName());
        user.setLastname(userRepresentation.getLastName());
        user.setUsername(userRepresentation.getUsername());
        user.setEmail(userRepresentation.getEmail());
        return user;
    }


}
