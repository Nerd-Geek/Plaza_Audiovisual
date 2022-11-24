package org.example.service.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomersUserDetailsService implements UserDetailsService {
    private final UserService userService;

    /**
     * Cargar usuario por nombre de usuario
     * @param username nombre de usuario
     * @return UserDetails (detalles del usuario)
     * @throws UsernameNotFoundException Usuario no encontrado
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " no encontrado"));
    }

    /**
     * Cargar usuario por ID
     * @param userId ID del usuario
     * @return UserDetails (detalles del usuario)
     */
    public UserDetails loadUserById(String userId) {
        return userService.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario con id: " + userId + " no encontrado"));
    }
}
