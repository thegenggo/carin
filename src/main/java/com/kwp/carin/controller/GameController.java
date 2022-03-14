package com.kwp.carin.controller;

import com.kwp.carin.*;
import com.kwp.parser.GeneticCode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {
    private final Game game = Game.getInstance();

    @RequestMapping("/start")
    public void start() {
        game.startGame();
    }

    @RequestMapping("/pause")
    public void pause() {
        game.pauseGame();
    }

    @RequestMapping("/resume")
    public void resume() {
        game.resumeGame();
    }

    @RequestMapping("/reset")
    public void reset() {
        game.resetGame();
    }

    @RequestMapping("/isover")
    public boolean isOver() {
        return game.isGameOver();
    }

    @RequestMapping("/increasespeed")
    public float increaseSpeed() {
        return game.increaseSpeed();
    }

    @RequestMapping("/decreasespeed")
    public float decreaseSpeed() {
        return game.decreaseSpeed();
    }

    @GetMapping("/humanbody")
    public Organism[][] humanbody() {
        Cell[][] cells = game.getHumanBody().getCells();
        Organism[][] organisms = new Organism[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                organisms[i][j] = cells[i][j].getOrganism();
            }
        }
        return organisms;
    }

    @GetMapping("/antibodycredit")
    public int antibodyCredit() {
        return game.getAntibodyCredit();
    }

    @RequestMapping("/buy/pfizer")
    public void buyPfizer(@RequestParam int i, @RequestParam int j) {
        game.buyAntibody(i, j, Antibody.Type.Pfizer);
    }

    @RequestMapping("/buy/moderna")
    public boolean buyMorderna(@RequestParam int i, @RequestParam int j) {
        return game.buyAntibody(i, j, Antibody.Type.Moderna);
    }

    @RequestMapping("/buy/sinovac")
    public boolean buySinovac(@RequestParam int i, @RequestParam int j) {
        return game.buyAntibody(i, j, Antibody.Type.Sinovac);
    }

    @RequestMapping("/setgeneticcode/alpha")
    public boolean setGeneticCodeAlpha(@RequestBody String code) {
        GeneticCode geneticCode = new GeneticCode(code);
        return Alpha.setGeneticCode(geneticCode);
    }

    @RequestMapping("/setgeneticcode/beta")
    public boolean setGeneticCodeBeta(@RequestBody String code) {
        GeneticCode geneticCode = new GeneticCode(code);
        return Beta.setGeneticCode(geneticCode);
    }

    @RequestMapping("/setgeneticcode/gamma")
    public boolean setGeneticCodeGamma(@RequestBody String code) {
        GeneticCode geneticCode = new GeneticCode(code);
        return Gamma.setGeneticCode(geneticCode);
    }

    @RequestMapping("/setgeneticcode/pfizer")
    public boolean setGeneticCodePfizer(@RequestBody String code) {
        GeneticCode geneticCode = new GeneticCode(code);
        return Pfizer.setGeneticCode(geneticCode);
    }

    @RequestMapping("/setgeneticcode/moderna")
    public boolean setGeneticCodeModerna(@RequestBody String code) {
        GeneticCode geneticCode = new GeneticCode(code);
        return Moderna.setGeneticCode(geneticCode);
    }

    @RequestMapping("/setgeneticcode/sinovac")
    public boolean setGeneticCodeSinovac(@RequestBody String code) {
        GeneticCode geneticCode = new GeneticCode(code);
        return Sinovac.setGeneticCode(geneticCode);
    }

    @RequestMapping("/select")
    public void select(@RequestParam int i, @RequestParam int j) {
        game.decideMove(i, j);
    }

    @RequestMapping("/move")
    public void move(@RequestParam int i, @RequestParam int j) {
        game.moveSelectedAntibody(i, j);
    }
}
