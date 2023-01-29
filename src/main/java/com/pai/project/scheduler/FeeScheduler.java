package com.pai.project.scheduler;

import com.pai.project.entity.Fee;
import com.pai.project.model.FeeType;
import com.pai.project.model.ServiceName;
import com.pai.project.repository.AppUserRepository;
import com.pai.project.repository.FeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class FeeScheduler {

    private final AppUserRepository appUserRepository;
    private final FeeRepository feeRepository;

    @Scheduled(fixedRate = 10000)
    public void fee() {
        appUserRepository.findAll().forEach(e -> {

            if(e.getSubscription()!= null && !e.getSubscription().equals("")){
                if(e.getSubscription().equals(ServiceName.DEFAULT.getCode())){
                    System.out.println(e.getLogin()+": 1 zł for DEFAULT subscription");
                    feeRepository.save(new Fee(null, FeeType.SUBSCRIPTION.getCode(), e.getLogin(), 1.0));
                }
                if(e.getSubscription().equals(ServiceName.NORMAL.getCode())){
                    System.out.println(e.getLogin()+": 2 zł for NORMAL subscription");
                    feeRepository.save(new Fee(null, FeeType.SUBSCRIPTION.getCode(), e.getLogin(), 2.0));
                }
                if(e.getSubscription().equals(ServiceName.PREMIUM.getCode())){
                    System.out.println(e.getLogin()+": 3 zł for PREMIUM subscription");
                    feeRepository.save(new Fee(null, FeeType.SUBSCRIPTION.getCode(), e.getLogin(), 3.0));
                }
            }

        });
    }

}
