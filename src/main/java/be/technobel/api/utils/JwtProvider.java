package be.technobel.api.utils;


import be.technobel.api.models.entity.User;
import be.technobel.api.models.entity.UserRole;
import be.technobel.api.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class JwtProvider {

    private static final String JWT_SECRET = "UTC.ZO\"7%0u7.ieT_f`nsQd)8Z',yp/7k[N;#D%zgrY\"z{Bheg04(O)\"H&~W\"Jv";
    private static final long EXPIRES_AT = 86_400_000;
    private static final String AUTH_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final UserDetailsService userDetailsService;

    public JwtProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String username, List<UserRole> roles){

        return TOKEN_PREFIX + JWT.create()
                .withExpiresAt( Instant.now().plusMillis(EXPIRES_AT) )
                .withSubject(username)
                .withClaim("roles", roles.stream().map(Enum::toString).toList())
                .sign( Algorithm.HMAC512(JWT_SECRET) );

    }


    public String extractToken(HttpServletRequest req){
        String authHeader = req.getHeader( AUTH_HEADER );

        if(authHeader == null || !authHeader.startsWith( TOKEN_PREFIX ))
            return null;

        return authHeader.replaceFirst( TOKEN_PREFIX, "" );
    }

    public boolean validateToken(String token){

        try {
            // 1, Le bon secret a été utilisé (et le meme algo)
            // 2, pas expiré
            DecodedJWT jwt = JWT.require( Algorithm.HMAC512(JWT_SECRET) )
                    .acceptExpiresAt( EXPIRES_AT )
                    .withClaimPresence("sub")
                    .withClaimPresence("roles")
                    .build()
                    .verify( token );

            // 3, généré a partir d'un user existant
            String username = jwt.getSubject();
            User user = (User) userDetailsService.loadUserByUsername(username);
            if( !user.isEnabled() )
                return false;

            // (4, Les roles ne sont plus bon) Verifier les roles n'est pas conventionnel
            List<UserRole> tokenRoles = jwt.getClaim("roles")
                    .asList(UserRole.class);

            return user.getRoles().containsAll( tokenRoles );
        }
        catch (JWTVerificationException | UsernameNotFoundException ex ){
            return false;
        }


    }

    public Authentication createAuthentication(String token){
        DecodedJWT jwt = JWT.decode(token);

        String username = jwt.getSubject();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                null,
                userDetails.getAuthorities()
        );
    }
}
