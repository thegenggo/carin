package com.kwp.carin;

import com.kwp.parser.GeneticCode;

public class Sinovac extends Antibody {
    private static GeneticCode geneticCode = GeneticCode.getAntibodyDefault();

    private Sinovac(GeneticCode code) {
        super(code);
    }

    protected Sinovac() {
        this(geneticCode);
    }

    public String toString() {
        return "Sinovac";
    }
}
