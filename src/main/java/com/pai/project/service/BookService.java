package com.pai.project.service;

import com.pai.project.entity.Book;
import com.pai.project.repository.BookRepository;
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
public class BookService {

    private final BookRepository bookRepository;


    public FileInputStream downloadBook(Long id) throws IOException {
        Optional<Book> byId = bookRepository.findById(id);
        if (byId.isEmpty()) throw new RuntimeException("Book does not exist");
        else {
            Book book = byId.get();
            File file = new File(book.getName().trim() + ".txt");
            FileWriter output = new FileWriter(file);
            output.write(book.getName() + " - " + book.getDescription());
            output.close();
            return new FileInputStream(file);
        }

    }

    public String getNameForBook(Long id) {
        Optional<Book> byId = bookRepository.findById(id);
        if (byId.isEmpty()) throw new RuntimeException("Book does not exist");
        else return byId.get().getName();
    }

    public List<Book> getAll() {
        return (List<Book>) bookRepository.findAll();
    }
}
