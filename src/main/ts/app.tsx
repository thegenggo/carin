import { useEffect, useState } from 'react';
import React from 'react';
import ReactDOM from 'react-dom';
import Bar from './components/Bar';
import './App.css'
import Canvas from './components/canvas';

function App() {

    return (
        <div id="App">
            {/* <Bar />
            <Canvas /> */}
        </div>
    );
}

ReactDOM.render(<App />, document.getElementById('react'));
