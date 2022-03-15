import React from "react";
import gameName from './images/gameName.png'
import './Home.css'
import { Link } from "react-router-dom";

function Home() {
    return (
        <Link to={"setup"} className="underline">
            <div className="home">
                <div className="center">
                    <img src={gameName} className="center-p" />
                    <div className="clickme">Click to start...</div>
                </div>
            </div>
        </Link>

    );
}
export default Home;