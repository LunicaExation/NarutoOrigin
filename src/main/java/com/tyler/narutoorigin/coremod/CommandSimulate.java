package com.tyler.narutoorigin.coremod;

import com.tyler.narutoorigin.config.NarutoOriginsConfig;
import com.tyler.narutoorigin.NarutoOriginMod;
import com.tyler.narutoorigin.gui.GuiOriginSelection;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.*;

public class CommandSimulate extends CommandBase {

    private final Random rand = new Random();
    private final Map<String, Boolean> hasClaimedOrigin = new HashMap<>();
    private final Map<String, Integer> simulationCount = new HashMap<>();
    private final Map<String, List<String[]>> pendingClaims = new HashMap<>();
    private final Map<String, Set<String>> usedClans = new HashMap<>();

    private final String[] clan = {"Uchiha", "Uzumaki", "Hyuga", "Kaguya", "Lee", "Otsusuki", "Namikaze", "Senju"};
    private final String[] element = {"fire", "water", "earth", "lightning", "wind"};
    private final String[] kkg = {"Dust", "Magnet", "Scorch", "Ice", "Explosion", "Boil", "Lava", "Storm"};
    private final String[] dojutsu = {"Sharingan", "Byakugan", "no Dojutsu", "Rinnegan"};
    private final String[] taijutsu = {"8 Gates"};
    private final String[] medic = {"Medic"};
    private final String[] tailedBeasts = {"Shukaku", "Matatabi", "Isobu", "Son Goku", "Kokuo", "Saiken", "Chomei", "Gyuki", "Kurama"};

