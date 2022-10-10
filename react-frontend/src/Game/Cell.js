import React, { useEffect, useState } from "react";
import './Cell.css';

function Cell(props) {

    const [state, setState] = useState({value: ''});

    useEffect(() => {
        setState({
            value: props.matrix[props.row][props.column]
        })
    },[]);

    function handleCellClick(event)
    {
        setState({
           value: state.value === 9 ? '' : ++state.value
        });

        props.matrix[props.row][props.row] = state.value;
    }

    return (
        <div className="cell" onClick={handleCellClick}>{state.value}</div>
    );
}

export default Cell