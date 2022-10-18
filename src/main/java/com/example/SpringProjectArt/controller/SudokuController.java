package com.example.SpringProjectArt.controller;

import com.example.SpringProjectArt.dto.ResponseDto;
import com.example.SpringProjectArt.dto.SudokuDto;
import com.example.SpringProjectArt.service.SudokuService;
import com.example.SpringProjectArt.service.UserService;
import lib.sudoku.SudokuField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class SudokuController {

    private final UserService userService;
    private final SudokuService sudokuService;


    Logger logger = LoggerFactory.getLogger(SudokuController.class);
    @Autowired
    public SudokuController(UserService userService, SudokuService sudokuService)
    {
        this.userService = userService;
        this.sudokuService = sudokuService;
    }

    @GetMapping("/sudoku")
    public int[][] Sudoku()
    {
        return new SudokuDto().array;
    }

    @PostMapping("/check")
    public ResponseDto Sudoku1(@RequestBody int[][] data)
    {
        var res = new ResponseDto();

        var sudokuField = new SudokuField();
        sudokuField.setField(data);
        res.isCorrect = sudokuField.checkField();

        return res;
    }


}
