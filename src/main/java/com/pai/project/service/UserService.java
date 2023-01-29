package com.pai.project.service;

import com.pai.project.configuration.JwtTokenUtil;
import com.pai.project.entity.AppUser;
import com.pai.project.model.JwtResponse;
import com.pai.project.model.UserDto;
import com.pai.project.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final AppUserRepository appUserRepository;
    private final AuthUserService authUserService;


    @Transactional
    public void changePassword(String login, String newPassword) throws UsernameNotFoundException {
        Optional<AppUser> userOpt = appUserRepository.findByLogin(login);
        if (userOpt.isEmpty()) throw new UsernameNotFoundException("User " + login + " does not exist");
        if(newPassword==null || newPassword.equals("")) throw new RuntimeException("New password is empty");
        appUserRepository.updateUserDataWithNewPassword(login, passwordEncoder.encode(newPassword));
    }

    public void registerUser(UserDto newUser) throws RuntimeException {
        Optional<AppUser> userOpt = appUserRepository.findByLogin(newUser.getLogin());
        if (userOpt.isPresent()) throw new RuntimeException("User " + newUser.getLogin() + " already exist");
        else
            appUserRepository.save(new AppUser(null, newUser.getLogin(), passwordEncoder.encode(newUser.getPassword()), null));
    }

    public JwtResponse authenticate(String login, String password) throws AuthenticationException {
        if (login == null || password == null ||
                login.equals("") || password.equals(""))
            throw new RuntimeException("Provided data is empty");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, password));

        UserDetails userDetails = authUserService.loadUserByUsername(login);

        String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtResponse(token);

    }

}
