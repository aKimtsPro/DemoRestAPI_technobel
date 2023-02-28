package be.technobel.api.service;


import be.technobel.api.models.dto.AuthDTO;
import be.technobel.api.models.form.LoginForm;
import be.technobel.api.models.form.RegisterUserForm;

public interface AuthService {

    AuthDTO login(LoginForm form);
    AuthDTO register(RegisterUserForm form);

}
