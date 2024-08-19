package com.tyler.narutoorigin.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class NarutoOriginsConfig {

    public static float tailedBeastProbability;
    public static float medicProbability;
    public static float senjuWoodKKGProbability;
    public static float rinneganProbability;
    public static int doItForMePermissionLevel;
    public static int maxSimulations; // New variable for the maximum number of simulations

    private static final String CATEGORY_PROBABILITIES = "Probabilities";
    private static final String CATEGORY_PERMISSIONS = "Permissions";
    private static final String CATEGORY_LIMITS = "Limits"; // New category for limits

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

        // New configuration option for the maximum number of simulations
        maxSimulations = config.getInt(
            "maxSimulations",              // Name of the configuration option
            CATEGORY_LIMITS,               // Category under which this option is stored
            3,                             // Default value: Number of allowed simulations if not specified otherwise
            1,                             // Minimum value: The lowest allowed number of simulations
            10,                            // Maximum value: The highest allowed number of simulations
            "Maximum number of times a player can use the /simulate command" // Description of the configuration option
        );

        if (config.hasChanged()) {
            config.save();
        }
    }
}
