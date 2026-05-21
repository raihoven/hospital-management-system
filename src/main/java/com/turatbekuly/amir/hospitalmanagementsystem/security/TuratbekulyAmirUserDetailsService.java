package com.turatbekuly.amir.hospitalmanagementsystem.security;

import com.turatbekuly.amir.hospitalmanagementsystem.repository.TuratbekulyAmirUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TuratbekulyAmirUserDetailsService implements UserDetailsService {

    private final TuratbekulyAmirUserRepository userRepository;

    public TuratbekulyAmirUserDetailsService(TuratbekulyAmirUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с email " + username + " не найден"));
    }
}
