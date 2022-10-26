package lib.sudoku;

public class Sudoku {
    private int[][] field;
    public int[][] solution = null;

    private int solutionCount = 0;
    public boolean hasSingleSolution = false;
    private final int rows = 9, columns = 9;

    public Sudoku()
    {
        field = new int[rows][columns];
        fillField();
    }

    public boolean setField(int[][] field)
    {
        if (checkField(field))
        {
            this.field = field;
            solution = null;
            solutionCount = 0;

            return true;
        }

        return false;
    }

    private boolean checkField()
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

    public void calculate()
    {
        var copy = copy(field);
        bruteForce(copy);
    }

    private void bruteForce(int[][] field)
    {
        int[] index = getEmptyCell(field);

        if (index == null)
        {
            if (solutionCount == 0)
                solution = copy(field);

            solutionCount++;

            if (solutionCount == 1)
                hasSingleSolution = true;
            else
                hasSingleSolution = false;

                return;
        }


        for (int value = 1; value <= 9; value++)
        {
            if (!check(field, value, index[0], index[1]))
                continue;

            field[index[0]][index[1]] = value;
            bruteForce(field);

            if (solutionCount > 1)
                return;
        }

        field[index[0]][index[1]] = 0;
    }

    private int[] getEmptyCell(int[][] field)
    {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                if (field[i][j] == 0)
                    return new int[] {i, j};

        return null;
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
