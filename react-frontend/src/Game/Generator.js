import React, {useEffect, useRef, useState} from "react";

function Generator(props) {

    const [count, setCount] = useState(0);


    let generate = () => {
        fetch("http://localhost:8077/generate?count=" + count)
            .then(resp => resp.json())
            .then(data => {
                console.log(data);

            })
            .catch(error => {
                console.log(error);
            });
    }

    function handleInputChange(e) {
        setCount(e.target.value);
    }

    return (
        <>
            <button onClick={generate}>Generate</button>
            <input type="text" onChange={handleInputChange}/>
        </>
    );
}
export default Generator;
