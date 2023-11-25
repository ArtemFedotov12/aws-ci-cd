package com.start.springlearningdemo.service.userService;


import com.start.springlearningdemo.enums.Role;
import com.start.springlearningdemo.exception.UnauthorizedException;
import com.start.springlearningdemo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service(value = "userService")
@Slf4j
public class UserServiceImpl implements UserDetailsService {
    private static final List<User> users = List.of(
            new User("Artem", "12345", Set.of(Role.ADMIN)),
            new User("Sasha", "12345", Set.of(Role.USER))
    );

    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.info("Load user from db. Username: {}", username);
        // imitate loading user from the db
        final Optional<User> optionalUser = users
                .stream()
                .filter(u -> username.equals(u.getUsername()))
                .findFirst();
        if (optionalUser.isEmpty()) {
            throw new UnauthorizedException();
        } else {
            final User user = optionalUser.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    getAuthority(user));
        }
    }

    private Set<SimpleGrantedAuthority> getAuthority(final User user) {
        final Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            //authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getValue()));
        });
        return authorities;
    }

}
