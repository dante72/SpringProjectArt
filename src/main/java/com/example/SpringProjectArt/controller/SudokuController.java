package com.example.SpringProjectArt.controller;

import com.example.SpringProjectArt.dto.SudokuDto;
import com.example.SpringProjectArt.model.User;
import com.example.SpringProjectArt.service.SudokuService;
import com.example.SpringProjectArt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SudokuController {

    private final UserService userService;
    private final SudokuService sudokuService;


    @Autowired
    public SudokuController(UserService userService, SudokuService sudokuService)
    {
        this.userService = userService;
        this.sudokuService = sudokuService;
    }

    @GetMapping("/sudoku")
    public SudokuDto Sudoku()
    {
        return new SudokuDto();
    }
}
