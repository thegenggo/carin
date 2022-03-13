import React from 'react';
import ReactDOM from "react-dom";
import { BrowserRouter } from 'react-router-dom';
import App from './App';
const webmanifest = require('./manifest.webmanifest');

ReactDOM.render(<BrowserRouter><App /></BrowserRouter>, document.getElementById("react"));

if ("serviceWorker" in navigator) {
    navigator.serviceWorker.register("./sw.js").then(function(registration) {
        console.log("Service Worker Registered");
        console.log(registration);
    }).catch(function(err) {
        console.log("Service Worker Failed to Register", err);
    });
}