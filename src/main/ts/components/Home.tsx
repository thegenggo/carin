import React from "react";
import gameName from './images/gameName.png'
import './Home.css'
import { Link } from "react-router-dom";

function Home (){
    return(
        <Link to={"setup"} className = "underline">
            <div className="home">
            <div className="center">
                <img src={gameName} className ="center-p" />
            </div>
            <h1 className="center">Click to start...</h1>
            
            </div>
        </Link>
        
    );
}
export default Home;