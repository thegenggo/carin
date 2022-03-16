import React, { useEffect } from "react";
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

        if (geneticCode.length === 0) {
            createMessageBox("Please enter a genetic code");
            return;
        }

        fetch(`/game/setgeneticcode/${type}`, { body: geneticCode, method: "PUT" }).then(response => {
            return response.text();
        }).then((data: string) => {
            createMessageBox(data);
        }).catch(error => {
            createMessageBox(error);
        });
    }

    const createMessageBox = (data: string) => {
        let messageBox: HTMLElement = document.createElement("div");
        let message: HTMLElement = document.createElement("div");
        let okButtonElement: HTMLImageElement = document.createElement("img");
        okButtonElement.src = okButton;
        okButtonElement.className = "okButton";
        okButtonElement.addEventListener("click", () => {
            messageBox.remove();
        });
        message.innerHTML = data;
        messageBox.id = "messageBox";
        messageBox.appendChild(message);
        messageBox.appendChild(okButtonElement);
        document.getElementById("setGeneticCodePage").append(messageBox);
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

    const tab = (event: any) => {
        let textarea = event.target

        if (event.keyCode === 9) {
            event.preventDefault()

            textarea.setRangeText(
                '  ',
                textarea.selectionStart,
                textarea.selectionStart,
                'end'
            )
        }
    }

    useEffect(() => {
        let textarea = document.getElementById("geneticcode") as HTMLTextAreaElement;

        textarea.addEventListener('keydown', (e) => {
            if (e.keyCode === 9) {
                e.preventDefault()

                textarea.setRangeText(
                    '  ',
                    textarea.selectionStart,
                    textarea.selectionStart,
                    'end'
                )
            }
        })
    }, []);

    return (
        <div className="setGeneticCodePage" id="setGeneticCodePage">
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
                    <textarea spellCheck="false" id="geneticcode" placeholder="Enter genetic code..." rows={20} cols={50} onKeyDown={tab}></textarea>
                </div>
            </div>
            <Link to="/setup">
                <img src={backButton} className="backButton" ></img>
            </Link>
            <img src={applyButton} className="applyButton" onClick={apply}></img>
        </div >
    )
}

export default SetGeneticCodePage;