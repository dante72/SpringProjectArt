import React, {useEffect, useState, useContext, useRef} from "react";
import './Cell.css';
import {NumbersContext} from "./Sudoku";
import {MarksContext} from "./Sudoku";

function Cell(props) {
    const marks = useContext(MarksContext);

    const [number, setNumber] = useState(props.value);

    function handleCellClick(event)
    {
        let newValue = props.value == 9 ? 0 : props.value + 1;
        props.update(props.row, props.column, newValue);
    }

        return (
            <div className={(props.mark ? 'incorrect' : '') + ' cell'} onClick={handleCellClick}>
                <text className='text'>{props.value == 0 ? '' : props.value}</text>
            </div>
        );
}

export default Cell