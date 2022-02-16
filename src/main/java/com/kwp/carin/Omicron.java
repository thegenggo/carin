package com.kwp.carin;

import com.kwp.parser.GeneticCode;

public class Omicron extends Virus {
    private static final GeneticCode defaultCode = GeneticCode.getDefault();

    private Omicron(GeneticCode code) {
        super(code);
    }

    protected Omicron() {
        this(defaultCode);
    }

    public String toString() {
        return "Omicron";
    }
}
