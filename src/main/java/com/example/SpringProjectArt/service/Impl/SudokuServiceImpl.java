package com.example.SpringProjectArt.service.Impl;

import com.example.SpringProjectArt.model.Sudoku;
import com.example.SpringProjectArt.repository.SudokuRepository;
import com.example.SpringProjectArt.service.SudokuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SudokuServiceImpl implements SudokuService {

    private final SudokuRepository sudokuRepository;

    public SudokuServiceImpl(SudokuRepository sudokuRepository) {
        this.sudokuRepository = sudokuRepository;
    }

    @Override
    public List<Sudoku> getAll() {
        return sudokuRepository.findAll();
    }

    @Override
    public Sudoku findByValue(String value) {
        return sudokuRepository.findByValue(value);
    }

    @Override
    public Sudoku findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {
        var item = findById(id);
        if (item != null)
            sudokuRepository.delete(findById(id));
    }

    @Override
    public void add(String value, int rating) {

        Sudoku sudoku = sudokuRepository.findByValue(value);
        if (sudoku == null) {
            var item = new Sudoku();
            item.setValue(value);
            item.setRating(rating);
            item.setCountGenerated(1);
            sudokuRepository.save(item);
        }
        else
        {
            sudoku.setRating(rating);
            sudoku.setCountGenerated(sudoku.getCountGenerated() + 1);
            sudokuRepository.save(sudoku);
        }
    }
}
