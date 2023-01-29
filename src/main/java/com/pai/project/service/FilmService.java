package com.pai.project.service;

import com.pai.project.entity.Film;
import com.pai.project.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;


    public FileInputStream downloadFilm(Long id) throws IOException {
        Optional<Film> byId = filmRepository.findById(id);
        if (byId.isEmpty()) throw new RuntimeException("Film does not exist");
        else {
            Film film = byId.get();
            File file = new File(film.getName().trim() + ".txt");
            FileWriter output = new FileWriter(file);
            output.write(film.getName() + " - " + film.getDescription());
            output.close();
            return new FileInputStream(file);
        }

    }

    public String getNameForFilm(Long id) {
        Optional<Film> byId = filmRepository.findById(id);
        if (byId.isEmpty()) throw new RuntimeException("Film does not exist");
        else return byId.get().getName();
    }

    public List<Film> getAll() {
        return (List<Film>) filmRepository.findAll();
    }
}
