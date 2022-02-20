package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.parser.Parser;

public class Moderna extends Antibody {
    private static GeneticCode geneticCode = GeneticCode.getAntibodyDefault();

    public static boolean setGeneticCode(GeneticCode code) {
        Parser parser = new Parser(code.getCode());
        if (parser.parse() == null) return false;
        geneticCode = code;
        return true;
    }

    private Moderna(GeneticCode code) {
        super(code);
    }

    protected Moderna() {
        this(geneticCode);
    }

    public String toString() {
        return "Moderna";
    }
}
