package com.example.SpringProjectArt.controller;

import com.example.SpringProjectArt.dto.GenerateDto;
import com.example.SpringProjectArt.dto.ResponseDto;
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
        var sudoku = sudokuService.generate();
        res.solution = sudoku.getField();
        return res;
    }

    @GetMapping("/db_sudoku")
    public ResponseDto DbSudoku(long id)
    {
        var res = new ResponseDto();

        var str = sudokuService.findById(id).getValue();
        res.solution = Sudoku.fromString(str);

        return res;
    }

    @PostMapping("/check")
    public ResponseDto Sudoku1(@RequestBody int[][] data)
    {
        var res = new ResponseDto();
        var sudoku = sudokuService.check(data);
        res.solution = sudoku.getSolution();
        res.hasSingleSolution = sudoku.hasSingleSolution();

        return res;
    }

    @GetMapping("/generate")
    public GenerateDto generate(int count) {

        return sudokuService.startGenerate(count);
    }

    @PostMapping("/help")
    public int[][] help(@RequestBody int[][] data)
    {
        return sudokuService.help(data);
    }

}
