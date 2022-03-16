package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.parser.Parser;
import com.kwp.parser.Program;
import com.kwp.parser.SyntaxError;

public class Beta extends Virus {
    protected static GeneticCode geneticCode = GeneticCode.getVirusDefault();

    public static void setGeneticCode(GeneticCode code) throws SyntaxError {
        Program program = Parser.parse(code);
        geneticCode = code;
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
