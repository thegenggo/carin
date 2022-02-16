import { useEffect, useState } from 'react';
import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import Bar from './components/Bar';
import AntibodyCredit from './components/AntibodyCredit';
import './App.css'
import Canvas from './components/canvas';

function App() {



    return ( 
    <div id="App" >
        <Bar />
        <Canvas />
    </div>
    );
}

ReactDOM.render(<App />, document.getElementById('react'));
