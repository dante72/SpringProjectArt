import './Sudoku.css';

import React, { useEffect, useState } from "react";
import Cell from "./Cell";

const field =
    [
        ['', '', '', '', '', '', '', '', ''],
        ['', '', '', '', '', '', '', '', ''],
        ['', '', '', '', '', '', '', '', ''],
        ['', '', '', '', '', '', '', '', ''],
        ['', '', '', '', '', '', '', '', ''],
        ['', '', '', '', '', '', '', '', ''],
        ['', '', '', '', '', '', '', '', ''],
        ['', '', '', '', '', '', '', '', ''],
        ['', '', '', '', '', '', '', '', ''],
    ];

function update_field(data)
{
    for (let i = 0;  i < data.length; i++)
        for (let j = 0; j < data[i].length; j++)
        {
            if (data[i][j] === 0)
                field[i][j] = '';
            else
                field[i][j] = data[i][j];
        }
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
                update_field(data);
                setState({
                    sudoku: field
                });
            })
            .catch(error => {
                console.log(error);
            });
    }, []);

    let update = (row, column, value) => {

        console.log(field);
        console.log(state.sudoku);
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
                                                <Cell row={i} column={j} matrix={matrix} isInitial={column == 0}/>
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

    return (
        print(state.sudoku)
    );
}
export default Sudoku