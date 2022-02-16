package com.kwp.carin;

import com.kwp.parser.GeneticCode;

public class Gamma extends Virus {
    private static GeneticCode geneticCode = GeneticCode.getBerserk();

    private Gamma(GeneticCode code) {
        super(code);
    }

    protected Gamma() {
        this(geneticCode);
    }

    public String toString() {
        return "Gamma";
    }
}
