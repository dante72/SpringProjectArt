package com.example.SpringProjectArt.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="sudoku")
@Data
public class Sudoku extends BaseEntity {
    @Column(name = "value")
    private String value;

    @Column(name = "rating")
    private int rating;
}
