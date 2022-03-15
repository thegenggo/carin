import React, { useEffect, useState } from 'react';
import Bar from './Bar';
import Canvas from './Canvas';
import './Gameplay.css';
import ResetWindow from './ResetWindow';
import ShopWindow from './ShopWindow';
import gameOver from './images/gameOver.png';
import gameWin from './images/gameWin.png';
import { useNavigate } from 'react-router-dom';

function Gameplay() {
    const [showResetWindow, setShowResetWindow] = useState(false);
    const [showShopWindow, setShowShopWindow] = useState(false);
    const [win, setWin] = useState(false);
    const [lose, setLose] = useState(false);

    let navigate = useNavigate()

    const openResetConfirmWindow = () => {
        console.log("Reset button clicked");
        clearAllWindows();
        setShowResetWindow(true);
    }

    const clearAllWindows = () => {
        setShowResetWindow(false);
        setShowShopWindow(false);
    }

    const openShopWindow = () => {
        console.log("Shop button clicked");
        clearAllWindows();
        setShowShopWindow(true);
    }

    const fetchCheckWin = () => {
        fetch("game/check").then(response => response.json()).then(data => {
            if (data === 1) {
                setWin(true)
                setTimeout(() => {
                    navigate("/")
                    console.log("You win!")
                }, 3500)
            } else if (data === 2) {
                setLose(true)
                setTimeout(() => {
                    navigate("/")
                    console.log("You lose!")
                }, 3500)
            }
        }).catch(error => {
            console.log(error)
        })
    }

    useEffect(() => {
        let interval = setInterval(fetchCheckWin, 250);
        return () => clearInterval(interval);
    }, [])

    return (
        <div className="gameplay">
            <Bar openResetConfirmWindow={openResetConfirmWindow} openShopWindow={openShopWindow}/>
            <Canvas clearAllWindows={clearAllWindows}/>
            <ResetWindow show={showResetWindow} clearAllWindows={clearAllWindows}/>
            <ShopWindow show={showShopWindow} clearAllWindows={clearAllWindows}/>
            {win ? <img className="win" src={gameWin} draggable="false"/>: null}
            {lose ? <img className="lose" src={gameOver} draggable="false"/>: null}
        </div>
    )
}

export default Gameplay;