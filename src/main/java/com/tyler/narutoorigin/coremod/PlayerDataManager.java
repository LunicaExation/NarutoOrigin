package com.tyler.narutoorigin.coremod;

import net.minecraft.server.MinecraftServer;
import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PlayerDataManager {
    private final String filePath;
    private Map<String, PlayerData> playerDataMap;

    public PlayerDataManager(MinecraftServer server) {
        // Get the current world's directory
        File worldDirectory = server.getWorld(0).getSaveHandler().getWorldDirectory();

        // Create the "NarutoOrigins" folder in the current world directory
        File narutoOriginsDir = new File(worldDirectory, "NarutoOrigins");
        if (!narutoOriginsDir.exists()) {
            narutoOriginsDir.mkdirs(); // Create the folder if it doesn't exist
        }

        // Set the path to the "playerDataNarutoOrigins.dat" file in the "NarutoOrigins" folder
        this.filePath = Paths.get(narutoOriginsDir.getPath(), "playerDataNarutoOrigins.dat").toString();
        playerDataMap = new HashMap<>();
        loadPlayerData();
    }

    public PlayerData getPlayerData(String playerName) {
        return playerDataMap.computeIfAbsent(playerName, k -> new PlayerData(playerName));
    }

    public void savePlayerData(PlayerData playerData) {
        playerDataMap.put(playerData.getName(), playerData);
        saveAllPlayerData();
    }

    private void saveAllPlayerData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(playerDataMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadPlayerData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            playerDataMap = (Map<String, PlayerData>) ois.readObject();
        } catch (FileNotFoundException e) {
            playerDataMap = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
