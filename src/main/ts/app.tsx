import React from 'react';
import './App.css'
import SetGeneticCodePage from './components/SetGeneticCodePage';
import SetupPage from './components/SetupPage';
import { Route, Routes } from 'react-router-dom';

function App() {

    return (
        <div className="App">
            <Routes>
                <Route path="/" element={<SetupPage />} />
                <Route path="setup/pfizer" element={<SetGeneticCodePage type="pfizer"/>} />
                <Route path="setup/moderna" element={<SetGeneticCodePage type="moderna"/>} />
                <Route path="setup/sinovac" element={<SetGeneticCodePage type="sinovac"/>} />
                <Route path="setup/alpha" element={<SetGeneticCodePage type="alpha"/>} />
                <Route path="setup/beta" element={<SetGeneticCodePage type="beta"/>} />
                <Route path="setup/gamma" element={<SetGeneticCodePage type="gamma"/>} />
            </Routes>
        </div>
    );
}

export default App;