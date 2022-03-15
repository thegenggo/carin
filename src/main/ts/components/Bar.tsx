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

let canSend = true;

function Bar({ openResetConfirmWindow, openShopWindow, disable, showMessage }: 
    { openResetConfirmWindow: () => void, openShopWindow: () => void, disable: boolean, showMessage: (message: string) => void }) {
    const [antibodyCredit, setAntibodyCredit] = useState(0);
    const [isPlaying, setIsPlaying] = useState(true);

    const fetchAntibodyCredit = () => {
        if (canSend) {
            canSend = false;
            fetch("game/antibodycredit").then(response => response.json()).then(data => {
                setAntibodyCredit(data)
                canSend = true
            }).catch(error => {
                console.log(error)
            })
        }
    }

    const speedUp = () => {
        if (disable) return
        fetch("game/increasespeed").then(response => response.json()).then(data => {
            showMessage("x" + data)
        }).catch(error => {
            console.log(error)
        })
    }

    const speedDown = () => {
        if (disable) return
        fetch("game/decreasespeed").then(response => response.json()).then(data => {
            showMessage("x" + data)
        }).catch(error => {
            console.log(error)
        })
    }

    const pause = () => {
        if (disable) return
        fetch("game/pause").then(response => {
            setIsPlaying(false)
            showMessage("paused")
        }).catch(error => { console.log(error) })
    }

    const resume = () => {
        if (disable) return
        fetch("game/resume").then(response => {
            setIsPlaying(true)
            showMessage("resumed")
        }).catch(error => { console.log(error) })
    }

    useEffect(() => {
        let interval = setInterval(() => {
            fetchAntibodyCredit();
        }, 10)

        fetchAntibodyCredit();
        setIsPlaying(true);

        return () => { clearInterval(interval) }
    }, []);

    return (<div className="Bar" >
        <div className="flex">
            <div className="left">
                <img className="antibodyCreditIcon" src={antibodyCreditIcon} draggable="false"></img>
                <div className="antibodyCredit">
                    {antibodyCredit}
                </div>
            </div>
            <img className="shopButton" src={shopButton} onClick={openShopWindow} draggable="false"></img>
        </div>
        <div className="flex">
            <div className="right">
                <img className="decreaseSpdButton" src={decreaseSpdButton} onClick={speedDown} draggable="false"></img>
                {isPlaying ? <img className="pauseButton" src={pauseButton} onClick={pause} draggable="false"></img>
                    : <img className="resumeButton" src={resumeButton} onClick={resume} draggable="false"></img>}
                <img className="increaseSpdButton" src={increaseSpdButton} onClick={speedUp} draggable="false"></img>
            </div>
            <img className="replayButton" src={replayButton} onClick={openResetConfirmWindow} draggable="false"></img>
        </div>
    </div>
    );
}

export default Bar;