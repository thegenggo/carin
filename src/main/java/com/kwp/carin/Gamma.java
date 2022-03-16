package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.parser.Parser;
import com.kwp.parser.SyntaxError;

public class Gamma extends Virus {
    protected static GeneticCode geneticCode = GeneticCode.getVirusDefault();

    public static void setGeneticCode(GeneticCode code) throws SyntaxError {
        Parser.parse(code);
        geneticCode = code;
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
