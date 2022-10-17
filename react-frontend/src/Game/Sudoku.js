import './Sudoku.css';

import React, { useEffect, useState } from "react";
import Cell from "./Cell";

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

    return (
        print(state.sudoku)
    );
}
export default Sudoku