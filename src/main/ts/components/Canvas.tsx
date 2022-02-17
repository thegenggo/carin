import React from "react";
import "./Canvas.css"
import Cell from "./cell";
import {Store} from "../store/Store"




function Canvas  () {
    
    return(
        <div>
       <div id="grid">
            <table>
                <tbody>

                </tbody>
            </table>
            <Cell />
            <Cell />
            <Cell />
            <Cell />
            <Cell />
            <Cell />
            <Cell />
            <Cell />
            
        </div>

        </div>
 

    );
}
export default Canvas;