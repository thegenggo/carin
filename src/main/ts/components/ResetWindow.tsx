import React from "react";
import cancel from "./images/cancel.png";
import confirmButton from "./images/confirmButton.png";
import './ResetWindow.css';

function ResetWindow() {
    return (
        <div className="reset-window">
            <text className="reset-text">Are you sure to <span>reset</span> the game</text>
            <div className="reset-buttons">
                <img src={confirmButton}></img>
                <img src={cancel}></img>
            </div>
        </div>
    )
}

export default ResetWindow;