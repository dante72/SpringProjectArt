import React, {useEffect, useState, useContext, useRef} from "react";
import './Cell.css';

function Cell(props) {

    function handleCellClick(event)
    {
        if (props.init > 0)
            return;
        let newValue = props.value == 9 ? 0 : props.value + 1;
        props.update(props.row, props.column, newValue);
    }

    function dragStartHandler(e, row, column, value) {

        console.log('drag start', row, column, value);
    }

    function dragEndHandler(e, value) {
        if (props.target.value.init === 0)
            props.update(props.target.value.row, props.target.value.column, value);
        props.update(props.row, props.column, 0);
        console.log('drag end', props.target.value);
    }

    function dragOverHandler(e, row, column, value, init) {
        console.log('drag over', row, column, value, init);
        props.target.value = (
            {
                row: row,
                column: column,
                value: value,
                init: init
            }
        );
    }

    function dropHandler(e) {
        console.log('drag drop', e.target);
    }

    return (
            <div className={(props.mark ? 'incorrect' : '') + ' cell' + (props.init !== 0 ? ' init' : '')}
                 onClick={handleCellClick}
                 onDragStart={(e) => dragStartHandler(e, props.row, props.column, props.value)}
                 onDragEnd={(e) => dragEndHandler(e, props.value)}
                 onDragOver={(e) => dragOverHandler(e, props.row, props.column, props.value, props.init)}
                 onDrop={(e) => dropHandler(e)}
                 draggable={true}
            >
                <span className='text'>{props.value == 0 ? '' : props.value}</span>
            </div>
        );
}

export default Cell