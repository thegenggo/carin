package com.kwp.carin;

import com.kwp.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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
        Path path = Paths.get("src/main/java/com/kwp/carin/configuration.txt");
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
        } catch (NoSuchFileException e) {
            System.out.println("No configuration file found. Using default configuration.");
            m = 10; n = 10;
            virusSpawnRate = 0.8f;
            initialAntibodyCredit = 100; antibodyPlacementCost = 20;
            virusHealth = 100; antibodyHealth = 100;
            virusAttackDamage = 10; virusAttackGain = 5;
            antibodyAttackDamage = 10; antibodyKillGain = 5;
            antibodyMoveCost = 10;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pair<Integer, Integer> getDimension() {
        return new Pair<>(m, n);
    }

    public static float getVirusSpawnRate() {
        return instance.virusSpawnRate;
    }

    public static int getInitialAntibodyCredit() {
        return instance.initialAntibodyCredit;
    }

    public static int getAntibodyPlacementCost() {
        return instance.antibodyPlacementCost;
    }

    public static int getVirusHealth() {
        return instance.virusHealth;
    }

    public static int getAntibodyHealth() {
        return instance.antibodyHealth;
    }

    public static int getVirusAttackDamage() {
        return instance.virusAttackDamage;
    }

    public static int getVirusAttackGain() {
        return instance.virusAttackGain;
    }

    public static int getAntibodyAttackDamage() {
        return instance.antibodyAttackDamage;
    }

    public static int getAntibodyKillGain() {
        return instance.antibodyKillGain;
    }

    public static int getAntibodyMoveCost() {
        return instance.antibodyMoveCost;
    }

    private static final Configuration instance = new Configuration();

    public static Configuration getInstance() {
        return instance;
    }
}
