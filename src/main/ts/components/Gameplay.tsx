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
    const [disable, setDisable] = useState(false);
    const [timer, setTimer] = useState<NodeJS.Timeout>();

    let navigate = useNavigate()

    const openResetConfirmWindow = () => {
        if (disable) return
        clearAllWindows();
        setShowResetWindow(true);
    }

    const clearAllWindows = () => {
        setShowResetWindow(false);
        setShowShopWindow(false);
    }

    const openShopWindow = () => {
        if (disable) return
        clearAllWindows();
        setShowShopWindow(true);
    }

    const fetchCheckWin = () => {
        fetch("game/check").then(response => response.json()).then(data => {
            if (data === 1) {
                setDisable(true)
                setWin(true)
            } else if (data === 2) {
                setDisable(true)
                setLose(true)
            }
        }).catch(error => {
            console.log(error)
        })
    }

    const showMessage = (message: string) => {
        console.log("message: " + message)
        let messageElement = document.getElementById("message")
        if(messageElement) {

        } else {
            messageElement = document.createElement("div")
            messageElement.id = "message"
            document.body.appendChild(messageElement)
        }
        messageElement.innerHTML = message
        clearTimeout(timer)
        setTimer(setTimeout(() => {
            messageElement.innerHTML = ""
        }, 1000))
    }

    useEffect(() => {
        let interval = setInterval(fetchCheckWin, 250)
        return () => {
            clearInterval(interval)
            clearTimeout(timer)
        }
    }, [])

    useEffect(() => {
        if (disable === true) {
            setTimeout(() => {
                navigate("/")
                fetch("game/reset")
            }, 4500)
        }
    }, [disable])

    return (
        <div className="gameplay">
            <Bar openResetConfirmWindow={openResetConfirmWindow} openShopWindow={openShopWindow} disable={disable} showMessage={showMessage}/>
            <Canvas clearAllWindows={clearAllWindows} showMessage={showMessage}/>
            <ResetWindow show={showResetWindow} clearAllWindows={clearAllWindows} />
            <ShopWindow show={showShopWindow} clearAllWindows={clearAllWindows} />
            {win ? <img className="win" src={gameWin} draggable="false" /> : null}
            {lose ? <img className="lose" src={gameOver} draggable="false" /> : null}
        </div>
    )
}

export default Gameplay;