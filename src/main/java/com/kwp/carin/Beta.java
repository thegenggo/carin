package com.kwp.carin;

import com.kwp.parser.GeneticCode;

public class Beta extends Virus {
    private static GeneticCode geneticCode = GeneticCode.getDefault();

    private Beta(GeneticCode code) {
        super(code);
    }

    protected Beta() {
        this(geneticCode);
    }

    public String toString() {
        return "Beta";
    }
}
