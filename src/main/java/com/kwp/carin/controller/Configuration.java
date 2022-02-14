package com.kwp.carin.controller;

import com.kwp.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Configuration {
    private int m, n;
    private float virusSpawnRate;
    private int initialAntibodyCredit, antibodyPlacementCost;
    private int virusHealth, antibodyHealth;
    private int virusAttackDamage, virusAttackGain;
    private int antibodyAttackDamage, antibodyKillGain;
    private int antibodyMoveCost;
    private Configuration() {
        Path path = Paths.get("src/configuration.txt");
        Charset charset = StandardCharsets.US_ASCII;
        try (BufferedReader reader = Files.newBufferedReader(path, charset); Scanner scanner = new Scanner(reader)) {
            m = scanner.nextInt(); n = scanner.nextInt();
            virusSpawnRate = scanner.nextFloat();
            initialAntibodyCredit = scanner.nextInt(); antibodyPlacementCost = scanner.nextInt();
            virusHealth = scanner.nextInt(); antibodyHealth = scanner.nextInt();
            virusAttackDamage = scanner.nextInt(); virusAttackGain = scanner.nextInt();
            antibodyAttackDamage = scanner.nextInt(); antibodyKillGain = scanner.nextInt();
            antibodyMoveCost = scanner.nextInt();
            if (antibodyMoveCost > initialAntibodyCredit) System.out.println("Warning: Antibody Move Cost should be no more than the initial antibody credits");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pair<Integer, Integer> getDimension() {
        return new Pair<Integer, Integer>(m, n);
    }

    public float getVirusSpawnRate() {
        return virusSpawnRate;
    }

    public int getInitialAntibodyCredit() {
        return initialAntibodyCredit;
    }

    public int getAntibodyPlacementCost() {
        return antibodyPlacementCost;
    }

    public int getVirusHealth() {
        return virusHealth;
    }

    public int getAntibodyHealth() {
        return antibodyHealth;
    }

    public int getVirusAttackDamage() {
        return virusAttackDamage;
    }

    public int getVirusAttackGain() {
        return virusAttackGain;
    }

    public int getAntibodyAttackDamage() {
        return antibodyAttackDamage;
    }

    public int getAntibodyKillGain() {
        return antibodyKillGain;
    }

    public int getAntibodyMoveCost() {
        return antibodyMoveCost;
    }

    private static Configuration instance;

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }
}
