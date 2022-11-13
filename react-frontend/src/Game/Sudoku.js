import './Sudoku.css';

import React, {useEffect, useRef, useState} from "react";
import Cell from "./Cell";

export const NumbersContext = React.createContext(null);
export const MarksContext = React.createContext(null);
export const InitNumbersContext = React.createContext(null);

function init_field(init_val)
{
    return new Array(9).fill(null).map(_ =>
        new Array(9).fill(init_val)
    );
}

function copy(data)
{
    let field = init_field(0);

    for (let i = 0; i < field.length; i++)
        for (let j = 0; j < field[i].length; j++)
            field[i][j] = data[i][j];

    return field;
}

function Sudoku(props) {

    const [numbers, setNumbers] = useState(init_field(0));
    const [marks, setMarks] = useState(init_field(false));
    const [initNumbers, setInitNumber] = useState(init_field(0));
    const [startNumbers, setStartNumbers] = useState([]);
    const [target, setTarget] = useState(numbers[0][0]);

    let getData = () =>
    {
        fetch("http://localhost:8077/sudoku")
            .then(resp => resp.json())
            .then(data =>
            {
                let {solution} = data;
                let {hasSingleSolution} = data;
                setNumbers(solution);
                setInitNumber(solution);
                //setInitNumber(paintInitNumbers(solution));
                console.log(numbers);
                console.log(initNumbers);

            })
            .catch(error => {
                console.log(error);
            });
    }

    function paintInitNumbers(data)
    {
        let field = init_field(false);
        for (let i = 0; i < data.length; i++)
            for (let j = 0; j < data[i].length; j++)
                if (data[i][j] == 0)
                    field[i][j] = true;

        return field;
    }

    function checkHorizontal(row, column) {

        for (let col = 0; col < numbers[row].length; col++)
            if (numbers[row][col] != 0)
                if (col != column && numbers[row][col] == numbers[row][column])
                    return false;

        return true;
    }

    function correctHorizontal(row)
    {
        for (let col = 0; col < numbers[row].length; col++)
            if (!checkHorizontal(row, col))
                return false;

        return true;
    }

    function paintRow(marks, row)
    {
        for (let i = 0; i < marks[row].length; i++)
            marks[row][i] = true;
    }

    function checkVertical(row, column) {

        for (let r = 0; r < numbers.length; r++)
            if (numbers[r][column] != 0)
                if (r != row && numbers[r][column] == numbers[row][column])
                    return false;

        return true;
    }

    function correctVertical(column)
    {
        for (let r = 0; r < numbers.length; r++)
            if (!checkVertical(r, column))
                return false;

        return true;
    }

    function paintColumn(marks, column)
    {
        for (let i = 0; i < marks.length; i++)
            marks[i][column] = true;
    }

    function checkSquare(row, column) {

        let iSquare = row / 3 | 0, jSquare = column / 3 | 0;

        for (let i = iSquare * 3; i < iSquare * 3 + 3; i++)
            for (let j = jSquare * 3; j < jSquare * 3 + 3; j++)
            {
                if (numbers[i][j] == 0 || i == row && j == column)
                    continue;

                if (numbers[i][j] == numbers[row][column])
                    return false;
            }

        return true;
    }

    function correctSquare(iSquare, jSquare)
    {
        for (let i = iSquare * 3; i < iSquare * 3 + 3; i++)
            for (let j = jSquare * 3; j < jSquare * 3 + 3; j++)
                if (!checkSquare(i, j))
                    return false;

        return true;
    }

    function paintSquare(marks, iSquare, jSquare)
    {
        for (let i = iSquare * 3; i < iSquare * 3 + 3; i++)
            for (let j = jSquare * 3; j < jSquare * 3 + 3; j++)
                marks[i][j] = true;
    }

    function create_marks()
    {
        let marks = init_field(false);
        for (let i = 0; i < marks.length; i++)
            if (!correctHorizontal(i))
                paintRow(marks,  i);

        for (let i = 0; i < marks[0].length; i++)
            if (!correctVertical(i))
                paintColumn(marks,  i);

        for (let i = 0; i < 3; i++)
            for (let j = 0; j < 3; j++)
                if (!correctSquare(i, j))
                    paintSquare(marks, i, j);

        return marks;
    }


    let update = (row, column, value) => {


        //console.log(correctHorizontal(row));
        //console.log(checkHorizontal(row, column));

        numbers[row][column] = value;

        setMarks(create_marks());
        setNumbers(copy(numbers));

        console.log(checkSquare(row, column));


        console.log(numbers);
        console.log(marks);
    }

    let print = () =>
    {
        return (
            <MarksContext.Provider value={marks}>
            <NumbersContext.Provider value={numbers}>
                <InitNumbersContext.Provider value={initNumbers}>
                <table>
                    <tbody>
                    {
                        numbers.map((str, row)=> {
                            return (
                                <tr key={row}>
                                    {
                                        str.map((value, column) => {
                                            return (
                                                <td key={row * 9 + column}
                                                    className={(row % 3 === 0 ? "hor" : "") + (column % 3 === 0 ? " ver" : "")}>
                                                    <Cell row={row}
                                                          column={column}
                                                          value={value}
                                                          mark={marks[row][column]}
                                                          init={initNumbers[row][column]}
                                                          update={update} />
                                                </td>
                                            );
                                        })
                                    }
                                </tr>
                            );
                        })
                    }
                    </tbody>
                </table>
                </InitNumbersContext.Provider>
            </NumbersContext.Provider>
            </MarksContext.Provider>
        )
    }

    const url = 'http://localhost:8077/check';
    const data = { username: 'example' };
    let sendSudoku = () => {
        try {
            fetch(url, {
                method: 'POST',
                body: JSON.stringify(numbers),
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(resp => resp.json())
                .then(data => {
                    let {solution} = data;
                    let {hasSingleSolution} = data;
                    console.log(solution);
                    console.log('hasSingleSolution = ' + hasSingleSolution);
                    setNumbers(solution);
                });
        }

        catch (error) {
            alert("Error: " + error)
            console.error('Error:', error);
        }
    }

    return (
        <>
            {print(numbers)}
            <button onClick={sendSudoku}> Value</button>
            <button onClick={getData}>Get Data</button>
        </>
    );
}
export default Sudoku