package com.datmt.keycloak.springbootauth.service;

import com.datmt.keycloak.springbootauth.config.KeycloakProvider;
import com.datmt.keycloak.springbootauth.http.requests.Role;
import com.datmt.keycloak.springbootauth.mapper.RoleMapper;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class RoleClient {

    @Value("${keycloak.realm}")
    public String realm;

    private final KeycloakProvider kcProvider;


    public RoleClient(KeycloakProvider keycloakProvider) {
        this.kcProvider = keycloakProvider;
    }

    public List<Role> getAllRoles(){

        List<RoleRepresentation> roleRepresentationList = kcProvider.getInstance().realm(realm).roles().list();

        return RoleMapper.mapListRole(roleRepresentationList);
    }

    public  Role getRole(String roleName){
        RoleRepresentation representation = kcProvider.getInstance().realm(realm).roles().get(roleName).toRepresentation();
        return RoleMapper.mapRole(representation);
    }

    public  void addRole(Role role){
        RoleRepresentation roleRepresentation = RoleMapper.mapToRepresentation(role);
        kcProvider.getInstance().realm(realm).roles().create(roleRepresentation);

    }

    public void updateRole(Role role){
        RoleRepresentation roleRepresentation = RoleMapper.mapToRepresentation(role);
        kcProvider.getInstance().realm(realm).roles().get(role.getName()).update(roleRepresentation);
    }

    public  void deleteRole(String roleName){
        kcProvider.getInstance().realm(realm).roles().deleteRole(roleName);
    }



    public List<Role> getRoleForParticularUser(String userId){
        Keycloak keycloak = kcProvider.getInstance();
      return   RoleMapper.mapListRole(keycloak.realm(realm).users().get(userId).roles().realmLevel().listAll());
    }


    public void assignRole(String userId,String roleName){
        Keycloak keycloak = kcProvider.getInstance();
        RoleRepresentation roleRepresentation = keycloak.realm(realm).roles().get(roleName).toRepresentation();
        keycloak.realm(realm).users().get(userId).roles().realmLevel().add(Arrays.asList(roleRepresentation));
    }

}
