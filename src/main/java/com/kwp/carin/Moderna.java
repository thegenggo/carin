package com.kwp.carin;

import com.kwp.parser.GeneticCode;

public class Moderna extends Antibody {
    private static GeneticCode geneticCode = GeneticCode.getAntibodyDefault();

    private Moderna(GeneticCode code) {
        super(code);
    }

    protected Moderna() {
        this(geneticCode);
    }

    public String toString() {
        return "Pfizer";
    }
}
