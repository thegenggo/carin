import React from "react";
import "./ShopWindow.css";
import pfizer from "./images/pfizer.png";
import moderna from "./images/moderna.png";
import sinovac from "./images/sinovac.png";
import cancel from "./images/cancel.png";

const ShopWindow = (props: any) => {
    return (
        <div className="shop-window">
            <img className="antibody" src={pfizer}></img>
            <img className="antibody" src={moderna}></img>
            <img className="antibody" src={sinovac}></img>
            <img className="cancel" src={cancel}></img>
        </div>
    )
}

export default ShopWindow;