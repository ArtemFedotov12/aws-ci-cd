package com.start.springlearningdemo.service.userService;


import com.start.springlearningdemo.enums.Role;
import com.start.springlearningdemo.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {

	private static final List<User> users = List.of(
			new User("Artem", "12345", Set.of(Role.ADMIN)),
			new User("Sasha", "12345", Set.of(Role.USER))
	);

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = users.stream().filter(u -> username.equals(u.getUsername())).findFirst().orElse(null);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			//authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getValue()));
		});
		return authorities;
	}


}
