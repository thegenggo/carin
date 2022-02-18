import React, { useEffect, useState } from "react";
import "./Canvas.css"
import Cell from "./cell";
import OrganismProps from "./OrganismProps";

function Canvas() {
    const [cells, setCells] = useState<{organism: OrganismProps}[][]>([]);
    const [cameraZoom, setCameraZoom] = useState(1);
    const [cameraOffset, setCameraOffset] = useState({ x: 0, y: 0 });
    const [dragStart, setDragStart] = useState({ x: 0, y: 0 });
    const [isDragging, setIsDragging] = useState(false);
    const [humanbody, setHumanbody] = useState<HTMLElement | undefined>();
    const [canvas, setCanvas] = useState<HTMLElement | undefined>();
    const SCROLL_SENSITIVITY = -0.0005;
    const [cameraOffsetLimit, setCameraOffsetLimit] = useState<{ minX: number, minY: number, maxX: number, maxY: number }>({ minX: 0, minY: 0, maxX: 0, maxY: 0 });
    const [minZoom, setMinZoom] = useState(1);
    const [maxZoom, setMaxZoom] = useState(1);

    const fetchHumanbody = async () => {
        fetch("game/humanbody", {signal: Timeout(1).signal}).then(response => response.json()).then(data => {
            setCells(data.cells);
        });
    }

    const Timeout = (time: number) => {
        let controller = new AbortController();
        setTimeout(() => controller.abort(), time * 1000);
        return controller;
    };

    const adjustZoom = (zoomAmount: number) => {
        console.log("minZoom: " + minZoom)
        if (minZoom < cameraZoom + zoomAmount && cameraZoom + zoomAmount < maxZoom) setCameraZoom(cameraZoom + zoomAmount)
        else if (cameraZoom + zoomAmount < minZoom) setCameraZoom(minZoom)
        else if (cameraZoom + zoomAmount > maxZoom) setCameraZoom(maxZoom)
    }

    const onPointerDown = (event: any) => {
        setIsDragging(true);
        setDragStart({ x: event.clientX, y: event.clientY });
    }

    const onPointerMove = (event: any) => {
        if (isDragging) {
            const newOffset = {
                x: cameraOffset.x + event.movementX,
                y: cameraOffset.y + event.movementY
            }
            setCameraOffset(newOffset);
        }
    }

    const onPointerUp = (event: any) => {
        setIsDragging(false);
    }

    useEffect(() => {
        setHumanbody(document.getElementById("humanbody"))
        setCanvas(document.getElementById("canvas"));
        window.addEventListener("resize", () => update());
        if (humanbody && canvas) setMinZoom(Math.max(canvas.clientWidth / humanbody.clientWidth, canvas.clientHeight / humanbody.clientHeight))
    })

    useEffect(() => {
        const interval = setInterval(() => {
          fetchHumanbody();
        }, 100)
        return () => clearInterval(interval)
      }, [])


    useEffect(() => {
        update()
    }, [cameraOffset, cameraZoom]);

    const update = async () => {
        if (humanbody && canvas) {
            cameraOffsetLimit.minX = canvas.clientWidth - humanbody.clientWidth * cameraZoom;
            cameraOffsetLimit.minY = canvas.clientHeight - humanbody.clientHeight * cameraZoom;

            cameraOffset.x = Math.min(cameraOffset.x, cameraOffsetLimit.maxX);
            cameraOffset.y = Math.min(cameraOffset.y, cameraOffsetLimit.maxY);
            cameraOffset.x = Math.max(cameraOffset.x, cameraOffsetLimit.minX);
            cameraOffset.y = Math.max(cameraOffset.y, cameraOffsetLimit.minY);

            humanbody.style.top = `${cameraOffset.y}px`;
            humanbody.style.left = `${cameraOffset.x}px`;
            humanbody.style.transform = `scale(${cameraZoom})`;
        };
    }

    return (
        <div id="canvas" onWheel={(e) => adjustZoom(e.deltaY * SCROLL_SENSITIVITY)} onMouseDown={(e) => onPointerDown(e)} onMouseUp={(e) => onPointerUp(e)}
            onMouseMove={(e) => onPointerMove(e)} onMouseLeave={(e) => onPointerUp(e)}>
            <table id="humanbody">
                <tbody>
                    {cells.map((row, i: number) =>
                        <tr>
                            {row.map((cell, j: number) =>
                                <td>
                                    <Cell organism={cell.organism} i={i} j={j} />
                                </td>
                            )}
                        </tr>
                    )}
                </tbody>
            </table>
        </div>
    );
}
export default Canvas;