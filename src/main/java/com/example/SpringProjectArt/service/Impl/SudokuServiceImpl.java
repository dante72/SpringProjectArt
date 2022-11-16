package com.example.SpringProjectArt.service.Impl;

import com.example.SpringProjectArt.dto.GenerateDto;
import com.example.SpringProjectArt.model.Sudoku;
import com.example.SpringProjectArt.repository.SudokuRepository;
import com.example.SpringProjectArt.service.SudokuService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@Service
@Slf4j
public class SudokuServiceImpl implements SudokuService {

    private ExecutorService executor;
    private Integer successCount = 0;
    private final SudokuRepository sudokuRepository;

    public SudokuServiceImpl(SudokuRepository sudokuRepository) {
        this.sudokuRepository = sudokuRepository;
    }

    @Override
    public List<Sudoku> getAll() {
        return sudokuRepository.findAll();
    }

    @Override
    public Sudoku findByValue(String value) {
        return sudokuRepository.findByValue(value);
    }

    @Override
    public Sudoku findById(Long id) {
        var item = sudokuRepository.findById(id);
        if (item.isPresent())
            return item.get();
        else
            return null;
    }

    @Override
    public void delete(Long id) {
        var item = findById(id);
        if (item != null)
            sudokuRepository.delete(findById(id));
    }

    @Override
    public void add(String value, int rating) {

        try {
            var item = new Sudoku();
            item.setValue(value);
            item.setRating(rating);
            item.setCountGenerated(1);
            sudokuRepository.save(item);
        } catch (Exception ex)
        {
            Sudoku sudoku = sudokuRepository.findByValue(value);
            if (sudoku != null) {
                sudoku.setRating(rating);
                sudoku.setCountGenerated(sudoku.getCountGenerated() + 1);
                sudokuRepository.save(sudoku);
            }
        }
    }

    @Override
    public GenerateDto startGenerate(int count) {

        var response =  new GenerateDto();
        if (executor != null && !executor.isShutdown()) {

            response.isSuccess = false;
            response.message = "task isn't completed";
            response.successCount = successCount;

            return  response;
        }

        successCount = 0;
        List<Future<Integer>> taskResults = new ArrayList<Future<Integer>>();
        Callable task = () -> {
            try {
                generate();
                return 1;
            }
            catch(Exception ex)
            {
                return 0;
            }
        };

        int processors = Runtime.getRuntime().availableProcessors();
        executor = Executors.newFixedThreadPool(processors);


        successCount = 0;
        try {
            var start = new Date().getTime();

            for (int i = 0; i < count; i++) {
                var future = executor.submit(task);
                taskResults.add(future);
            }

            for (Future<Integer> taskResult : taskResults) {
                    successCount += taskResult.get();
            }

            var stop = new Date().getTime();

            response.isSuccess = true;
            response.message = "Task complete!";
            response.successCount = successCount;
            response.time = timeFormat(stop - start);

            executor.shutdown();

            return response;
        }
        catch (Exception ex)
        {
            response.isSuccess = false;
            response.message = "Error!";
            response.successCount = successCount;
            return response;
        }

    }

    private String timeFormat(long diff)
    {
        long HH = TimeUnit.MILLISECONDS.toHours(diff);
        long MM = TimeUnit.MILLISECONDS.toMinutes(diff) % 60;
        long SS = TimeUnit.MILLISECONDS.toSeconds(diff) % 60;
        long mmmm = diff % 1000;

        return  String.format("%02d:%02d:%02d:%04d", HH, MM, SS, mmmm);
    }

    @Override
    public GenerateDto stopGenerate() {
        return null;
    }

    @Override
    public lib.sudoku.Sudoku generate()
    {
        lib.sudoku.Sudoku newField = new lib.sudoku.Sudoku().generate();
        var solution = newField.getField();
        int rating = newField.getRating();

        var str = lib.sudoku.Sudoku.toString(solution);

        add(str, rating);

        return newField;
    }

    public lib.sudoku.Sudoku check(int[][] field)
    {
        var sudoku = new lib.sudoku.Sudoku();

        sudoku.setField(field);
        sudoku.calculate();

        return sudoku;
    }

    @Override
    public int[][] help(int[][] field) {
        var sudoku = new lib.sudoku.Sudoku();
        sudoku.setField(field);
        return sudoku.getHelp();
    }

}
