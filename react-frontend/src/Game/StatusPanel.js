import React, {useEffect, useState, useContext, useRef} from "react";

import "./StatusPanel.css";

function StatusPanel(props) {

    return (
        <div className="status">{props.status}</div>
    );
}

export default StatusPanel