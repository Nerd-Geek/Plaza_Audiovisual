package org.example.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomersUserDetailsService implements UserDetailsService {
    private final UserService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioService.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " no encontrado"));
    }

    public UserDetails loadUserById(String userId) {
        return usuarioService.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario con id: " + userId + " no encontrado"));
    }
}
