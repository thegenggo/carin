import React from 'react';
import icon from './images/aitibodyCreditIcon.png';
import "./css/components.css";

function AntibodyCredit() {
    return (
        <div id="aitibodyCredit">
            <img id="aitibodyCreditIcon" src={icon}></img>
            <div id="aitibodyCreditText">
                {12345}
            </div>
        </div>
    );
}

export default AntibodyCredit;