package lib.sudoku;

import com.example.SpringProjectArt.service.SudokuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;

public class GeneratorTask implements Callable<Integer> {

    private int count;
    private final SudokuService sudokuService;


    public GeneratorTask(SudokuService sudokuService, int count) {
        this.sudokuService = sudokuService;
        this.count = count;
    }

    @Override
    public Integer call() throws Exception {

        int i = 0;

        try {

            for (i = 0; i < count; i++)
                sudokuService.generate();
        }
        catch (Exception ex)
        {

        }
        finally {

            return i;
        }

    }
}
