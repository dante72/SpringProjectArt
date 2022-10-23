import React, { useEffect, useState, useContext } from "react";
import './Cell.css';
import {NumbersContext} from "./Sudoku";

function Cell(props) {

    const numbers = useContext(NumbersContext);
    const [number, setNumber] = useState('');

    useEffect(() => {
        setNumber(props.value);
    },[]);

    useEffect(() => {
        numbers[props.row][props.coluumn] = number;
    },[number]);

    function handleCellClick(event)
    {
        console.log(numbers);
        setNumber(number => number == 9 ? 0 : number + 1);
    }

    return (
        <div className="cell" onClick={handleCellClick}>
            {number == 0 ? '': number}
        </div>
    );
}

export default Cell