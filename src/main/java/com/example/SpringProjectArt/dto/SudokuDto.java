package com.example.SpringProjectArt.dto;

public class SudokuDto {
    public int[][] array = new int[9][9];

    public SudokuDto()
    {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                array[i][j] = 0;

        //array[0][0] = 1;
        array[4][4] = 7;
        //array[5][5] = 8;
    }
}
