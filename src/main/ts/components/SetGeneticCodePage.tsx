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
import { Link, useNavigate } from "react-router-dom";



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
            <div className="flex">
                <div className="flex row1">
                    <div className="empty"></div>
                    <div className="text">
                        Set Genetic Code
                    </div>
                </div>
                <div className="flex row2">
                    <div className="organismPortrait">
                        {TypeImage()}
                    </div>
                    <textarea spellCheck="false" id="geneticcode" placeholder="Enter genetic code..." rows={20} cols={50}></textarea>
                </div>
            </div>
            <Link to="/setup">
                <img src={backButton} className="backButton" ></img>
            </Link>
            <img src={applyButton} className="applyButton" onClick={apply}></img>
            <div id="successWindow">
                <div>Success</div>
                <img src={okButton} className="okButton" onClick={ok}></img>
            </div>
            <div id="failWindow">
                <div>Fail</div>
                <img src={okButton} className="okButton" onClick={ok}></img>
            </div>
        </div >
    )
}

export default SetGeneticCodePage;