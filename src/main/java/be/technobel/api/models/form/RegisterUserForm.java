package be.technobel.api.models.form;

import be.technobel.api.models.entity.User;
import be.technobel.api.models.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RegisterUserForm {

    @NotBlank
    private String login;

    @NotBlank
    @Size(min = 4)
    private String password;

    @NotBlank
    private String email;

    public User toEntity(){

        User user = new User();

        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setRoles( Set.of(UserRole.USER) );
        user.setEnabled(true);

        return user;

    }

}
