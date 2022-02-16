package com.kwp.carin;

import com.kwp.parser.GeneticCode;

public class Alpha extends Virus {
    private static final GeneticCode defaultCode = GeneticCode.getDefault();

    private Alpha(GeneticCode code) {
        super(code);
    }

    protected Alpha() {
        this(defaultCode);
    }

    public String toString() {
        return "Omicron";
    }
}
