package com.example.LibraryAPI.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @ManyToOne
    private Author author;
}
