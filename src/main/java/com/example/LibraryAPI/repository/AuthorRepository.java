package com.example.LibraryAPI.repository;

import com.example.LibraryAPI.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
