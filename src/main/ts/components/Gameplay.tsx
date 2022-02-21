import React from 'react';
import Bar from './Bar';
import Canvas from './Canvas';
import './Gameplay.css';
import ResetWindow from './ResetWindow';

function Gameplay() {
    const openResetConfirmWindow = () => {
        console.log("Reset button clicked");
        document.getElementById('reset-window').style.display = 'block';
    }

    return (
        <div className="gameplay">
            <Bar openResetConfirmWindow={openResetConfirmWindow}/>
            <Canvas/>
            <ResetWindow/>
        </div>
    )
}

export default Gameplay;