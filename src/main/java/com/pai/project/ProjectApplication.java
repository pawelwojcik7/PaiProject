package com.pai.project;

import com.pai.project.entity.*;
import com.pai.project.model.ServiceName;
import com.pai.project.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
@EnableScheduling
public class ProjectApplication {

    private final BookRepository bookRepository;
    private final AppUserRepository appUserRepository;
    private final AppServiceRepository appServiceRepository;
    private final FilmRepository filmRepository;
    private final GameRepository gameRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @PostConstruct
    public void asd() {

        appUserRepository.save(new AppUser(null, "admin", "$2a$10$VX.qLZLRWfcUgNnev9IEFOYsklMbtn3I0yscwo3SK/Ts8wWG2sGZG", ServiceName.PREMIUM.getCode()));

        appServiceRepository.save(new AppService(null, ServiceName.DEFAULT.getCode(), "Usluga pozwala na pobieranie ksiazek", 0.01));
        appServiceRepository.save(new AppService(null, ServiceName.NORMAL.getCode(), "Usluga pozwala na pobieranie ksiazek i filmow", 0.02));
        appServiceRepository.save(new AppService(null, ServiceName.PREMIUM.getCode(), "Usluga pozwala na pobieranie ksiazek, filmow, oraz gier", 0.03));

        bookRepository.save(new Book(null, "W pustyni i w puszczy", "Powiesc przygodowa", 1.00));
        bookRepository.save(new Book(null, "Krzyzacy", "Powiesc historyczna", 1.00));
        bookRepository.save(new Book(null, "Pan Tadeusz", "Poezja Epicka", 1.00));

        filmRepository.save(new Film(null, "Lsnienie", "Horror", 2.0));
        filmRepository.save(new Film(null, "Incepcja", "Thriller", 2.0));
        filmRepository.save(new Film(null, "Goraczka", "Film akcji", 2.0));

        gameRepository.save(new Game(null, "GTA V", "Gra akcji", 3.0));
        gameRepository.save(new Game(null, "Wiedzmin 3", "Gra roku 2015", 3.0));
        gameRepository.save(new Game(null, "Battelfield 4", "FPS", 3.0));

    }
}
