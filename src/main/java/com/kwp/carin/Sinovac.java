package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.parser.Parser;
import com.kwp.parser.SyntaxError;

public class Sinovac extends Antibody {
    private static GeneticCode geneticCode = GeneticCode.getAntibodyDefault();

    public static void setGeneticCode(GeneticCode code) throws SyntaxError {
        Parser.parse(code);
        geneticCode = code;
    }

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
