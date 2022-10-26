package com.example.SpringProjectArt.controller;

import com.example.SpringProjectArt.dto.ResponseDto;
import com.example.SpringProjectArt.dto.SudokuDto;
import com.example.SpringProjectArt.service.SudokuService;
import com.example.SpringProjectArt.service.UserService;
import lib.sudoku.Sudoku;
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
    public ResponseDto Sudoku()
    {
        var res = new ResponseDto();
        res.solution = new SudokuDto().array;
        return res;
    }

    @PostMapping("/check")
    public ResponseDto Sudoku1(@RequestBody int[][] data)
    {
        var res = new ResponseDto();
        var sudoku = new Sudoku();

        sudoku.setField(data);
        sudoku.calculate();
        res.solution = sudoku.solution;
        res.hasSingleSolution = sudoku.hasSingleSolution;

        return res;
    }


}
