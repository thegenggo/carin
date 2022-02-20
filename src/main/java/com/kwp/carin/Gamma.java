package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.parser.Parser;

public class Gamma extends Virus {
    protected static GeneticCode geneticCode = GeneticCode.getVirusDefault();

    public static boolean setGeneticCode(GeneticCode code) {
        Parser parser = new Parser(code.getCode());
        if (parser.parse() == null) return false;
        geneticCode = code;
        return true;
    }

    private Gamma(GeneticCode code) {
        super(code);
    }

    protected Gamma() {
        this(geneticCode);
    }

    protected Virus getMutation() {
        return new Gamma();
    }

    public String toString() {
        return "Gamma";
    }
}
