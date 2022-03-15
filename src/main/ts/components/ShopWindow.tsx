import React from "react";
import "./ShopWindow.css";
import pfizer from "./images/pfizer.png";
import moderna from "./images/moderna.png";
import sinovac from "./images/sinovac.png";
import cancel from "./images/cancel.png";

const ShopWindow = ({ show, clearAllWindows }: { show: boolean, clearAllWindows: () => void }) => {

    const drag = (event: any) => {
        setTimeout(clearAllWindows, 10);
        let img = new Image();
        img.src = event.target.src;
        event.dataTransfer.setDragImage(img, img.width / 2, img.height / 2);
        event.dataTransfer.dropEffect = "move";
        event.dataTransfer.effectAllowed = "move";
        event.dataTransfer.setData("text", event.target.id);
    }

    return (
        <div id="shop-window" className="shop-window" style={{ visibility: show ? "visible" : "hidden" }}>
            <img className="antibody" id="pfizer" src={pfizer} draggable={true} onDragStart={drag}></img>
            <img className="antibody" id="moderna" src={moderna} draggable={true} onDragStart={drag}></img>
            <img className="antibody" id="sinovac" src={sinovac} draggable={true} onDragStart={drag}></img>
            <img className="cancel" src={cancel} draggable="false" onClick={clearAllWindows}></img>
        </div>
    )
}

export default ShopWindow;