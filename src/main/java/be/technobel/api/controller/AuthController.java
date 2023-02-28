package be.technobel.api.controller;

import be.technobel.api.models.dto.AuthDTO;
import be.technobel.api.models.form.LoginForm;
import be.technobel.api.models.form.RegisterUserForm;
import be.technobel.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthDTO login(@RequestBody @Valid LoginForm form){
        return authService.login(form);
    }

    @PostMapping("/register")
    public AuthDTO register(@RequestBody @Valid RegisterUserForm form){
        return authService.register(form);
    }

}
