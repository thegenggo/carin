import React from "react";
import { Link, useNavigate } from "react-router-dom";
import './SetupPage.css';
import pfizer from './images/pfizer.png';
import moderna from './images/moderna.png';
import sinovac from './images/sinovac.png';
import alpha from './images/alpha.png';
import beta from './images/beta.png';
import gamma from './images/gamma.png';
import playButton from './images/playButton 1.png';

function SetupPage() {
    let navigate = useNavigate()

    const start = () => {
        fetch("/game/start", { method: "POST" }).then(() => {
            navigate("/play")
        })
    }

    return (
        <div className="setupPage">
            <div className="setupMidField">
                <h1>Set Genetic Code</h1>
                <div>
                    <div className="row">
                        <Link to={"pfizer"}>
                            <div className="organismPortrait">
                                <img src={pfizer} className="pfizer"></img>
                            </div>
                        </Link>
                        <Link to={"moderna"}>
                            <div className="organismPortrait">
                                <img src={moderna} className="pfizer"></img>
                            </div>
                        </Link>
                        <Link to={"sinovac"}>
                            <div className="organismPortrait">
                                <img src={sinovac} className="pfizer"></img>
                            </div>
                        </Link>
                    </div>
                    <div className="row">
                        <Link to={"alpha"}>
                            <div className="organismPortrait">
                                <img src={alpha} className="pfizer"></img>
                            </div>
                        </Link>
                        <Link to={"beta"}>
                            <div className="organismPortrait">
                                <img src={beta} className="pfizer"></img>
                            </div>
                        </Link>
                        <Link to={"gamma"}>
                            <div className="organismPortrait">
                                <img src={gamma} className="pfizer"></img>
                            </div>
                        </Link>
                    </div>
                </div>
            </div>
            <img src={playButton} className="playButton" onClick={start}></img>
        </div>
    )
}

export default SetupPage;