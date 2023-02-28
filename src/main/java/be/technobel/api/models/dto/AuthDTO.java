package be.technobel.api.models.dto;

import be.technobel.api.models.entity.UserRole;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthDTO {

    private String token;
    private String username;
    private Set<UserRole> roles;

}
