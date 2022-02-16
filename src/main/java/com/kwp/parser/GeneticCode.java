package com.kwp.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class GeneticCode {
    private final String code;

    public GeneticCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneticCode that = (GeneticCode) o;
        return code.equals(that.code);
    }

    public int hashCode() {
        return Objects.hash(code);
    }

    public static GeneticCode getDefault() {
        return getGeneticCodeFromFile("src/main/java/com/kwp/parser/geneticcodes/default.txt");
    }

    public static GeneticCode getBerserk() {
        return getGeneticCodeFromFile("src/main/java/com/kwp/parser/geneticcodes/berserk.txt");
    }

    public static GeneticCode getTest02() {
        String code = "t = t + 1\n" +
                "virusLoc = virus\n" +
                "if (virusLoc / 10 - 1)\n" +
                "then\n" +
                "  if (virusLoc % 10 - 7) then move upleft\n" +
                "  else if (virusLoc % 10 - 6) then move left\n" +
                "  else if (virusLoc % 10 - 5) then move downleft\n" +
                "  else if (virusLoc % 10 - 4) then move down\n" +
                "  else if (virusLoc % 10 - 3) then move downright\n" +
                "  else if (virusLoc % 10 - 2) then move right\n" +
                "  else if (virusLoc % 10 - 1) then move upright\n" +
                "  else move up\n" +
                "else if (virusLoc)\n" +
                "then\n" +
                "  if (virusLoc % 10 - 7) then shoot upleft\n" +
                "  else if (virusLoc % 10 - 6) then shoot left\n" +
                "  else if (virusLoc % 10 - 5) then shoot downleft\n" +
                "  else if (virusLoc % 10 - 4) then shoot down\n" +
                "  else if (virusLoc % 10 - 3) then shoot downright\n" +
                "  else if (virusLoc % 10 - 2) then shoot right\n" +
                "  else if (virusLoc % 10 - 1) then shoot upright\n" +
                "  else shoot up\n" +
                "else\n" +
                "{\n" +
                "  dir = random % 8\n" +
                "  if (dir - 6) then move upleft\n" +
                "  else if (dir - 5) then move left\n" +
                "  else if (dir - 4) then move downleft\n" +
                "  else if (dir - 3) then move down\n" +
                "  else if (dir - 2) then move downright\n" +
                "  else if (dir - 1) then move right\n" +
                "  else if (dir) then move upright\n" +
                "  else move up\n" +
                "}\n" +
                "while (virus)\n" +
                "if(virus) then shoot up\n" +
                "if(virus) then shoot up\n" +
                "if(virus) then move up\n" +
                "if(virus) then move upright\n" +
                "else then if(virus) then move";
        return new GeneticCode(code);
    }

    public static GeneticCode getGeneticCodeFromFile(String pathText) {
        Path path = Paths.get(pathText);
        Charset charset = StandardCharsets.US_ASCII;
        StringBuilder code = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            while (reader.ready()) {
                code.append(reader.readLine());
                code.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GeneticCode(code.toString());
    }
}
