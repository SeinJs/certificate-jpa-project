package com.nhnacademy.jpa.service;

import com.nhnacademy.jpa.entity.Resident;
import com.nhnacademy.jpa.repository.ResidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final ResidentRepository residentRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Resident resident = residentRepository.getById(id);
        if (Objects.isNull(resident)) {
            throw new UsernameNotFoundException(id + " not found");
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_MEMBER");
        Collection<GrantedAuthority> grantedAuthorities = Collections.singletonList(grantedAuthority);

        return new User(resident.getName(), resident.getPassword(), grantedAuthorities);
    }

}
