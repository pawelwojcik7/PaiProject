package com.pai.project.service;

import com.pai.project.entity.AppUser;
import com.pai.project.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<AppUser> userOpt = appUserRepository.findByLogin(login);
        if (userOpt.isEmpty()) throw new UsernameNotFoundException("User " + login + " does not exist");
        else return new User(userOpt.get().getLogin(), userOpt.get().getPassword(), new ArrayList<>());
    }


}
