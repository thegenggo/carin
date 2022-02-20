import React from 'react';
import Bar from './Bar';
import Canvas from './Canvas';
import './Gameplay.css';

function Gameplay() {
    return (
        <div className="gameplay">
            <Bar/>
            <Canvas/>
        </div>
    )
}

export default Gameplay;