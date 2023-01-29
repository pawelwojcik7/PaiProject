package com.pai.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "app_service")
@Table(name = "app_service")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private String description;

    @Column
    private Double price;
}
