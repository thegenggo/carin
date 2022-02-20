import React, { useEffect } from "react";
import antibodyCreditIcon from "./images/antibodyCreditIcon.png";
import shopButton from "./images/shopButton.png";
import decreaseSpdButton from "./images/decreaseSpdButton.png";
import increaseSpdButton from "./images/increaseSpdButton.png";
import pauseButton from "./images/pauseButton.png";
import replayButton from "./images/replayButton.png";
import resumeButton from "./images/resumeButton.png";
import "./Bar.css";
import { useState } from "react";

function Bar() {
    const [antibodyCredit, setAntibodyCredit] = useState(0);
    const [isPlaying, setIsPlaying] = useState(true);

    const fetchAntibodyCredit = () => {
        fetch("game/antibodycredit").then(response => response.json()).then(data => {
            setAntibodyCredit(data)
        }).catch(error => {
            console.log(error)
        });
    }

    const speedUp = () => {
        fetch("game/increasespeed")
    }

    const speedDown = () => {
        fetch("game/decreasespeed")
    }

    const pause = () => {
        fetch("game/pause").then(response => setIsPlaying(false)).catch(error => { console.log(error) })
    }

    const resume = () => {
        fetch("game/resume").then(response => setIsPlaying(true)).catch(error => { console.log(error) })
    }

    const start = () => {
        fetch("game/start").then(response => setIsPlaying(true)).catch(error => { console.log(error) })
    }

    const reset = () => {
        fetch("game/reset").then(response => setIsPlaying(true)).catch(error => { console.log(error) })
    }

    useEffect(() => {
        setInterval(() => {
            fetchAntibodyCredit();
        }, 200)
        setIsPlaying(true);
    }, []);

    return (<div className="Bar flex" >
        <div className="flex flex-row m-8 min-w-3/10 space-x-8">
            <div className="flex flex-row space-x-8">
                <img className="antibodyCreditIcon" src={antibodyCreditIcon} onClick={start}></img>
                <div className="antibodyCredit">
                    {antibodyCredit}
                </div>
            </div>
            <img className="shopButton" src={shopButton}></img>
        </div>
        <div className="flex flex-row m-8 min-w-3/10 space-x-8">
            <div className="flex flex-row space-x-8">
                <img className="decreaseSpdButton" src={decreaseSpdButton} onClick={speedDown}></img>
                {isPlaying ? <img className="pauseButton" src={pauseButton} onClick={pause}></img> : <img className="resumeButton" src={resumeButton} onClick={resume}></img>}
                <img className="increaseSpdButton" src={increaseSpdButton} onClick={speedUp}></img>
            </div>
            <img className="replayButton" src={replayButton} onClick={reset}></img>
        </div>
    </div>
    );
}

export default Bar;