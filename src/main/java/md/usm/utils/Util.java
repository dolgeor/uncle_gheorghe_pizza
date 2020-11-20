package md.usm.utils;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

public class Util {

    public static UserDetails extractUserDetailsForUser(final md.usm.model.user.User user) {
        return User.withUsername(user.getEmail())
                .passwordEncoder(createDelegatingPasswordEncoder()::encode)
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}