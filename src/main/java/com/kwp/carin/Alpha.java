package com.kwp.carin;

import com.kwp.parser.GeneticCode;
import com.kwp.parser.Parser;

public class Alpha extends Virus {
    protected static GeneticCode geneticCode = GeneticCode.getVirusDefault();

    public static boolean setGeneticCode(GeneticCode code) {
        Parser parser = new Parser(code);
        if (parser.parse() == null) return false;
        geneticCode = code;
        return true;
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
