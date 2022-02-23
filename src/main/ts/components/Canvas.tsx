import React, { useEffect, useState, lazy, Suspense} from "react";
import { transform } from "typescript";
import "./Canvas.css"
//import Cell from "./cell";
import OrganismProps from "./OrganismProps";

const Cell = lazy(() => import("./Cell"))

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
        let mousePosition = { x: 0, y: 0 }

        let cameraOffset = { x: 0, y: 0 }
        let isDragging = false
        let cameraOffsetLimit = { minX: 0, minY: 0, maxX: 0, maxY: 0 }

        const adjustZoom = (zoomAmount: number) => {
            minZoom = Math.max(canvas.clientWidth / humanbody.clientWidth, canvas.clientHeight / humanbody.clientHeight)
            let oldCameraZoom = cameraZoom
            if (minZoom < cameraZoom + zoomAmount && cameraZoom + zoomAmount < maxZoom) cameraZoom = cameraZoom + zoomAmount
            else if (cameraZoom + zoomAmount < minZoom) cameraZoom = minZoom
            else if (cameraZoom + zoomAmount > maxZoom) cameraZoom = maxZoom
            cameraOffset.x = cameraOffset.x - (mousePosition.x * (cameraZoom / oldCameraZoom) - mousePosition.x)
            cameraOffset.y = cameraOffset.y - (mousePosition.y * (cameraZoom / oldCameraZoom) - mousePosition.y)
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

                humanbody.style.transform = `translate(${cameraOffset.x}px, ${cameraOffset.y}px) scale(${cameraZoom})`
            };
        }

        canvas.addEventListener("wheel", (event: any) => {
            let rect = humanbody.getBoundingClientRect()
            mousePosition.x = event.clientX - rect.left
            mousePosition.y = event.clientY - rect.top
            console.log(mousePosition.x, mousePosition.y)
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
                            <Suspense fallback={<div>Loading...</div>}>
                                <Cell key={j} organism={cell.organism} i={i} j={j} />
                            </Suspense>
                            )}
                        </tr>
                    ) : <div>Loading...</div>}
                </tbody>
            </table>
        </div>
    );
}
export default Canvas;