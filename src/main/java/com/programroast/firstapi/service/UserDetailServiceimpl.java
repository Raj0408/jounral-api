package com.programroast.firstapi.service;

import com.programroast.firstapi.entity.UserEntity;
import com.programroast.firstapi.repository.UserEntityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component

public class UserDetailServiceimpl implements UserDetailsService {

    @Autowired
    private UserEntityRepo userEntityRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userEntityRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found" + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();


    }
}
