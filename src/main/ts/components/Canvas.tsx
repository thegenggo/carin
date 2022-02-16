import React from "react";
import "./Canvas.css"
import Cell from "./cell";

function Canvas  () {
    return(
        <div id="grid">
            <Cell />
            <Cell />
            <Cell />
            <Cell />
            <Cell />
            <Cell />
            <Cell />
            <Cell />
        </div>

    );
}
export default Canvas;