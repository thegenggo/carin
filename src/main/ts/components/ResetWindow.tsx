import React from "react";
import { useNavigate } from "react-router-dom";
import cancelButton from "./images/cancel.png";
import confirmButton from "./images/confirmButton.png";
import './ResetWindow.css';

function ResetWindow({ clearAllWindows }: { clearAllWindows: () => void }) {
    const navigate = useNavigate();

    const reset = () => {
        fetch("game/reset").then(response => { navigate("/") }).catch(error => { console.log(error) })
    }

    return (
        <div id="reset-window">
            <div className="reset-text">Are you sure to <span>reset</span> the game</div>
            <div className="reset-buttons">
                <img src={confirmButton} onClick={reset}></img>
                <img src={cancelButton} onClick={clearAllWindows}></img>
            </div>
        </div>
    )
}

export default ResetWindow;