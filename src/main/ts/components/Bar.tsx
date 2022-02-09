import React from "react";
import image from "./images/Bar.png";
import creditIcon from "./images/aitibodyCreditIcon.png";
import shopButton from "./images/shopButton.png";
import decreaseSpdButton from "./images/decreaseSpdButton.png";
import increaseSpdButton from "./images/increaseSpdButton.png";
import pauseButton from "./images/pauseButton.png";
import replayButton from "./images/replayButton.png";
import "./Bar.css";

function Bar() {
    return (<div id="Bar">
        <img id="bar" src={image}></img>
        <img id="antibodyCreditIcon" src={creditIcon}></img>
        <div id="antibodyCreditText">
            {12345}
        </div>
        <img id="shopButton" src={shopButton}></img>
        <img id="decreaseSpdButton" src={decreaseSpdButton}></img>
        <img id="pauseButton" src={pauseButton}></img>
        <img id="replayButton" src={replayButton}></img>
        <img id="increaseSpdButton" src={increaseSpdButton}></img>
    </div>
    );
}
export default Bar;