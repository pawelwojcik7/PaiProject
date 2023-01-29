package com.pai.project.service;

import com.pai.project.entity.AppService;
import com.pai.project.entity.AppUser;
import com.pai.project.entity.Fee;
import com.pai.project.model.FeeType;
import com.pai.project.repository.AppServiceRepository;
import com.pai.project.repository.AppUserRepository;
import com.pai.project.repository.FeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FeeService {

    private final FeeRepository feeRepository;
    private final AppUserRepository appUserRepository;
    private final AppServiceRepository appServiceRepository;

    public void notifyFeeBySubscription(String login) {
        Optional<AppUser> byLogin = appUserRepository.findByLogin(login);
        if (byLogin.isEmpty()) throw new RuntimeException("user does not exist");
        else {
            AppUser appUser = byLogin.get();
            AppService byName = appServiceRepository.findByName(appUser.getSubscription());
            feeRepository.save(new Fee(null, FeeType.SUBSCRIPTION.getCode(), login, byName.getPrice()));
        }
    }

    public void notifyFeeByDownload(String login, FeeType feeType) {
        Optional<AppUser> byLogin = appUserRepository.findByLogin(login);
        if (byLogin.isEmpty()) throw new RuntimeException("User does not exist");
        else {
            if (feeType.getCode().equals(FeeType.DOWNLOAD_BOOK.getCode()))
                feeRepository.save(new Fee(null, feeType.getCode(), login, 1.0));
            if (feeType.getCode().equals(FeeType.DOWNLOAD_FILM.getCode()))
                feeRepository.save(new Fee(null, feeType.getCode(), login, 2.0));
            if (feeType.getCode().equals(FeeType.DOWNLOAD_GAME.getCode()))
                feeRepository.save(new Fee(null, feeType.getCode(), login, 3.0));
        }
    }

    public List<Fee> getAllForUser(String name) {
        return feeRepository.findAllByLogin(name);
    }
}
