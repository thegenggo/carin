package com.kwp.carin;

import com.kwp.parser.GeneticCode;

public class Gamma extends Virus {
    private static final GeneticCode defaultCode = GeneticCode.getBerserk();

    private Gamma(GeneticCode code) {
        super(code);
    }

    protected Gamma() {
        this(defaultCode);
    }

    public String toString() {
        return "Gamma";
    }
}
