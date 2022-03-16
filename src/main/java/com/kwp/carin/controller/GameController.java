package com.kwp.carin.controller;

import com.kwp.carin.*;
import com.kwp.parser.GeneticCode;
import com.kwp.parser.SyntaxError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {
    private final Game game = Game.getInstance();
    public static class StringResponse {
        private final String response;

        public StringResponse(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }
    }

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
    public int buyPfizer(@RequestParam int i, @RequestParam int j) {
        return game.buyAntibody(i, j, Antibody.Type.Pfizer);
    }

    @RequestMapping("/buy/moderna")
    public int buyMorderna(@RequestParam int i, @RequestParam int j) {
        return game.buyAntibody(i, j, Antibody.Type.Moderna);
    }

    @RequestMapping("/buy/sinovac")
    public int buySinovac(@RequestParam int i, @RequestParam int j) {
        return game.buyAntibody(i, j, Antibody.Type.Sinovac);
    }

    @RequestMapping("/setgeneticcode/alpha")
    public String setGeneticCodeAlpha(@RequestBody String code) {
        try {
            GeneticCode geneticCode = new GeneticCode(code);
            Alpha.setGeneticCode(geneticCode);
            return "success";
        } catch (SyntaxError e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/setgeneticcode/beta")
    public String setGeneticCodeBeta(@RequestBody String code) {
        try {
            GeneticCode geneticCode = new GeneticCode(code);
            Beta.setGeneticCode(geneticCode);
            return "success";
        } catch (SyntaxError e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/setgeneticcode/gamma")
    public String setGeneticCodeGamma(@RequestBody String code) {
        try {
            GeneticCode geneticCode = new GeneticCode(code);
            Gamma.setGeneticCode(geneticCode);
            return "success";
        } catch (SyntaxError e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/setgeneticcode/pfizer")
    public String setGeneticCodePfizer(@RequestBody String code) {
        System.out.println(code);
        try {
            GeneticCode geneticCode = new GeneticCode(code);
            Pfizer.setGeneticCode(geneticCode);
            return "success";
        } catch (SyntaxError e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/setgeneticcode/moderna")
    public String setGeneticCodeModerna(@RequestBody String code) {
        try {
            GeneticCode geneticCode = new GeneticCode(code);
            Moderna.setGeneticCode(geneticCode);
            return "success";
        } catch (SyntaxError e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/setgeneticcode/sinovac")
    public String setGeneticCodeSinovac(@RequestBody String code) {
        try {
            GeneticCode geneticCode = new GeneticCode(code);
            Sinovac.setGeneticCode(geneticCode);
            return "success";
        } catch (SyntaxError e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/select")
    public void select(@RequestParam int i, @RequestParam int j) {
        game.decideMove(i, j);
    }

    @RequestMapping("/move")
    public void move(@RequestParam int i, @RequestParam int j) {
        game.moveSelectedAntibody(i, j);
    }

    /** @return 1 when antibody win
     *          2 when virus win
     *          0 when no win */
    @RequestMapping("/check")
    public int check() {
        return game.checkWin();
    }
}
