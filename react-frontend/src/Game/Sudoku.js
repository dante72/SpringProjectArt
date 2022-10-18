import './Sudoku.css';

import React, {useEffect, useLayoutEffect, useState} from "react";
import Cell from "./Cell";

function getFieldDto(sudoku)
{
    let field = new Array(9);

    for (let i = 0; i < field.length; i++) {
        field[i] = new Array(9);
    }

    for (let i = 0;  i < sudoku.length; i++)
        for (let j = 0; j < sudoku[i].length; j++)
            field[i][j] = sudoku[i][j].value === '' ? 0 : sudoku[i][j].value;

    return field;
}


function update_field(data)
{
    const field = new Array(9);

    for (let i = 0; i < field.length; i++) {
        field[i] = new Array(9);
    }

    for (let i = 0;  i < data.length; i++)
        for (let j = 0; j < data[i].length; j++)
            field[i][j] =
                {
                    value: data[i][j] === 0 ? '' : data[i][j],
                    mark: false
                };

    return field;
}

function Sudoku() {
// set state
    const [state, setState] = useState({sudoku: [] });

// first data grab
    useEffect(() => {
        fetch("http://localhost:8077/sudoku") // your url may look different
            .then(resp => resp.json())
            .then(data =>
            {
                setState({
                    sudoku: update_field(data)
                });
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    function checkError(matrix, row)
    {
        for (let i = 0; i < matrix[row].length; i++)
            if (matrix[row][i] != 0)
                return true;

        return false;
    }

    let print = (matrix) =>
    {
        return (
            <table>
                <tbody>
                {
                    matrix.map((row, i)=> {
                        return (
                            <tr key={i}>
                                {
                                    row.map((column, j) => {
                                        return (
                                            <td key={i * 9 + j} className={(i % 3 === 0 ? "hor" : "") + (j % 3 === 0 ? " ver" : "")}>
                                                <Cell row={i} column={j} matrix={matrix} isInitial={column.value == 0} />
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
        )
    }

    const url = 'http://localhost:8077/check';
    const data = { username: 'example' };
    let sendSudoku = () => {
        try {
            const response = fetch(url, {
                method: 'POST', // или 'PUT'
                body: JSON.stringify(getFieldDto(state.sudoku)), // данные могут быть 'строкой' или {объектом}!
                headers: {
                    'Content-Type': 'application/json'
                }
            })
                .then(resp => resp.json())
                .then(data => {console.log(getFieldDto(state.sudoku)); alert(data.isCorrect);});
            //const json = await response.json();
            //alert(response);
            //console.log('Success:', JSON.stringify(json));
        }

        catch (error) {
            alert("Error: " + error)
            console.error('Error:', error);
        }
    }

    return (
        <>
            {print(state.sudoku)}
            <button onClick={sendSudoku}> Value</button>>
        </>
    );
}
export default Sudoku