import React, { useEffect, useState } from "react";
import "./Canvas.css"
import Cell from "./cell";
import OrganismProps from "./OrganismProps";

let canSend = true;

function Canvas() {
    const [cells, setCells] = useState<{ organism: OrganismProps }[][]>()
    const SCROLL_SENSITIVITY = -0.0005

    const fetchHumanbody = () => {
        if (canSend) {
            canSend = false;
            fetch("game/humanbody").then(response => response.json()).then(data => {
                setCells(data.cells);
                canSend = true;
            }).catch(error => {
                console.log(error);
            });
        }
    }

    useEffect(() => {
        let interval = setInterval(() => {
            fetchHumanbody();
        }, 10)

        let humanbody = document.getElementById("humanbody")
        let canvas = document.getElementById("canvas")

        let cameraZoom = 1
        let minZoom = 1
        let maxZoom = 1

        let cameraOffset = { x: 0, y: 0 }
        let isDragging = false
        let cameraOffsetLimit = { minX: 0, minY: 0, maxX: 0, maxY: 0 }

        const adjustZoom = (zoomAmount: number) => {
            minZoom = Math.max(canvas.clientWidth / humanbody.clientWidth, canvas.clientHeight / humanbody.clientHeight)
            if (minZoom < cameraZoom + zoomAmount && cameraZoom + zoomAmount < maxZoom) cameraZoom = cameraZoom + zoomAmount
            else if (cameraZoom + zoomAmount < minZoom) cameraZoom = minZoom
            else if (cameraZoom + zoomAmount > maxZoom) cameraZoom = maxZoom
            update()
        }

        const onPointerDown = (event: any) => {
            isDragging = true
        }

        const onPointerMove = (event: any) => {
            if (isDragging) {
                cameraOffset.x = cameraOffset.x + event.movementX
                cameraOffset.y = cameraOffset.y + event.movementY
            }
            update()
        }

        const onPointerUp = (event: any) => {
            isDragging = false
        }

        const update = () => {
            if (humanbody && canvas) {

                cameraOffsetLimit.minX = canvas.clientWidth - humanbody.clientWidth * cameraZoom;
                cameraOffsetLimit.minY = canvas.clientHeight - humanbody.clientHeight * cameraZoom;

                cameraOffset.x = Math.min(cameraOffset.x, cameraOffsetLimit.maxX)
                cameraOffset.y = Math.min(cameraOffset.y, cameraOffsetLimit.maxY)
                cameraOffset.x = Math.max(cameraOffset.x, cameraOffsetLimit.minX)
                cameraOffset.y = Math.max(cameraOffset.y, cameraOffsetLimit.minY)

                humanbody.style.transform = `translate(${cameraOffset.x}px, ${cameraOffset.y}px) scale(${cameraZoom})`;
            };
        }

        canvas.addEventListener("wheel", (event: any) => {
            adjustZoom(event.deltaY * SCROLL_SENSITIVITY)
        })
        canvas.addEventListener("pointerdown", onPointerDown)
        canvas.addEventListener("pointermove", onPointerMove)
        canvas.addEventListener("pointerup", onPointerUp)
        window.addEventListener("resize", update)

        return () => { clearInterval(interval) }
    }, [])

    return (
        <div id="canvas">
            <table id="humanbody">
                <tbody>
                    {cells ? cells.map((row, i: number) =>
                        <tr key={i}>
                            {row.map((cell, j: number) =>
                                <Cell key={j} organism={cell.organism} i={i} j={j} />
                            )}
                        </tr>
                    ) : <div>Loading...</div>}
                </tbody>
            </table>
        </div>
    );
}
export default Canvas;