import './Sudoku.css';
import React, { useEffect, useState } from "react";

function Sudoku() {
// set state
    const [sudoku, setSudoku] = useState([]);

// first data grab
    useEffect(() => {
        fetch("http://localhost:8077/sudoku") // your url may look different
            .then(resp => resp.json())
            .then(data => setSudoku(data))
            .catch(error => {
                console.log(error);
            });
    }, []);

    return (
        <table>
            <tbody>
            {
                sudoku.map(str => <tr>
                {
                    str.map(val => <td>{val}</td>)
                }
            </tr>
            )}
            </tbody>
        </table>
    );
}
export default Sudoku