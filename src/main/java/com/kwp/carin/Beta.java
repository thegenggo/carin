package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.parser.Parser;

public class Beta extends Virus {
    protected static GeneticCode geneticCode = GeneticCode.getVirusDefault();

    public static boolean setGeneticCode(GeneticCode code) {
        Parser parser = new Parser(code);
        if (parser.parse() == null) return false;
        geneticCode = code;
        return true;
    }

    private Beta(GeneticCode code) {
        super(code);
    }

    protected Beta() {
        this(geneticCode);
    }

    public String toString() {
        return "Beta";
    }

    protected Virus getMutation() {
        return new Beta();
    }
}
