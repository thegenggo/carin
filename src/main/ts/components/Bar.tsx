import React from "react";
import image from "./images/Bar.png";
import creditIcon from "./images/aitibodyCreditIcon.png";
import "./Bar.css";

function Bar() {
    


    return (<div id="Bar">
        <img id="bar" src={image}></img>
        <img id="antibodyCreditIcon" src={creditIcon}></img>
        <div id="antibodyCreditText">
            {1245678}
        </div>
    </div>
    );
}

export default Bar;