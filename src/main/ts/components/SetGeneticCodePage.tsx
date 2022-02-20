import React from "react";
import './SetGeneticCodePage.css';
import backButton from './images/backButton.png';
import applyButton from './images/applyButton.png';
import pfizer from './images/pfizer.png';
import moderna from './images/moderna.png';
import sinovac from './images/sinovac.png';
import alpha from './images/alpha.png';
import beta from './images/beta.png';
import gamma from './images/gamma.png';
import okButton from './images/okButton 1.png';
import { Link } from "react-router-dom";

function SetGeneticCodePage({ type }: { type: string }) {

    const apply = () => {
        let textarea = document.getElementById("geneticcode") as HTMLTextAreaElement;
        let geneticCode = textarea.value;

        fetch(`/game/setgeneticcode/${type}`, { body: geneticCode, method: "POST" }).then(response => {
            return response.json();
        }).then((data: boolean) => {
            if (data === true) {
                document.getElementById("successWindow").style.display = "flex";
            }
            else {
                document.getElementById("failWindow").style.display = "flex";
            }
        }).catch(error => {
            console.log(error);
        });
    }

    const back = () => {
        window.location.href = "/";
    }

    const ok = () => {
        document.getElementById("successWindow").style.display = "none";
        document.getElementById("failWindow").style.display = "none";
    }

    const TypeImage = () => {
        switch (type) {
            case "pfizer":
                return <img src={pfizer} className="pfizer"></img>;
            case "moderna":
                return <img src={moderna} className="pfizer"></img>;
            case "sinovac":
                return <img src={sinovac} className="pfizer"></img>;
            case "alpha":
                return <img src={alpha} className="pfizer"></img>;
            case "beta":
                return <img src={beta} className="pfizer"></img>;
            case "gamma":
                return <img src={gamma} className="pfizer"></img>;
            default:
                return <div></div>;
        }
    }

    return (
        <div className="setGeneticCodePage">
            <div className="absolute-t19-l13 organismPortrait">
                {TypeImage()}
            </div>
            <h1>Set Genetic Code</h1>
            <textarea id="geneticcode" rows={20} cols={50}></textarea>
            <Link to="/">
                <img src={backButton} className="backButton" onClick={back}></img>
            </Link>
            <img src={applyButton} className="applyButton" onClick={apply}></img>
            <div id="successWindow">
                <h1>Success</h1>
                <img src={okButton} className="okButton" onClick={ok}></img>
            </div>
            <div id="failWindow">
                <h1>Fail</h1>
                <img src={okButton} className="okButton" onClick={ok}></img>
            </div>
        </div>
    )
}

export default SetGeneticCodePage;