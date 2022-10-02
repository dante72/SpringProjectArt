package com.example.SpringProjectArt.dto;

import com.example.SpringProjectArt.model.Sudoku;
import lombok.experimental.ExtensionMethod;

@ExtensionMethod(Sudoku.class)
public class SudokuExtensions {
    public static SudokuDto ToDto() throws Exception {

        return new SudokuDto();
    }
}
