package com.kwp.carin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kwp.util.Pair;

import java.util.LinkedList;

public class HumanBody {
    private static HumanBody instance;

    public static HumanBody getInstance() {
        if (instance == null) {
            Pair<Integer, Integer> dimension = Configuration.getDimension();
            instance = new HumanBody(dimension.fst(), dimension.snd());
        }
        return instance;
    }

    private final Cell[][] cells;
    private final int m, n;

    private HumanBody(int m, int n) {
        this.m = m; this.n = n;
        cells = new Cell[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Cell getCell(int i, int j) {
        if (i < 0 || i >= m || j < 0 || j >= n) return null;
        return cells[i][j];
    }

    /** @return empty cells in human body*/
    @JsonIgnore
    public LinkedList<Cell> getEmptyCells() {
        LinkedList<Cell> cells = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (this.cells[i][j].isEmpty()) cells.add(this.cells[i][j]);
            }
        }
        return cells;
    }

    public void reset() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                cells[i][j].clear();
            }
        }
    }

    public void print() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(cells[i][j]);
            }
            System.out.println();
        }
        System.out.println("----------------------------------------");
    }
}
