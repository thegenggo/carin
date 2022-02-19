import React from "react";
import './SetGeneticCodePage.css';
import backButton from './images/backButton.png';
import applyButton from './images/applyButton.png';
import pfizer from './images/pfizer.png';

function SetGeneticCodePage({type}: {type: string}) {
    
    const apply = () => {
        let textarea = document.getElementById("geneticcode") as HTMLTextAreaElement;
        let geneticCode = textarea.value;

        fetch(`/game/setgeneticcode/${type}`, {body: geneticCode, method: "POST"}).then(response => {
            return response.json();
        }).then((data: boolean) => {
            if (data === true) alert("Genetic code applied successfully");
            else alert("Genetic code is invalid");
        }).catch(error => {
            console.log(error);
        });
    }

    const back = () => {
        window.location.href = "/";
    }

    return (
        <div className="setGeneticCodePage">
            <div className="organismPortrait">
                <img src={pfizer} className="pfizer"></img>
            </div>
            <h1>Set Genetic Code</h1>
            <textarea id="geneticcode" rows={20} cols={50}></textarea>
            <img src={backButton} className="backButton" onClick={back}></img>
            <img src={applyButton} className="applyButton" onClick={apply}></img>
        </div>
    )
}

export default SetGeneticCodePage;