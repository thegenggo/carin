import React from "react";
import { Link, useNavigate } from "react-router-dom";
import cancelButton from "./images/cancel.png";
import confirmButton from "./images/confirmButton.png";
import './ResetWindow.css';

function ResetWindow() {
    const navigate = useNavigate();

    const reset = () => {
        fetch("game/reset").then(response => { navigate("/") }).catch(error => { console.log(error) })
    }

    const cancel = () => {
        document.getElementById('reset-window').style.display = 'none';
    }

    return (
        <div id="reset-window">
            <text className="reset-text">Are you sure to <span>reset</span> the game</text>
            <div className="reset-buttons">
                <img src={confirmButton} onClick={reset}></img>
                <img src={cancelButton} onClick={cancel}></img>
            </div>
        </div>
    )
}

export default ResetWindow;