package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.parser.Parser;

public class Sinovac extends Antibody {
    private static GeneticCode geneticCode = GeneticCode.getAntibodyDefault();

    public static boolean setGeneticCode(GeneticCode code) {
        Parser parser = new Parser(code);
        if (parser.parse() == null) return false;
        geneticCode = code;
        return true;
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
