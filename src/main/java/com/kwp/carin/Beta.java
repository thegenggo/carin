package com.kwp.carin;

import com.kwp.parser.GeneticCode;

public class Beta extends Virus {
    private static final GeneticCode defaultCode = GeneticCode.getBerserk();

    private Beta(GeneticCode code) {
        super(code);
    }

    protected Beta() {
        this(defaultCode);
    }

    public String toString() {
        return "Beta";
    }
}
