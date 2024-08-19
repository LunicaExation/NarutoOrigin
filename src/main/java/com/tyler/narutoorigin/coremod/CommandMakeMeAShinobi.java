package com.tyler.narutoorigin.coremod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.narutomod.procedure.ProcedureAddNinjaXpCommandExecuted;

import java.util.HashMap;

public class CommandMakeMeAShinobi extends CommandBase {

    private final PlayerDataManager playerDataManager;

    public CommandMakeMeAShinobi(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @Override
    public String getName() {
        return "makeMeAShinobi";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/makeMeAShinobi";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String playerName = sender.getName();
        PlayerData playerData = playerDataManager.getPlayerData(playerName);

        if (playerData.hasUsedMakeMeAShinobi()) {
            sender.sendMessage(new TextComponentString("You have already used this command!"));
            return;
        }

        playerData.setHasUsedMakeMeAShinobi(true);
        playerDataManager.savePlayerData(playerData);  // Save the player data after updating it

        // Give the player basic items
        server.getCommandManager().executeCommand(server, "give " + playerName + " narutomod:kunai");
        server.getCommandManager().executeCommand(server, "give " + playerName + " narutomod:shuriken");

        // Check the player's clan and KKG and give corresponding items
        if (playerData.getClan().equals("Lee")) {
            server.getCommandManager().executeCommand(server, "give " + playerName + " narutomod:eightgates");
        } else if (playerData.getClan().equals("Senju") && playerData.getKkg().equals("Wood")) {
            server.getCommandManager().executeCommand(server, "give " + playerName + " narutomod:mokuton");
        }

        // Check if the player has Rinnegan as Dojutsu and give the corresponding item
        if (playerData.getDojutsu().equals("Rinnegan")) {
            server.getCommandManager().executeCommand(server, "give " + playerName + " narutomod:rinneganhelmet");
        }

        // Wait for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Add XP and Ninja XP
        EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(playerName);
        if (player != null) {
            player.addExperience(160);

            HashMap<String, String> cmdparams = new HashMap<>();
            cmdparams.put("0", playerName);
            cmdparams.put("1", "300");

            HashMap<String, Object> $_dependencies = new HashMap<>();
            $_dependencies.put("entity", player);
            $_dependencies.put("cmdparams", cmdparams);
            ProcedureAddNinjaXpCommandExecuted.executeProcedure($_dependencies);

        } else {
            sender.sendMessage(new TextComponentString("Player not found"));
        }

        sender.sendMessage(new TextComponentString("Have fun as a Shinobi"));
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true; // Allows any player to execute the command
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0; // Sets the required permission level to 0, meaning no admin rights are required
    }
}
