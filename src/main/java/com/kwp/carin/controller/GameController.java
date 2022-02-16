package com.kwp.carin.controller;

import com.kwp.carin.Alpha;
import com.kwp.carin.Game;
import com.kwp.carin.HumanBody;
import com.kwp.parser.GeneticCode;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/humanbody")
    public HumanBody humanbody() {
        return game.getHumanBody();
    }

    @RequestMapping("/select")
    public void select(@RequestParam int i, @RequestParam int j) {
        game.selectAntibody(i, j);
    }

    @RequestMapping("/move")
    public void move(@RequestParam int i, @RequestParam int j) {
        game.moveSelectedAntibody(i, j);
    }

    @RequestMapping("/setgeneticcode/alpha")
    public boolean setGeneticCodeAlpha(@RequestBody String code) {
        GeneticCode geneticCode = new GeneticCode(code);
        return Alpha.setGeneticCode(geneticCode);
    }

    @RequestMapping("/setgeneticcode/beta")
    public boolean setGeneticCodeBeta(@RequestBody String code) {
        GeneticCode geneticCode = new GeneticCode(code);
        return Alpha.setGeneticCode(geneticCode);
    }

    @RequestMapping("/setgeneticcode/gamma")
    public boolean setGeneticCodeGamma(@RequestBody String code) {
        GeneticCode geneticCode = new GeneticCode(code);
        return Alpha.setGeneticCode(geneticCode);
    }
}
