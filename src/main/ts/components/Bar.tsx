import React, { useEffect } from "react";
import antibodyCreditIcon from "./images/antibodyCreditIcon.png";
import shopButton from "./images/shopButton.png";
import decreaseSpdButton from "./images/decreaseSpdButton.png";
import increaseSpdButton from "./images/increaseSpdButton.png";
import pauseButton from "./images/pauseButton.png";
import replayButton from "./images/replayButton.png";
import "./Bar.css";
import { useState } from "react";

function Bar() {
    const [age, setAge] = useState(0);

    const testButton = () => {
        setAge(age - 1);
    }

    useEffect(() => {
        console.log("age: " + age);
    }, [age]);

    return (<div className="Bar flex" >
        <div className="flex flex-row m-8 min-w-3/10 space-x-8">
            <div className="flex flex-row space-x-8">
                <img className="antibodyCreditIcon" src={antibodyCreditIcon}></img>
                <div className="antibodyCreditText">
                    {12345}
                </div>
            </div>
            <img className="shopButton" src={shopButton} onClick={testButton}></img>
        </div>
        <div className="flex flex-row m-8 min-w-3/10 space-x-8">
            <div className="flex flex-row space-x-8">
                <img className="decreaseSpdButton" src={decreaseSpdButton}></img>
                <img className="pauseButton" src={pauseButton}></img>
                <img className="increaseSpdButton" src={increaseSpdButton}></img>
            </div>
            <img className="replayButton" src={replayButton}></img>
        </div>
    </div>
    );
}

export default Bar;