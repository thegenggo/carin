import React from 'react';
import OrganismProps from './OrganismProps';
import PfizerImage from './images/pfizer.png';
import SinovacImage from './images/sinovac.png';
import ModernaImage from './images/moderna.png';
import AlphaImage from './images/alpha.png';
import BetaImage from './images/beta.png';
import GammaImage from './images/gamma.png';
import './Cell.css';

type CellProps = {
    organism: OrganismProps
    i: number;
    j: number;
}

const Cell = ({organism, i, j}: CellProps) => {

    const buyAntibody = async () => {
        console.log("buying antibody");
        fetch(`game/buy/pfizer?i=${i}&j=${j}`)
    }

    const render = () => {
        if (organism == null) return null;
        switch (organism.type) {
            case "Beta": return <img src={BetaImage}></img>;
            case "Alpha": return <img src={AlphaImage}></img>;
            case "Gamma": return <img src={GammaImage}></img>;
            case "Pfizer": return <img src={PfizerImage}></img>;
            case "Moderna": return <img src={ModernaImage}></img>;
            case "Sinovac" : return <img src={SinovacImage}></img>;
            default: return <div></div>;
        }
    }

    return(
        <div id="Cell" onClick={buyAntibody}>{render()}</div>
    )
}
export default Cell;