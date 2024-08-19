package com.tyler.narutoorigin.coremod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.util.text.Style;
import com.tyler.narutoorigin.config.NarutoOriginsConfig;

import java.util.*;

public class CommandSimulate extends CommandBase {

    private final Random rand = new Random(); // Initialize the random variable
    private final Map<String, Boolean> hasClaimedOrigin = new HashMap<>();
    private final Map<String, Integer> simulationCount = new HashMap<>();
    private final Map<String, List<String[]>> pendingClaims = new HashMap<>(); // Store multiple simulation results
    private final Map<String, Set<String>> usedClans = new HashMap<>(); // Track used clans per player

    // Updated clan list to include "Senju"
    private final String[] clan = {"Uchiha", "Uzumaki", "Hyuga", "Kaguya", "Lee", "Otsusuki", "Namikaze", "Senju"};
    private final String[] element = {"fire", "water", "earth", "lightning", "wind"};
    private final String[] kkg = {"Dust", "Magnet", "Scorch", "Ice", "Explosion", "Boil", "Lava", "Storm"};
    private final String[] dojutsu = {"Sharingan", "Byakugan", "no Dojutsu", "Rinnegan"};
    private final String[] taijutsu = {"8 Gates"};
    private final String[] medic = {"Medic"};
    private final String[] tailedBeasts = {"Shukaku", "Matatabi", "Isobu", "Son-Goku", "Kokuo", "Saiken", "Chomei", "Gyuki", "Kurama"};

    // Probabilities are taken from the configuration file
    private final float tailedBeastChance = NarutoOriginsConfig.tailedBeastProbability * 100; // Probability for Tailed Beast
    private final float woodKKGChance = NarutoOriginsConfig.senjuWoodKKGProbability * 100; // Probability for Wood KKG (Mokuton)
    private final float medicChance = NarutoOriginsConfig.medicProbability * 100; // Probability for Medic
    private final float rinneganChance = NarutoOriginsConfig.rinneganProbability * 100; // Probability for Rinnegan

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

        // Use maxSimulations from the config instead of hardcoding the value
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

        String selectedClan = availableClans.get(rand.nextInt(availableClans.size()));
        playerUsedClans.add(selectedClan);
        usedClans.put(playerName, playerUsedClans);

        String selectedDojutsu = "";
        String selectedElement = "";
        String selectedKKG = "";
        String selectedTaijutsu = "";
        String selectedMedic = "";
        String selectedTailedBeast = "";

        switch (selectedClan) {
            case "Uchiha":
                selectedDojutsu = "Sharingan";
                selectedElement = "fire";
                selectedKKG = kkg[rand.nextInt(kkg.length)];
                selectedMedic = (rand.nextInt(100) < medicChance) ? "Medic" : "no Medic";
                if (rand.nextInt(100) < tailedBeastChance) {
                    selectedTailedBeast = tailedBeasts[rand.nextInt(tailedBeasts.length)];
                }
                break;
            case "Hyuga":
                selectedDojutsu = "Byakugan";
                selectedElement = element[rand.nextInt(element.length)];
                selectedKKG = kkg[rand.nextInt(kkg.length)];
                selectedMedic = (rand.nextInt(100) < medicChance) ? "Medic" : "no Medic";
                if (rand.nextInt(100) < tailedBeastChance) {
                    selectedTailedBeast = tailedBeasts[rand.nextInt(tailedBeasts.length)];
                }
                break;
            case "Lee":
                selectedDojutsu = "no Dojutsu";
                selectedElement = "no Element";
                selectedKKG = "no KKG";
                selectedTaijutsu = "8 Gates";
                selectedMedic = "no Medic"; // Lee Clan should never be a medic
                break;
            case "Otsusuki":
                int dojutsuChance = rand.nextInt(100);
                if (dojutsuChance < 90) {
                    selectedDojutsu = "Byakugan";
                } else if (dojutsuChance < 95) {
                    selectedDojutsu = "Sharingan";
                } else {
                    selectedDojutsu = "Rinnegan";
                }
                selectedElement = element[rand.nextInt(element.length)];
                selectedKKG = kkg[rand.nextInt(kkg.length)];
                selectedMedic = (rand.nextInt(100) < medicChance) ? "Medic" : "no Medic";
                if (rand.nextInt(100) < tailedBeastChance) {
                    selectedTailedBeast = tailedBeasts[rand.nextInt(tailedBeasts.length)];
                }
                break;
            case "Kaguya":
                selectedDojutsu = "no Dojutsu";
                selectedElement = element[rand.nextInt(element.length)];
                selectedKKG = "shikotsumyaku";
                selectedMedic = (rand.nextInt(100) < medicChance) ? "Medic" : "no Medic";
                if (rand.nextInt(100) < tailedBeastChance) {
                    selectedTailedBeast = tailedBeasts[rand.nextInt(tailedBeasts.length)];
                }
                break;
            case "Senju":
                selectedDojutsu = "no Dojutsu"; // Senju should not get any Dojutsu
                selectedElement = element[rand.nextInt(element.length)]; // Randomly select an element
                selectedKKG = (rand.nextInt(100) < woodKKGChance) ? "Wood" : kkg[rand.nextInt(kkg.length)]; // 60% chance to get Wood KKG, otherwise a random KKG
                selectedMedic = (rand.nextInt(100) < medicChance) ? "Medic" : "no Medic";
                if (rand.nextInt(100) < tailedBeastChance) {
                    selectedTailedBeast = tailedBeasts[rand.nextInt(tailedBeasts.length)];
                }
                break;
            default:
                selectedDojutsu = (rand.nextInt(100) < rinneganChance) ? "Rinnegan" : "no Dojutsu";
                selectedElement = element[rand.nextInt(element.length)];
                selectedKKG = kkg[rand.nextInt(kkg.length)];
                selectedMedic = (rand.nextInt(100) < medicChance) ? "Medic" : "no Medic";
                if (rand.nextInt(100) < tailedBeastChance) {
                    selectedTailedBeast = tailedBeasts[rand.nextInt(tailedBeasts.length)];
                }
                break;
        }

