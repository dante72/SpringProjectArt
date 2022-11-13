import React, {useEffect, useState, useContext, useRef} from "react";
import './Cell.css';

function Cell(props) {

    const [number, setNumber] = useState(props.value);

    function handleCellClick(event)
    {
        let newValue = props.value == 9 ? 0 : props.value + 1;
        props.update(props.row, props.column, newValue);
    }

        return (
            <div className={(props.mark ? 'incorrect' : '') + ' cell' + (props.init !== 0 ? ' init' : '')} onClick={handleCellClick}>
                <span className='text'>{props.value == 0 ? '' : props.value}</span>
            </div>
        );
}

export default Cell