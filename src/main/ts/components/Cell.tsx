import cell from "./images/cell.png"
import React from 'react';

type CellProps = {
    x: number;
    y: number;
  }

const Cell = () => {
    return(
        <img src = {cell}></img>
    );
}
export default Cell;