        pendingClaims.computeIfAbsent(playerName, k -> new ArrayList<>())
                .add(new String[]{selectedClan, selectedDojutsu, selectedElement, selectedKKG, selectedTaijutsu, selectedMedic, selectedTailedBeast});
        simulationCount.put(playerName, simulationCount.getOrDefault(playerName, 0) + 1);

        for (int i = 0; i < 20; i++) {
            sender.sendMessage(new TextComponentString(""));
        }

        TextComponentString combinedMessage = new TextComponentString("");
        combinedMessage.appendSibling(new TextComponentString("Clan: " + selectedClan + "\n"));

        if (selectedDojutsu.equals("Sharingan")) {
            combinedMessage.appendSibling(new TextComponentString("Dojutsu: " + TextFormatting.RED + selectedDojutsu + "\n"));
        } else if (selectedDojutsu.equals("Byakugan")) {
            combinedMessage.appendSibling(new TextComponentString("Dojutsu: " + TextFormatting.WHITE + TextFormatting.BOLD + selectedDojutsu + "\n"));
        } else if (selectedDojutsu.equals("Rinnegan")) {
            combinedMessage.appendSibling(new TextComponentString("Dojutsu: " + TextFormatting.DARK_PURPLE + selectedDojutsu + "\n"));
        } else {
            combinedMessage.appendSibling(new TextComponentString("Dojutsu: " + selectedDojutsu + "\n"));
        }

        switch (selectedElement) {
            case "fire":
                combinedMessage.appendSibling(new TextComponentString("Element: " + TextFormatting.RED + selectedElement + "\n"));
                break;
            case "water":
                combinedMessage.appendSibling(new TextComponentString("Element: " + TextFormatting.BLUE + selectedElement + "\n"));
                break;
            case "earth":
                combinedMessage.appendSibling(new TextComponentString("Element: "+TextFormatting.GOLD + selectedElement + "\n"));
                break;
            case "lightning":
                combinedMessage.appendSibling(new TextComponentString("Element: " + TextFormatting.YELLOW + selectedElement + "\n"));
                break;
            case "wind":
                combinedMessage.appendSibling(new TextComponentString("Element: " + TextFormatting.GREEN + selectedElement + "\n"));
                break;
            default:
                combinedMessage.appendSibling(new TextComponentString("Element: " + selectedElement + "\n"));
                break;
        }

        if (!selectedKKG.equals("no KKG")) {
            combinedMessage.appendSibling(new TextComponentString("KKG: " + selectedKKG + "\n"));
        }

        if (!selectedTaijutsu.isEmpty()) {
            combinedMessage.appendSibling(new TextComponentString("Taijutsu: " + selectedTaijutsu + "\n"));
        }

        if (!selectedMedic.isEmpty()) {
            combinedMessage.appendSibling(new TextComponentString("Medic: " + selectedMedic + "\n"));
        }

        if (!selectedTailedBeast.isEmpty()) {
            combinedMessage.appendSibling(new TextComponentString("Tailed Beast: " + selectedTailedBeast + "\n"));
        }

        TextComponentString claimOriginMessage = new TextComponentString(TextFormatting.GREEN + "[Claim this Origin]\n");
        claimOriginMessage.setStyle(new Style().setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/claimOrigin " + (pendingClaims.get(playerName).size() - 1)))
                .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to claim this origin"))));
        combinedMessage.appendSibling(claimOriginMessage);

        sender.sendMessage(combinedMessage);
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
}
