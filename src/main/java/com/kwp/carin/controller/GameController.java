package com.kwp.carin.controller;

import com.kwp.carin.Game;
import com.kwp.carin.HumanBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {
    private Game game;

    @RequestMapping("/start")
    public void start() {
        game = Game.getInstance();
        game.startGame();
    }

    @RequestMapping("/resume")
    public void resume() {
        game.resumeGame();
    }

    @RequestMapping("/pause")
    public void pause() {
        game.pauseGame();
    }

    @RequestMapping("/buy/antibody")
    public void buyAntibody(@RequestParam int i, @RequestParam int j) {
        game.buyAntibody(i, j);
    }

    @RequestMapping("/humanbody")
    public HumanBody humanbody() {
        return game.getHumanBody();
    }

    @RequestMapping("/select")
    public void select(@RequestParam int i, @RequestParam int j) {
        game.selectAntibody(i, j);
    }

    @RequestMapping("move")
    public void move(@RequestParam int i, @RequestParam int j) {
        game.moveSelectedAntibody(i, j);
    }
}
