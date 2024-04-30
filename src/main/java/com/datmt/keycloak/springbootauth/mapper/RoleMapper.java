package com.datmt.keycloak.springbootauth.mapper;

import com.datmt.keycloak.springbootauth.http.requests.Role;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.ArrayList;
import java.util.List;

public class RoleMapper {

    public static List<Role> mapListRole(List<RoleRepresentation> roleRepresentationList){
        List<Role> roles =new ArrayList<>();
        if(!roleRepresentationList.isEmpty()){
            roleRepresentationList.forEach(roleRepo->roles.add(mapRole(roleRepo)));
        }
        return roles;
    }

    public static Role mapRole(RoleRepresentation roleRepo) {
        Role role=new Role();
        role.setId(roleRepo.getId());
        role.setName(roleRepo.getName());
        return role;
    }

    public static RoleRepresentation mapToRepresentation(Role role){
        RoleRepresentation roleRepresentation=new RoleRepresentation();
        roleRepresentation.setName(role.getName());
        return roleRepresentation;
    }
}
