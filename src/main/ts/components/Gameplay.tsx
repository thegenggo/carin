import React from 'react';
import Bar from './Bar';
import Canvas from './Canvas';
import './Gameplay.css';
import ResetWindow from './ResetWindow';
import ShopWindow from './ShopWindow';

function Gameplay() {
    const [showResetWindow, setShowResetWindow] = React.useState(false);
    const [showShopWindow, setShowShopWindow] = React.useState(false);

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

    return (
        <div className="gameplay">
            <Bar openResetConfirmWindow={openResetConfirmWindow} openShopWindow={openShopWindow}/>
            <Canvas clearAllWindows={clearAllWindows}/>
            {showResetWindow && <ResetWindow clearAllWindows={clearAllWindows}/>}
            {showShopWindow && <ShopWindow clearAllWindows={clearAllWindows}/>}
        </div>
    )
}

export default Gameplay;