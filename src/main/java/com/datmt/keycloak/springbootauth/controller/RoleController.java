package com.datmt.keycloak.springbootauth.controller;

import com.datmt.keycloak.springbootauth.http.requests.Role;
import com.datmt.keycloak.springbootauth.service.RoleClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/public")
public class RoleController {

    @Autowired
    private RoleClient roleClient;

    @GetMapping("/roles")
    public List<Role> getAllRole(){
        return roleClient.getAllRoles();
    }

    @GetMapping("role/{roleName}")
    public Role getRole(@PathVariable("roleName") String roleName){
        return roleClient.getRole(roleName);
    }

    @PostMapping("/role")
    public ResponseEntity<?> addRole(@NotNull @RequestBody Role role){

        roleClient.addRole(role);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/role-update")
    public ResponseEntity<?> updateRole(@NotNull @RequestBody Role role){
        roleClient.updateRole(role);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/role/{roleName}")
    public  ResponseEntity<?> deleteRole(@PathVariable("roleName") String roleName){
        roleClient.deleteRole(roleName);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping("/user/{userId}/roles")
     public ResponseEntity<List<Role>> fetchRoleByUserId(@PathVariable("userId") String userId){
        return ResponseEntity.ok().body(roleClient.getRoleForParticularUser(userId));
     }

     @GetMapping("/user/{userId}/role/{roleName}")
     public ResponseEntity<?> assignRoleToUser(@PathVariable("userId")String userId,@PathVariable("roleName") String roleName){
        roleClient.assignRole(userId,roleName);
        return ResponseEntity.ok("Successfully Assign Role");
     }
}
