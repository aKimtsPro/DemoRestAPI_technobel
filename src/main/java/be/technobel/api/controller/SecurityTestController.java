package be.technobel.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security/test")
public class SecurityTestController {

    // Tout le monde peut acceder
    @GetMapping("/permit-all")
    public String permitAll(){
        return "ok";
    }

    // Tout le monde peut acceder
    @GetMapping("/connected")
    public String connected(){
        return "ok";
    }
    // Tout le monde peut acceder
    @GetMapping("/not-connected")
    public String notConnected(){
        return "ok";
    }
    // Tout le monde peut acceder
    @GetMapping("/role_user")
    public String hasRoleUser(){
        return "ok";
    }
    // Tout le monde peut acceder
    @GetMapping("/any_role")
    public String hasAnyRoleUserAdmin(){
        return "ok";
    }
    // Tout le monde peut acceder
    @GetMapping("/has_authority_role_user")
    public String hasAuthority(){
        return "ok";
    }
    // Tout le monde peut acceder
    @GetMapping("/has_any_authority")
    public String hasAnyAuthority(){
        return "ok";
    }

}
