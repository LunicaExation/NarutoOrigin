package com.tyler.narutoorigin.coremod;

import com.tyler.narutoorigin.coremod.ClanEffectManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class CommandClaimOrigin extends CommandBase {

    private final CommandSimulate simulateCommand;
    private final PlayerDataManager playerDataManager;

    public CommandClaimOrigin(CommandSimulate simulateCommand, PlayerDataManager playerDataManager) {
        this.simulateCommand = simulateCommand;
        this.playerDataManager = playerDataManager;
    }

    @Override
    public String getName() {
        return "claimOrigin";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/claimOrigin <index>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws WrongUsageException {
        String playerName = sender.getName();
        PlayerData playerData = playerDataManager.getPlayerData(playerName);

        if (playerData.hasClaimedOrigin()) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "You have already claimed an Origin."));
            return;
        }

        if (args.length < 1) {
            throw new WrongUsageException("Usage: /claimOrigin <index>");
        }

        int index;
        try {
            index = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new WrongUsageException("The index must be a valid number.");
        }

        String[] claimData = simulateCommand.getPendingClaim(playerName, index);
        if (claimData == null) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Invalid origin selection."));
            return;
        }

        // Save the Clan and KKG data in PlayerData
        playerData.setClan(claimData[0]);
        playerData.setKkg(claimData[3]);
        playerData.setDojutsu(claimData[1]);

        // Mark the origin as claimed and save the data
        playerData.setHasClaimedOrigin(true);
        playerDataManager.savePlayerData(playerData);

        // Apply Clan Effects using ClanEffectManager
        EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(playerData.getName());
        if (player != null) {
            ClanEffectManager.applyEffectsBasedOnClan(player, playerData.getClan());
        }

        // Automatically execute related claims
        if (!claimData[3].equals("no KKG")) {
            executeServerCommand(server, sender, "/playerClick executeKKG " + playerName + " " + claimData[3]);
        }

        if (!claimData[1].equals("no Dojutsu")) {
            executeServerCommand(server, sender, "/playerClick executeDojutsu " + playerName + " " + claimData[1]);
        }

        if (!claimData[2].equals("no Element")) {
            executeServerCommand(server, sender, "/playerClick executeElement " + playerName + " " + claimData[2].toLowerCase());
        }

        if (!claimData[5].equals("no Medic")) {
            executeServerCommand(server, sender, "/playerClick executeMedic " + playerName);
        }

        if (claimData[0].equals("Lee") && !claimData[4].isEmpty()) {
            executeServerCommand(server, sender, "/playerClick executeTaijutsu " + playerName);
        }

        if (!claimData[6].isEmpty()) {
            executeServerCommand(server, sender, "/playerClick executeTailedBeast " + playerName + " " + claimData[6]);
        }

        sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "You have claimed your Origin successfully."));
    }

    private void executeServerCommand(MinecraftServer server, ICommandSender sender, String command) {
        server.getCommandManager().executeCommand(sender, command);
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
