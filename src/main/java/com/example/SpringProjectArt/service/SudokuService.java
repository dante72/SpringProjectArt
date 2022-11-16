package com.example.SpringProjectArt.service;

import com.example.SpringProjectArt.dto.GenerateDto;
import com.example.SpringProjectArt.model.Sudoku;
import java.util.List;

public interface SudokuService {
    List<Sudoku> getAll();
    Sudoku findByValue(String value);
    Sudoku findById(Long id);
    void delete(Long id);
    void add(String value, int rating);
    GenerateDto startGenerate(int count);
    GenerateDto stopGenerate();
    lib.sudoku.Sudoku generate();
    lib.sudoku.Sudoku check(int[][] field);
}
