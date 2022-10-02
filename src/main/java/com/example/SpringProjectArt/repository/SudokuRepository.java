package com.example.SpringProjectArt.repository;

import com.example.SpringProjectArt.model.Sudoku;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SudokuRepository extends JpaRepository<Sudoku, Long> {
    Sudoku findByValue(String value);
}
