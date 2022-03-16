package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.parser.Parser;
import com.kwp.parser.Program;
import com.kwp.parser.SyntaxError;

public class Pfizer extends Antibody {
    private static GeneticCode geneticCode = GeneticCode.getAntibodyDefault();

    public static void setGeneticCode(GeneticCode code) throws SyntaxError {
        Program program = Parser.parse(code);
        geneticCode = code;
    }

    private Pfizer(GeneticCode code) {
        super(code);
    }

    protected Pfizer() {
        this(geneticCode);
    }

    public String toString() {
        return "Pfizer";
    }
}
