package com.tyler.narutoorigin.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class NarutoOriginsConfig {

    public static float tailedBeastProbability;
    public static float medicProbability;
    public static float senjuWoodKKGProbability;
    public static float rinneganProbability;
    public static float otsusukiByakuganProbability;
    public static float otsusukiSharinganProbability;
    public static float otsusukiRinneganProbability;
    public static boolean onlyDojutsuNoKKG;
    public static int maxSimulations;
    public static int simulatePermissionLevel;
    public static int resetMePermissionLevel;
    public static int makeMeAShinobiPermissionLevel;
    public static int doItForMePermissionLevel;

    // Add these new fields
    public static float Jutsu_XP_MULTI;
    public static float Ninja_XP_MULTI;

    private static final String CATEGORY_PROBABILITIES = "Probabilities";
    private static final String CATEGORY_OTSUSUKI = "Otsusuki Probabilities";
    private static final String CATEGORY_SIMULATIONS = "Simulations";
    private static final String CATEGORY_SETTINGS = "Settings";

    public static void init(FMLPreInitializationEvent event) {
        File directory = event.getModConfigurationDirectory();
        File configFile = new File(directory.getPath(), "NarutoOrigins.cfg");
        Configuration config = new Configuration(configFile);

        config.load();

        tailedBeastProbability = config.getFloat("tailedBeast", CATEGORY_PROBABILITIES, 0.07f, 0.0f, 1.0f,
                "Probability of a player obtaining a Tailed Beast (7% chance by default)");
        medicProbability = config.getFloat("medic", CATEGORY_PROBABILITIES, 0.30f, 0.0f, 1.0f,
                "Probability of a player becoming a Medic (30% chance by default)");
        senjuWoodKKGProbability = config.getFloat("senjuWoodKKG", CATEGORY_PROBABILITIES, 0.60f, 0.0f, 1.0f,
                "Probability of a Senju player obtaining the Wood Release Kekkei Genkai (60% chance by default)");
        rinneganProbability = config.getFloat("rinnegan", CATEGORY_PROBABILITIES, 0.01f, 0.0f, 1.0f,
                "Probability of a player obtaining the Rinnegan (1% chance by default)");

        otsusukiByakuganProbability = config.getFloat("otsusukiByakugan", CATEGORY_OTSUSUKI, 0.90f, 0.0f, 1.0f,
                "Probability of an Otsusuki player obtaining the Byakugan (90% chance by default)");
        otsusukiSharinganProbability = config.getFloat("otsusukiSharingan", CATEGORY_OTSUSUKI, 0.05f, 0.0f, 1.0f,
                "Probability of an Otsusuki player obtaining the Sharingan (5% chance by default)");
        otsusukiRinneganProbability = config.getFloat("otsusukiRinnegan", CATEGORY_OTSUSUKI, 0.05f, 0.0f, 1.0f,
                "Probability of an Otsusuki player obtaining the Rinnegan (5% chance by default)");

        onlyDojutsuNoKKG = config.getBoolean("onlyDojutsuNoKKG", CATEGORY_SETTINGS, false,
                "If true, players with a Dojutsu will not receive a KKG");

        maxSimulations = config.getInt(
            "maxSimulations",
            CATEGORY_SIMULATIONS,
            3,
            1,
            8,
            "Maximum number of times a player can use the /simulate command"
        );


        if (config.hasChanged()) {
            config.save();
        }
    }
}
