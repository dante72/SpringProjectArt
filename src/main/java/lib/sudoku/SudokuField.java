package lib.sudoku;

import lombok.Data;
import org.hibernate.annotations.Check;

public class SudokuField {
    private int[][] field;
    private int rows = 9, columns = 9;

    public SudokuField()
    {
        field = new int[rows][columns];
        fillField();
    }

    private boolean setField(int[][] field)
    {
        if (checkField(field))
        {
            this.field = field;
            return true;
        }

        return false;
    }

    public boolean checkField()
    {
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[j].length; j++)
                if (!check(field[i][j], i, j))
                    return false;

        return true;
    }


    private boolean checkField(int[][] field)
    {
        if (field.length == rows)
        {
            for (int i = 0; i < field.length; i++)
                if (field[i].length != columns)
                    return false;
        }

        return true;
    }
    private void fillField()
    {
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[i].length; j++)
                field[i][j] = 0;
    }

    private boolean checkHorizontal(int row)
    {
        for (int i = 0; i < field[row].length; i++)
        {
            if (field[row][i] == 0)
                continue;

            for (int j = i + 1; j < field[row].length; j++)
                if (field[row][i] == field[row][j])
                    return false;
        }

        return true;
    }

    private boolean checkVertical(int column)
    {
        for (int i = 0; i < field.length; i++)
        {
            if (field[i][column] == 0)
                continue;

            for (int j = i + 1; j < field.length; j++)
                if (field[i][column] == field[j][column])
                    return false;
        }

        return true;
    }

    private boolean checkHorizontal(int value, int row)
    {
        for (int i = 0; i < field[row].length; i++)
            if (field[row][i] == value)
                return false;

        return true;
    }

    private boolean checkVertical(int value, int column)
    {
        for (int i = 0; i < field.length; i++)
            if (field[i][column] == value)
                return false;

        return true;
    }

    private boolean checkSquare(int value, int row, int column)
    {
        int iSquare = row % 3, jSquare = column % 3;

        for (int i = iSquare * 3; i < iSquare * 3 + 3; i++)
            for (int j = jSquare * 3; j < jSquare * 3 + 3; j++)
            {
                if (i == row && j == column)
                    continue;

                if (field[i][j] == value)
                    return false;
            }

        return true;
    }

    private boolean check(int value, int row, int column)
    {
        return checkHorizontal(value, row) && checkVertical(value, column) && checkSquare(value, row, column);
    }

}
