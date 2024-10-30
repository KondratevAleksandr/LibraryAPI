package com.example.LibraryAPI.repository;

import com.example.LibraryAPI.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
