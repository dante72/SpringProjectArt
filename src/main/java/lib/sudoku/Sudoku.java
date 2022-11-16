package lib.sudoku;

public class Sudoku {
    private int[][] field;
    private int[][] solution = null;

    private int solutionCount = 0;
    private static final int rows = 9, columns = 9;

    public Sudoku()
    {
        field = new int[rows][columns];
        fillField();
    }

    private static int getRandomIntegerBetweenRange(int min, int max)
    {
        return (int)(Math.random() * ((max - min) + 1)) + min;
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

    public int[][] getField()
    {
        return field;
    }
    public int[][] getSolution() {return solution;}

    public boolean hasSingleSolution()
    {
        return solutionCount == 1;
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
        solutionCount = 0;
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

    public Sudoku generate()
    {
        Sudoku sudoku = new Sudoku();

        for (int i = 0; i < 9; i++) {
            int index = getRandomIntegerBetweenRange(0, rows * columns - 1);

            sudoku.field[index / rows][index % columns] = i + 1;
        }

        sudoku.calculate();
        var copy = copy(sudoku.solution);

        do {
            sudoku.field = copy(copy);
            for (int i = 0; i < rows * columns / 8; i++) {

                int index = getRandomIntegerBetweenRange(0, rows * columns - 1);
                sudoku.field[index / rows][index % columns] = 0;
            }
            sudoku.calculate();
        } while (sudoku.solutionCount > 1);

            int k, t, removingIndex = getRandomIntegerBetweenRange(0, 2);
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++)
                {
                    if (randomRemovingNumber(removingIndex, i, j))
                    {
                        k = i;
                        t = j;
                    }
                    else
                    {
                        k = rows - 1 - i;
                        t = columns - 1 - j;
                    }
                    if (sudoku.field[k][t] == 0)
                        continue;

                    int tmp = sudoku.field[k][t];
                    sudoku.field[k][t] = 0;
                    sudoku.calculate();

                    if (sudoku.solutionCount > 1)
                        sudoku.field[k][t] = tmp;
                }
            }

        randomTransform(sudoku.field);

        return sudoku;
    }

    private boolean chessRandom(int i, int j)
    {
        return i % 2 == 0 && j % 2 != 0 || i % 2 != 0 && j % 2 == 0;
    }

    private boolean halfChessRandom(int i, int j)
    {
        return i % 2 == 0 && j % 2 != 0;
    }

    private boolean randomRemovingNumber(int randomIndex, int i, int j)
    {
        switch (randomIndex)
        {
            case 0:
                return chessRandom(i, j);
            default:
                return halfChessRandom(i, j);
        }
    }


    private void randomTransform(int[][] field)
    {
        int count = getRandomIntegerBetweenRange(4, 9);
        for (int i = 0; i < count; i++)
            doRandomSwap(field, getRandomIntegerBetweenRange(0, 4));
    }
    private void doRandomSwap(int[][] field, int randomIndex)
    {
        switch (randomIndex)
        {
            case 0:
                randomSwapColumnsArea(field);
                break;
            case 1:
                randomSwapRowsArea(field);
                break;
            case 2:
                randomSwapRowsSmall(field);
                break;
            case 3:
                randomSwapColumnsSmall(field);
                break;
            case 4:
                transposition(field);
                break;
            default:
                break;
        }
    }
    private void randomSwapColumnsArea(int[][] field)
    {
        transposition(field);
        randomSwapRowsArea(field);
        transposition(field);
    }

    private void randomSwapRowsArea(int[][] field)
    {
        int areaIndex1, areaIndex2;
        do {
            areaIndex1 = getRandomIntegerBetweenRange(0, 2);
            areaIndex2 = getRandomIntegerBetweenRange(0, 2);
        } while (areaIndex1 == areaIndex2);

        swapRowsArea(field, areaIndex1, areaIndex2);
    }

    private void randomSwapRowsSmall(int[][] field)
    {
        int rowIndex1, rowIndex2;
        int areaIndex = getRandomIntegerBetweenRange(0, 2);
        do {
            rowIndex1 = getRandomIntegerBetweenRange(0, 2);
            rowIndex2 = getRandomIntegerBetweenRange(0, 2);
        } while (rowIndex1 == rowIndex2);

        swapRowsSmall(field, areaIndex * 3 + rowIndex1, areaIndex * 3 + rowIndex2);
    }

    private void randomSwapColumnsSmall(int[][] field)
    {
        transposition(field);
        randomSwapRowsSmall(field);
        transposition(field);
    }

    private void transposition(int[][] field)
    {
        for (int i = 0; i < field.length; i++)
        {
            for (int j = 0; j < i; j++)
            {
                int tmp = field[i][j];
                field[i][j] = field[j][i];
                field[j][i] = tmp;
            }
        }
    }


    private void swapRowsSmall(int[][] field, int row1, int row2)
    {
        int[] tmp = field[row1];
        field[row1] = field[row2];
        field[row2] = tmp;
    }

    private void swapColumnsSmall(int[][] field, int column1, int column2)
    {
        transposition(field);
        swapRowsSmall(field, column1, column2);
        transposition(field);
    }

    private void swapRowsArea(int[][] field, int areaRow1, int areaRow2)
    {
        for (int i = 0; i < 3; i++)
            swapRowsSmall(field, areaRow1 * 3 + i, areaRow2 * 3 + i);
    }

    private void swapColumnsArea(int[][] field, int areaColumn1, int areaColumn2)
    {
        transposition(field);
        swapRowsArea(field, areaColumn1, areaColumn2);
        transposition(field);
    }

    private int getNumber()
    {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                if (field[i][j] != 0)
                    return i * rows + j;

        return -1;
    }

    public static String toString(int[][] field)
    {
        String str = "";
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                str += field[i][j];
            }
        }

        return str;
    }

    public static int[][] fromString(String str) {
        var field = new int[rows][columns];
        var chars = str.toCharArray();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                field[i][j] = chars[i * rows + j] - '0';
            }
        }

        return field;
    }

    public int getRating()
    {
        int rating = 0, count;

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                if (field[i][j] > 0)
                    continue;

                count = 0;
                for (int value = 1; value <= 9; value++)
                {
                    if (check(field, value, i, j))
                        count++;

                }

                if (count == 2)
                    rating += 100;
                else if (count == 1)
                    rating += 1;
            }
        }
        return rating;
    }

    public int[][] getHelp()
    {

        int[][] result = new int[rows][columns];
        for (int i = 0; i < result.length; i++)
            for (int j = 0; j < result[i].length; j++)
                result[i][j] = -1;

        int count;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < columns; j++)
            {
                if (field[i][j] > 0)
                    continue;

                count = 0;
                for (int value = 1; value <= 9; value++)
                {
                    if (check(field, value, i, j))
                        count++;

                }

                result[i][j] = count;

            }
        }
        return result;
    }
}
