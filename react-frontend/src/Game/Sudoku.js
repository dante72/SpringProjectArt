import './Sudoku.css';

import React, {useState} from "react";
import Cell from "./Cell";

export const NumbersContext = React.createContext(null);

function init_field()
{
    return [
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
        [0, 0, 0, 0, 0, 0, 0, 0, 0],
    ];
}

function Sudoku() {

    const [numbers, setNumbers] = useState(init_field());
    const [marks, setMarks] = useState([]);
    const [startNumbers, setStartNumbers] = useState([]);

    let getData = () =>
    {
        fetch("http://localhost:8077/sudoku")
            .then(resp => resp.json())
            .then(data =>
            {
                setNumbers(data);
                console.log(numbers);
            })
            .catch(error => {
                console.log(error);
            });
    }

    let print = (matrix) =>
    {
        return (
            <NumbersContext.Provider value={numbers}>
                <table>
                    <tbody>
                    {
                        matrix.map((str, row)=> {
                            return (
                                <tr key={row}>
                                    {
                                        str.map((value, column) => {
                                            return (
                                                <td key={row * 9 + column} className={(row % 3 === 0 ? "hor" : "") + (column % 3 === 0 ? " ver" : "")}>
                                                    <Cell row={row} column={column} value={value} />
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
            </NumbersContext.Provider>
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
                    console.log(data.solution);
                    setNumbers(data.solution);
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