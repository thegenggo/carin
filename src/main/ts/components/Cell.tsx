import React, { useEffect } from 'react';
import OrganismProps from './OrganismProps';
import PfizerImage from './images/pfizer.png';
import SinovacImage from './images/sinovac.png';
import ModernaImage from './images/moderna.png';
import AlphaImage from './images/alpha.png';
import BetaImage from './images/beta.png';
import GammaImage from './images/gamma.png';
import './Cell.css';
import Cursor from './images/Polygon 1.png';

type CellProps = {
    organism: OrganismProps
    i: number;
    j: number;
}

const Cell = React.memo(({ organism, i, j }: CellProps) => {

    const render = () => {
        if (organism == null) return null;
        switch (organism.type) {
            case "Beta": return <img src={BetaImage} className="organismImage" draggable="false"></img>;
            case "Alpha": return <img src={AlphaImage} className="organismImage" draggable="false"></img>;
            case "Gamma": return <img src={GammaImage} className="organismImage" draggable="false"></img>;
            case "Pfizer": return <img src={PfizerImage} className="organismImage" draggable="false"></img>;
            case "Moderna": return <img src={ModernaImage} className="organismImage" draggable="false"></img>;
            case "Sinovac": return <img src={SinovacImage} className="organismImage" draggable="false"></img>;
            default: return <div></div>;
        }
    }

    const health = () => {
        if (organism == null) return null;
        return <div className="health">{organism.health}</div>;
    }

    const cursor = () => {
        if (organism == null) return null;
        if (organism.selected) {
            return <img src={Cursor} className="cursor"></img>;
        } else {
            return null;
        }
    }

    const drop = async (event: any) => {
        event.preventDefault();
        const data = event.dataTransfer.getData("text");
        let response
        if (data == "pfizer") {
            response = await fetch(`game/buy/pfizer?i=${i}&j=${j}`)
        } else if (data == "sinovac") {
            response = await fetch(`game/buy/sinovac?i=${i}&j=${j}`);
        } else if (data == "moderna") {
            response = await fetch(`game/buy/moderna?i=${i}&j=${j}`);
        }
        const result = await response.json();
        console.log(result);
    }

    const allowDrop = (event: any) => {
        event.preventDefault();
    }

    const select = (event: any) => {
        event.preventDefault();
        fetch(`game/select?i=${i}&j=${j}`);
    }

    console.log("rendering cell");

    return (
        <td>
            <div id="Cell" onDrop={drop} onDragOver={allowDrop} onTouchStart={select} onMouseDown={select}>
                <div className="organism">
                    {cursor()}
                    {render()}
                    {health()}
                </div>
            </div>
        </td>
    )
})

export default Cell;