    @Override
    public String getName() {
        return "simulate";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/simulate";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws WrongUsageException {
        String playerName = sender.getName();

        if (hasPlayerClaimedOrigin(playerName)) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "You have already claimed an Origin."));
            return;
        }

        int maxSimulations = NarutoOriginsConfig.maxSimulations;
        if (simulationCount.getOrDefault(playerName, 0) >= maxSimulations) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "You have reached the maximum number of simulations (" + maxSimulations + ")."));
            return;
        }

        Set<String> playerUsedClans = usedClans.getOrDefault(playerName, new HashSet<>());
        List<String> availableClans = new ArrayList<>(Arrays.asList(clan));
        availableClans.removeAll(playerUsedClans);

        if (availableClans.isEmpty()) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "No more clans available to simulate."));
            return;
        }

        // Generate a new simulation
        String selectedClan = availableClans.get(rand.nextInt(availableClans.size()));
        playerUsedClans.add(selectedClan);
        usedClans.put(playerName, playerUsedClans);

        String selectedDojutsu = "no Dojutsu";
        String selectedElement = "no Element";
        String selectedKKG = "no KKG";
        String selectedTaijutsu = "";
        String selectedMedic = "no Medic";
        String selectedTailedBeast = "";

        // Logic to choose attributes based on the clan
        switch (selectedClan) {
            case "Uchiha":
                selectedDojutsu = "Sharingan";
                selectedElement = "fire";
                if (!(NarutoOriginsConfig.onlyDojutsuNoKKG && !selectedDojutsu.equals("no Dojutsu"))) {
                    selectedKKG = kkg[rand.nextInt(kkg.length)];
                }
                selectedMedic = (rand.nextInt(100) < NarutoOriginsConfig.medicProbability * 100) ? "Medic" : "no Medic";
                if (rand.nextInt(100) < NarutoOriginsConfig.tailedBeastProbability * 100) {
                    selectedTailedBeast = tailedBeasts[rand.nextInt(tailedBeasts.length)];
                }
                break;
            case "Hyuga":
                selectedDojutsu = "Byakugan";
                selectedElement = element[rand.nextInt(element.length)];
                if (!(NarutoOriginsConfig.onlyDojutsuNoKKG && !selectedDojutsu.equals("no Dojutsu"))) {
                    selectedKKG = kkg[rand.nextInt(kkg.length)];
                }
                selectedMedic = (rand.nextInt(100) < NarutoOriginsConfig.medicProbability * 100) ? "Medic" : "no Medic";
                if (rand.nextInt(100) < NarutoOriginsConfig.tailedBeastProbability * 100) {
                    selectedTailedBeast = tailedBeasts[rand.nextInt(tailedBeasts.length)];
                }
                break;
            case "Lee":
                selectedDojutsu = "no Dojutsu";
                selectedElement = "no Element";
                selectedKKG = "no KKG";
                selectedTaijutsu = "8 Gates";
                selectedMedic = "no Medic";
                break;
            case "Otsusuki":
                float dojutsuRoll = rand.nextFloat() * 100;
                if (dojutsuRoll < NarutoOriginsConfig.otsusukiByakuganProbability * 100) {
                    selectedDojutsu = "Byakugan";
                } else if (dojutsuRoll < (NarutoOriginsConfig.otsusukiByakuganProbability + NarutoOriginsConfig.otsusukiSharinganProbability) * 100) {
                    selectedDojutsu = "Sharingan";
                } else if (dojutsuRoll < (NarutoOriginsConfig.otsusukiByakuganProbability + NarutoOriginsConfig.otsusukiSharinganProbability + NarutoOriginsConfig.otsusukiRinneganProbability) * 100) {
                    selectedDojutsu = "Rinnegan";
                } else {
                    selectedDojutsu = "Byakugan"; // Default fallback
                }
                selectedElement = element[rand.nextInt(element.length)];
                if (!(NarutoOriginsConfig.onlyDojutsuNoKKG && !selectedDojutsu.equals("no Dojutsu"))) {
                    selectedKKG = kkg[rand.nextInt(kkg.length)];
                }
                selectedMedic = (rand.nextInt(100) < NarutoOriginsConfig.medicProbability * 100) ? "Medic" : "no Medic";
                if (rand.nextInt(100) < NarutoOriginsConfig.tailedBeastProbability * 100) {
                    selectedTailedBeast = tailedBeasts[rand.nextInt(tailedBeasts.length)];
                }
                break;
            case "Kaguya":
                selectedDojutsu = "no Dojutsu";
                selectedElement = element[rand.nextInt(element.length)];
                selectedKKG = "shikotsumyaku";
                selectedMedic = (rand.nextInt(100) < NarutoOriginsConfig.medicProbability * 100) ? "Medic" : "no Medic";
                if (rand.nextInt(100) < NarutoOriginsConfig.tailedBeastProbability * 100) {
                    selectedTailedBeast = tailedBeasts[rand.nextInt(tailedBeasts.length)];
                }
                break;
            case "Senju":
                selectedDojutsu = "no Dojutsu";
                selectedElement = element[rand.nextInt(element.length)];
                selectedKKG = (rand.nextInt(100) < NarutoOriginsConfig.senjuWoodKKGProbability * 100) ? "Wood" : kkg[rand.nextInt(kkg.length)];
                selectedMedic = (rand.nextInt(100) < NarutoOriginsConfig.medicProbability * 100) ? "Medic" : "no Medic";
                if (rand.nextInt(100) < NarutoOriginsConfig.tailedBeastProbability * 100) {
                    selectedTailedBeast = tailedBeasts[rand.nextInt(tailedBeasts.length)];
                }
                break;
            default:
                selectedDojutsu = (rand.nextInt(100) < NarutoOriginsConfig.rinneganProbability * 100) ? "Rinnegan" : "no Dojutsu";
                selectedElement = element[rand.nextInt(element.length)];
                if (!(NarutoOriginsConfig.onlyDojutsuNoKKG && !selectedDojutsu.equals("no Dojutsu"))) {
                    selectedKKG = kkg[rand.nextInt(kkg.length)];
                }
                selectedMedic = (rand.nextInt(100) < NarutoOriginsConfig.medicProbability * 100) ? "Medic" : "no Medic";
                if (rand.nextInt(100) < NarutoOriginsConfig.tailedBeastProbability * 100) {
                    selectedTailedBeast = tailedBeasts[rand.nextInt(tailedBeasts.length)];
                }
                break;
        }

        String[] newSimulationResult = new String[]{selectedClan, selectedDojutsu, selectedElement, selectedKKG, selectedTaijutsu, selectedMedic, selectedTailedBeast};
        pendingClaims.put(playerName, Collections.singletonList(newSimulationResult));
        simulationCount.put(playerName, simulationCount.getOrDefault(playerName, 0) + 1);

        // Update simulation results
        NarutoOriginMod.simulationResults = Collections.singletonList(newSimulationResult);

        EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(playerName);
        if (player != null) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiOriginSelection(player, newSimulationResult, this, NarutoOriginMod.playerDataManager));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    public boolean hasPlayerClaimedOrigin(String playerName) {
        return hasClaimedOrigin.getOrDefault(playerName, false);
    }

    public void setPlayerClaimedOrigin(String playerName) {
        hasClaimedOrigin.put(playerName, true);
    }

    public List<String[]> getPendingClaims(String playerName) {
        return pendingClaims.getOrDefault(playerName, new ArrayList<>());
    }

    public String[] getPendingClaim(String playerName, int index) {
        List<String[]> claims = getPendingClaims(playerName);
        if (index >= 0 && index < claims.size()) {
            return claims.get(index);
        }
        return null;
    }

    public void executeServerCommand(MinecraftServer server, ICommandSender sender, String command) {
        server.getCommandManager().executeCommand(sender, command);
    }
}
