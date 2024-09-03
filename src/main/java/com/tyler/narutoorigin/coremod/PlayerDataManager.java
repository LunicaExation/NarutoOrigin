package com.tyler.narutoorigin.coremod;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.server.MinecraftServer;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PlayerDataManager {
    private final String filePath;
    private Map<String, PlayerData> playerDataMap;

    public PlayerDataManager(MinecraftServer server) {
        File worldDirectory = server.getWorld(0).getSaveHandler().getWorldDirectory();
        File narutoOriginsDir = new File(worldDirectory, "NarutoOrigins");
        if (!narutoOriginsDir.exists()) {
            narutoOriginsDir.mkdirs();
        }
        this.filePath = Paths.get(narutoOriginsDir.getPath(), "playerDataNarutoOrigins.json").toString();
        playerDataMap = new HashMap<>();
        loadPlayerData();
    }

    public PlayerData getPlayerData(String playerName, String worldName) {
        return playerDataMap.computeIfAbsent(playerName + ":" + worldName, k -> new PlayerData(playerName, worldName));
    }

    public void savePlayerData(PlayerData playerData) {
        playerDataMap.put(playerData.getName() + ":" + playerData.getWorldName(), playerData);
        saveAllPlayerData();
    }

    private void saveAllPlayerData() {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(playerDataMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPlayerData() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)) {
            Type type = new TypeToken<Map<String, PlayerData>>() {}.getType();
            playerDataMap = gson.fromJson(reader, type);
            if (playerDataMap == null) {
                playerDataMap = new HashMap<>();
            }
        } catch (FileNotFoundException e) {
            playerDataMap = new HashMap<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
