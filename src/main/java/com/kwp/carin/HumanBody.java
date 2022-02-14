package com.kwp.carin;

import com.kwp.util.Pair;

import java.util.LinkedList;

public class HumanBody {
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

    public LinkedList<Cell> getEmptyCells() {
        LinkedList<Cell> cells = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (this.cells[i][j].isEmpty()) cells.add(this.cells[i][j]);
            }
        }
        return cells;
    }

    private static HumanBody instance;

    public static HumanBody getInstance() {
        if (instance == null) {
            Configuration config = Configuration.getInstance();
            Pair<Integer, Integer> dimension = config.getDimension();
            instance = new HumanBody(dimension.fst(), dimension.snd());
        }
        return instance;
    }
}
