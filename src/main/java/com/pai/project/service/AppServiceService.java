package com.pai.project.service;

import com.pai.project.entity.AppService;
import com.pai.project.entity.AppUser;
import com.pai.project.model.ServiceName;
import com.pai.project.repository.AppUserRepository;
import com.pai.project.repository.AppServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppServiceService {

    private final AppUserRepository appUserRepository;
    private final AppServiceRepository appServiceRepository;

    @Transactional
    public String subscribe(Long subscriptionId, String login) throws RuntimeException{

        Optional<AppService> byId = appServiceRepository.findById(subscriptionId);
        if(byId.isEmpty()) throw new RuntimeException("Service does not exist");
        else{
            AppService appService = byId.get();
            appUserRepository.updateUserDataWithNewSubscription(login, appService.getName());
            return appService.getName();
        }


    }

    @Transactional
    public void unsubscribe(String name) {
        appUserRepository.updateUserDataWithNewSubscription(name, null);
    }

    public List<AppService> getAll() {
        return (List<AppService>) appServiceRepository.findAll();
    }

    public void checkBookSubscriptions(String name) throws RuntimeException{

        Optional<AppUser> byLogin = appUserRepository.findByLogin(name);
        if(byLogin.isEmpty()) throw new RuntimeException("User does not exist");
        else{
            AppUser appUser = byLogin.get();
            if(appUser.getSubscription() ==null || appUser.getSubscription().equals("")) throw new RuntimeException("You must subscribe to the minimum default service");
        }

    }

    public void checkFilmSubscriptions(String name) throws RuntimeException{

        Optional<AppUser> byLogin = appUserRepository.findByLogin(name);
        if(byLogin.isEmpty()) throw new RuntimeException("User does not exist");
        else{
            AppUser appUser = byLogin.get();
            if(appUser.getSubscription() ==null || appUser.getSubscription().equals("") || appUser.getSubscription().equals(ServiceName.DEFAULT.getCode())) throw new RuntimeException("You must subscribe to the minimum normal service");
        }

    }

    public void checkGameSubscriptions(String name) throws RuntimeException{

        Optional<AppUser> byLogin = appUserRepository.findByLogin(name);
        if(byLogin.isEmpty()) throw new RuntimeException("User does not exist");
        else{
            AppUser appUser = byLogin.get();
            if(appUser.getSubscription() ==null || appUser.getSubscription().equals("") || appUser.getSubscription().equals(ServiceName.DEFAULT.getCode()) || appUser.getSubscription().equals(ServiceName.NORMAL.getCode())) throw new RuntimeException("You must subscribe to the premium service");
        }

    }

}
