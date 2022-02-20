import React from "react";
import './SetupPage.css';
import pfizer from './images/pfizer.png';
import moderna from './images/moderna.png';
import sinovac from './images/sinovac.png';
import alpha from './images/alpha.png';
import beta from './images/beta.png';
import gamma from './images/gamma.png';
import playButton from './images/playButton 1.png';
import { Link } from "react-router-dom";

function SetupPage() {
    return (
        <div className="setupPage">
            <h1>Set Genetic Code</h1>
            <div>
                <div className="row">
                    <Link to={"setup/pfizer"}>
                        <div className="setupPage organismPortrait">
                            <img src={pfizer} className="pfizer"></img>
                        </div>
                    </Link>
                    <Link to={"setup/moderna"}>
                        <div className="setupPage organismPortrait">
                            <img src={moderna} className="pfizer"></img>
                        </div>
                    </Link>
                    <Link to={"setup/sinovac"}>
                        <div className="setupPage organismPortrait">
                            <img src={sinovac} className="pfizer"></img>
                        </div>
                    </Link>
                </div>
                <div className="row">
                    <Link to={"setup/alpha"}>
                        <div className="setupPage organismPortrait">
                            <img src={alpha} className="pfizer"></img>
                        </div>
                    </Link>
                    <Link to={"setup/beta"}>
                        <div className="setupPage organismPortrait">
                            <img src={beta} className="pfizer"></img>
                        </div>
                    </Link>
                    <Link to={"setup/gamma"}>
                        <div className="setupPage organismPortrait">
                            <img src={gamma} className="pfizer"></img>
                        </div>
                    </Link>
                </div>
            </div>
            <Link to={"/play"}>
                <img src={playButton} className="playButton"></img>
            </Link>
        </div>
    )
}

export default SetupPage;