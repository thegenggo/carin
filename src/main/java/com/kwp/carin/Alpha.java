package com.kwp.carin;

import com.kwp.parser.GeneticCode;

public class Alpha extends Virus {
    private static GeneticCode geneticCode = GeneticCode.getVirusDefault();

    private Alpha(GeneticCode code) {
        super(code);
    }

    protected Alpha() {
        this(geneticCode);
    }

    protected Virus getMutation() {
        return new Alpha();
    }

    public String toString() {
        return "Alpha";
    }
}
