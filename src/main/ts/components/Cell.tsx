import React from 'react';
import OrganismProps from './OrganismProps';
import PfizerImage from './images/pfizer.png';
import SinovacImage from './images/sinovac.png';
import ModernaImage from './images/moderna.png';
import AlphaImage from './images/alpha.png';
import BetaImage from './images/beta.png';
import GammaImage from './images/gamma.png';
import './Cell.css';
import { AutomaticPrefetchPlugin } from 'webpack';

type CellProps = {
    organism: OrganismProps
    i: number;
    j: number;
}

const Cell = ({ organism, i, j }: CellProps) => {

    const buyAntibody = () => {
        console.log("buying antibody");
        fetch(`game/buy/pfizer?i=${i}&j=${j}`)
    }

    const render = () => {
        if (organism == null) return null;
        switch (organism.type) {
            case "Beta": return <img src={BetaImage} className="organismImage"></img>;
            case "Alpha": return <img src={AlphaImage} className="organismImage"></img>;
            case "Gamma": return <img src={GammaImage} className="organismImage"></img>;
            case "Pfizer": return <img src={PfizerImage} className="organismImage"></img>;
            case "Moderna": return <img src={ModernaImage} className="organismImage"></img>;
            case "Sinovac": return <img src={SinovacImage} className="organismImage"></img>;
            default: return <div></div>;
        }
    }

    const health = () => {
        if (organism == null) return null;
        return <div className="health">{organism.health}</div>;
    }

    return (
        <div id="Cell" onClick={buyAntibody}>
            <div className="organism">
                {render()}
                {health()}
            </div>
        </div>
    )
}
export default Cell;