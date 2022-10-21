package lib.sudoku;

import lombok.Data;
import org.hibernate.annotations.Check;

public class SudokuField {
    private int[][] field;
    private final int rows = 9, columns = 9;

    public SudokuField()
    {
        field = new int[rows][columns];
        fillField();
    }

    public boolean setField(int[][] field)
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
            for (int j = 0; j < field[i].length; j++)
            {
                if (field[i][j] == 0)
                    continue;

                if (!check(field[i][j], i, j))
                    return false;
            }

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
            for (int j = i + 1; j < field.length; j++)
                if (field[i][column] == field[j][column])
                    return false;
        }

        return true;
    }

    private boolean checkHorizontal(int[][] field, int value, int row, int column)
    {
        for (int i = 0; i < field[row].length; i++)
        {
            if (column == i)
                continue;

            if (field[row][i] == value)
                return false;
        }

        return true;
    }

    private boolean checkHorizontal(int value, int row, int column)
    {
        return checkHorizontal(field, value, row, column);
    }

    private boolean checkVertical(int[][] field, int value, int row, int column)
    {
        for (int i = 0; i < field.length; i++)
        {
            if (row == i)
                continue;

            if (field[i][column] == value)
                return false;
        }

        return true;
    }

    private boolean checkVertical(int value, int row, int column)
    {
        return checkVertical(field, value, row, column);
    }

    private boolean checkSquare(int value, int row, int column)
    {
        return checkSquare(field, value, row, column);
    }
    private boolean checkSquare(int[][] field, int value, int row, int column)
    {
        int iSquare = row / 3, jSquare = column / 3;

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
        return checkHorizontal(value, row, column) && checkVertical(value, row, column) && checkSquare(value, row, column);
    }

    private boolean check(int[][] field, int value, int row, int column)
    {
        return checkHorizontal(field, value, row, column) && checkVertical(field, value, row, column) && checkSquare(field, value, row, column);
    }

    public int[][] calculate()
    {
        var copy = copy(field);

        int[][] result = null;
        return bruteForce(copy, result);
    }

    public int[][] bruteForce(int[][] field, int[][] result)
    {
        //if (result != null)
            //return result;

        int index = getEmptyCell(field);

        if (index == -1)
        {
            result = copy(field);
            return result;
        }


        for (int value = 1; value <= 9; value++)
        {
            if (!check(field, value, index / rows, index % columns))
                continue;

            field[index / rows][index % columns] = value;
            result = bruteForce(field, result);

            if (result != null)
                return result;
        }

        field[index / rows][index % columns] = 0;
        return null;
    }

    public int getEmptyCell(int[][] field)
    {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                if (field[i][j] == 0)
                    return i * rows + j;

        return -1;
    }

    private int[][] copy(int[][] field)
    {
        var copy = new int[rows][columns];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                copy[i][j] = field[i][j];

        return copy;
    }
}
