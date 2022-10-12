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

    if (props.isInitial) {
        return (
            <div className="cell" onClick={handleCellClick}>
                <text className="text">{state.value}</text>
            </div>
        );
    }
    else
    {
        return (
            <div className="cell initial">
                <text className="text">{state.value}</text>
            </div>
        );
    }
}

export default Cell