package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.parser.Parser;
import com.kwp.parser.SyntaxError;

public class Alpha extends Virus {
    protected static GeneticCode geneticCode = GeneticCode.getVirusDefault();

    public static void setGeneticCode(GeneticCode code) throws SyntaxError {
        Parser.parse(code);
        geneticCode = code;
    }

    private Alpha(GeneticCode code) {
        super(code);
    }

    protected Alpha() {
        this(geneticCode);
    }

    protected Virus getMutation() {
        return new Alpha();
    }

    public String toString() {
        return "Alpha";
    }
}
