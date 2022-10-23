import React, { useEffect, useState, useContext } from "react";
import './Cell.css';
import {NumbersContext} from "./Sudoku";

function Cell(props) {

    const numbers = useContext(NumbersContext);
    const [number, setNumber] = useState(props.value);

    useEffect(() =>{
        numbers[props.row][props.column] = number;
        console.log(numbers);
    },[number])


    function handleCellClick(event)
    {
        setNumber(number == 9 ? 0 : number + 1);
    }

    return (
        <div className='cell' onClick={handleCellClick}>
            <text className='text'>{number == 0 ? '': number}</text>
        </div>
    );
}

export default Cell