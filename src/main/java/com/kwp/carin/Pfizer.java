package com.kwp.carin;

import com.kwp.parser.GeneticCode;

public class Pfizer extends Antibody {
    private static GeneticCode geneticCode = GeneticCode.getAntibodyDefault();

    private Pfizer(GeneticCode code) {
        super(code);
    }

    protected Pfizer() {
        this(geneticCode);
    }

    public String toString() {
        return "Pfizer";
    }
}
