package com.kwp.carin;

import com.kwp.parser.GeneticCode;

public class Alpha extends Virus {
    private static GeneticCode geneticCode = GeneticCode.getDefault();

    protected Alpha(GeneticCode code) {
        super(code);
    }

    protected Alpha() {
        this(geneticCode);
    }

    public String toString() {
        return "Alpha";
    }
}
