package com.pai.project.service;

import com.pai.project.entity.Game;
import com.pai.project.repository.GameRepository;
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
public class GameService {

    private final GameRepository gameRepository;


    public FileInputStream downloadGame(Long id) throws IOException {
        Optional<Game> byId = gameRepository.findById(id);
        if (byId.isEmpty()) throw new RuntimeException("Game does not exist");
        else {
            Game game = byId.get();
            File file = new File(game.getName().trim() + ".txt");
            FileWriter output = new FileWriter(file);
            output.write(game.getName() + " - " + game.getDescription());
            output.close();
            return new FileInputStream(file);
        }

    }

    public String getNameForGame(Long id) {
        Optional<Game> byId = gameRepository.findById(id);
        if (byId.isEmpty()) throw new RuntimeException("Game does not exist");
        else return byId.get().getName();
    }

    public List<Game> getAll() {
        return (List<Game>) gameRepository.findAll();
    }
}
