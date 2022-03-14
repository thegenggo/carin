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

function Bar({ openResetConfirmWindow, openShopWindow }: { openResetConfirmWindow: () => void, openShopWindow: () => void }) {
    const [antibodyCredit, setAntibodyCredit] = useState(0);
    const [isPlaying, setIsPlaying] = useState(true);
    const [timer, setTimer] = useState<NodeJS.Timeout>();

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
        fetch("game/increasespeed").then(response => response.json()).then(data => {
            showMessage("x" + data)
        }).catch(error => {
            console.log(error)
        })
    }

    const speedDown = () => {
        fetch("game/decreasespeed").then(response => response.json()).then(data => {
            showMessage("x" + data)
        }).catch(error => {
            console.log(error)
        })
    }

    const pause = () => {
        fetch("game/pause").then(response => {
            setIsPlaying(false)
            showMessage("paused")
        }).catch(error => { console.log(error) })
    }

    const resume = () => {
        fetch("game/resume").then(response => {
            setIsPlaying(true)
            showMessage("resumed")
        }).catch(error => { console.log(error) })
    }

    const showMessage = (message: string) => {
        console.log("message: " + message)
        let messageElement = document.getElementById("message")
        if(messageElement) {

        } else {
            messageElement = document.createElement("div")
            messageElement.id = "message"
            document.body.appendChild(messageElement)
        }
        messageElement.innerHTML = message
        clearTimeout(timer)
        setTimer(setTimeout(() => {
            messageElement.innerHTML = ""
            console.log()
        }, 1000))
    }

    useEffect(() => {
        let interval = setInterval(() => {
            fetchAntibodyCredit();
        }, 10)

        fetchAntibodyCredit();
        setIsPlaying(true);

        return () => { clearInterval(interval) }
    }, []);

    return (<div className="Bar flex" >
        <div className="flex flex-row m-8 min-w-3/10 space-x-8">
            <div className="flex flex-row space-x-8">
                <img className="antibodyCreditIcon" src={antibodyCreditIcon}></img>
                <div className="antibodyCredit">
                    {antibodyCredit}
                </div>
            </div>
            <img className="shopButton" src={shopButton} onClick={openShopWindow}></img>
        </div>
        <div className="flex flex-row m-8 min-w-3/10 space-x-8">
            <div className="flex flex-row space-x-8">
                <img className="decreaseSpdButton" src={decreaseSpdButton} onClick={speedDown}></img>
                {isPlaying ? <img className="pauseButton" src={pauseButton} onClick={pause}></img>
                    : <img className="resumeButton" src={resumeButton} onClick={resume}></img>}
                <img className="increaseSpdButton" src={increaseSpdButton} onClick={speedUp}></img>
            </div>
            <img className="replayButton" src={replayButton} onClick={openResetConfirmWindow}></img>
        </div>
    </div>
    );
}

export default Bar;