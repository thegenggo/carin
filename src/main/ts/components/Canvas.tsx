import React, { useEffect, useState, lazy, Suspense } from "react";
import "./Canvas.css"
import OrganismProps from "./OrganismProps";
import Cell from "./Cell";

function Canvas({ clearAllWindows, showMessage }: { clearAllWindows: () => void, showMessage: (message: string) => void }) {
    const [cells, setCells] = useState(<tbody>
        {Array(5).fill(0).map((_, rowIndex) => <tr key={rowIndex}>
            {Array(10).fill(0).map((_, colIndex) => <td key={colIndex}><div className="orphan-cell"></div></td>)}
        </tr>)}
    </tbody>)
    const SCROLL_SENSITIVITY = -0.0005

    const fetchHumanbody = () => {
        fetch("game/humanbody").then(response => response.json()).then((data: OrganismProps[][]) => {
            setCells(createCells(data))
        }).catch(error => {
            console.log(error)
        })
    }

    const createCells = (data: OrganismProps[][]) => {
        let top: number = 0
        let bottom: number = 0
        let orphanCell = <td><div className="orphan-cell"></div></td>
        let orphanRowCell = <tr>{data[0].length > 10 ? Array(data[0].length).fill(orphanCell):Array(10).fill(orphanCell) }</tr>
        let topOrphanCell: any[] = []
        let bottomOrphanCell: any[] = []
        if (data.length < 5) {
            top = Math.round((5 - data.length) / 2)
            bottom = 5 - data.length - top
            topOrphanCell = Array(top).fill(orphanRowCell)
            bottomOrphanCell = Array(bottom).fill(orphanRowCell)
        }
        return <tbody>
            {topOrphanCell}
            {data.map((row, i) => {
                let left: number = 0
                let right: number = 0
                let orphanCell = <td><div className="orphan-cell"></div></td>
                let leftOrphanCell: any[] = []
                let rightOrphanCell: any[] = []
                if (row.length < 10) {
                    left = Math.round((10 - row.length) / 2)
                    right = 10 - row.length - left
                    leftOrphanCell = Array(left).fill(orphanCell)
                    rightOrphanCell = Array(right).fill(orphanCell)
                }
                return <tr key={i}>
                    {leftOrphanCell}
                    {row.map((organism, j) => {
                        return <Cell key={j} organism={organism} showMessage={showMessage} i={i} j={j} />
                    })}
                    {rightOrphanCell}
                </tr>
            })}
            {bottomOrphanCell}
        </tbody>
    }

    useEffect(() => {
        let interval = setInterval(fetchHumanbody, 250);

        let humanbody = document.getElementById("humanbody")
        let canvas = document.getElementById("canvas")

        let cameraZoom = 1
        let lastZoom = cameraZoom
        let minZoom = 1
        let maxZoom = 1
        let mousePosition = { x: 0, y: 0 }
        let focusPosition = { x: 0, y: 0 }

        let cameraOffset = { x: 0, y: 0 }
        let dragStart = { x: 0, y: 0 }
        let isDragging = false
        let cameraOffsetLimit = { minX: 0, minY: 0, maxX: 0, maxY: 0 }

        let initialPinchDistance: any = null

        const getEventLocation = (event: any) => {
            if (event.touches && event.touches.length == 1) {
                return { x: event.touches[0].clientX, y: event.touches[0].clientY }
            } else {
                return { x: event.clientX, y: event.clientY }
            }
        }

        const adjustZoom = (zoomAmount: number, zoomFactor: number) => {
            if (humanbody.clientHeight < canvas.clientHeight || humanbody.clientWidth < canvas.clientWidth) return
            if (!isDragging) {
                minZoom = Math.max(canvas.clientWidth / humanbody.clientWidth, canvas.clientHeight / humanbody.clientHeight)
                let oldCameraZoom = cameraZoom
                if (zoomAmount) {
                    if (minZoom < cameraZoom + zoomAmount && cameraZoom + zoomAmount < maxZoom) cameraZoom = cameraZoom + zoomAmount
                    else if (cameraZoom + zoomAmount < minZoom) cameraZoom = minZoom
                    else if (cameraZoom + zoomAmount > maxZoom) cameraZoom = maxZoom
                    cameraOffset.x = cameraOffset.x - (mousePosition.x * (cameraZoom / oldCameraZoom) - mousePosition.x)
                    cameraOffset.y = cameraOffset.y - (mousePosition.y * (cameraZoom / oldCameraZoom) - mousePosition.y)
                }
                else if (zoomFactor) {
                    if (minZoom < zoomFactor * lastZoom && zoomFactor * lastZoom < maxZoom) cameraZoom = zoomFactor * lastZoom
                    else if (zoomFactor * lastZoom < minZoom) cameraZoom = minZoom
                    else if (zoomFactor * lastZoom > maxZoom) cameraZoom = maxZoom
                    cameraOffset.x = cameraOffset.x - (focusPosition.x * (cameraZoom / oldCameraZoom) - focusPosition.x)
                    cameraOffset.y = cameraOffset.y - (focusPosition.y * (cameraZoom / oldCameraZoom) - focusPosition.y)
                }
                update()
            }
        }

        const onPointerDown = (event: any) => {
            isDragging = true
            dragStart = getEventLocation(event)
        }

        const onPointerMove = (event: any) => {
            if (isDragging) {
                let dragEnd = getEventLocation(event)
                if (humanbody.clientWidth > canvas.clientWidth)
                    cameraOffset.x = cameraOffset.x + (dragEnd.x - dragStart.x)
                if (humanbody.clientHeight > canvas.clientHeight)
                    cameraOffset.y = cameraOffset.y + (dragEnd.y - dragStart.y)
                dragStart = dragEnd
                update()
            }
        }

        const onPointerUp = (event: any) => {
            isDragging = false
            initialPinchDistance = null
            lastZoom = cameraZoom
        }

        const handleTouch = (event: any, singleTouchHandler: any) => {
            if (event.touches.length === 1) {
                singleTouchHandler(event)
            } else if (event.type === "touchmove" && event.touches.length === 2) {
                isDragging = false
                handlePinch(event)
            }
        }

        const handlePinch = (event: any) => {
            event.preventDefault()
            let rect = humanbody.getBoundingClientRect()

            let touch1 = { x: event.touches[0].clientX, y: event.touches[0].clientY }
            let touch2 = { x: event.touches[1].clientX, y: event.touches[1].clientY }

            let currentDistance = Math.sqrt(Math.pow(touch1.x - touch2.x, 2) + Math.pow(touch1.y - touch2.y, 2))
            focusPosition.x = (touch1.x + touch2.x) / 2 - rect.left
            focusPosition.y = (touch1.y + touch2.y) / 2 - rect.top

            if (initialPinchDistance == null) {
                initialPinchDistance = currentDistance
            }
            else {
                adjustZoom(null, currentDistance / initialPinchDistance)
            }
        }

        const update = () => {
            if (humanbody && canvas) {
                cameraOffsetLimit.minX = canvas.clientWidth - humanbody.clientWidth * cameraZoom;
                cameraOffsetLimit.minY = canvas.clientHeight - humanbody.clientHeight * cameraZoom;

                cameraOffset.x = Math.max(cameraOffset.x, cameraOffsetLimit.minX)
                cameraOffset.x = Math.min(cameraOffset.x, cameraOffsetLimit.maxX)
                cameraOffset.y = Math.min(cameraOffset.y, cameraOffsetLimit.maxY)
                cameraOffset.y = Math.max(cameraOffset.y, cameraOffsetLimit.minY)

                humanbody.style.transform = `translate(${cameraOffset.x}px, ${cameraOffset.y}px) scale(${cameraZoom})`
                console.log(cameraOffset.x + " " + cameraOffset.y + " " + cameraZoom)
            };
        }

        canvas.addEventListener("wheel", (event: any) => {
            let rect = humanbody.getBoundingClientRect()
            mousePosition.x = event.clientX - rect.left
            mousePosition.y = event.clientY - rect.top
            adjustZoom(event.deltaY * SCROLL_SENSITIVITY, null)
        })

        canvas.addEventListener("pointerdown", event => { onPointerDown(event); clearAllWindows() })
        canvas.addEventListener("pointermove", onPointerMove)
        canvas.addEventListener("pointerup", onPointerUp)
        canvas.addEventListener("touchstart", (event) => { handleTouch(event, onPointerDown) })
        canvas.addEventListener("touchmove", (event) => { handleTouch(event, onPointerMove) })
        canvas.addEventListener("touchend", (event) => { handleTouch(event, onPointerUp) })
        window.addEventListener("resize", update)

        return () => { clearInterval(interval) }
    }, [])

    return (
        <div id="canvas">
            <table id="humanbody">
                {cells ? cells : null}
            </table>
        </div>
    );
}
export default Canvas;