import './Sudoku.css';

import React, {useEffect, useRef, useState} from "react";
import Cell from "./Cell";


function SudokuPanel(props) {

    const [numbers, setNumbers] = useState([[0, 1, 2, 3, 4], [5, 6, 7, 8, 9]]);


    let print = () =>
    {
        return (
            <table>
                <tbody>
                {
                    numbers.map((str, row)=> {
                        return (
                            <tr key={row}>
                                {
                                    str.map((value, column) => {
                                        return (
                                            <td key={row * 9 + column}>
                                                <Cell row={row}
                                                      column={column}
                                                      value={value}
                                                      mark={false}
                                                      init={1}
                                                      target={props.target}
                                                      update={props.update}
                                                />
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
        <>
            {print()}
        </>
    );

}
export default SudokuPanel