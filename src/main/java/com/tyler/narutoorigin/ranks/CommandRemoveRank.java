package com.tyler.narutoorigin.ranks;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class CommandRemoveRank extends CommandBase {
    private final RankManager rankManager;

    public CommandRemoveRank(RankManager rankManager) {
        this.rankManager = rankManager;
    }

    @Override
    public String getName() {
        return "removerank";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/removerank <player>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // Nur Spieler mit OP-Level 2 oder höher können diesen Befehl ausführen (normalerweise Admins)
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new CommandException("Usage: /removerank <player>", new Object[0]);
        }

        EntityPlayer player = getPlayer(server, sender, args[0]);

        Rank currentRank = rankManager.getRank(player);

        if (currentRank != null) {
            // Entfernen des Rangs
            rankManager.removeRank(player);

            // Nachricht im Chat für alle Spieler
            TextComponentString playerMessage = new TextComponentString(player.getName());
            playerMessage.setStyle(new Style().setColor(TextFormatting.WHITE).setBold(true)); // Spielername fett und weiß

            TextComponentString rankMessage = new TextComponentString(currentRank.getName());
            rankMessage.setStyle(new Style().setColor(currentRank.getColor()).setBold(true)); // Rang in Farbe und fett

            TextComponentString finalMessage = new TextComponentString("");
            finalMessage.appendSibling(playerMessage);
            finalMessage.appendText(" is no longer ");
            finalMessage.appendSibling(rankMessage);

            server.getPlayerList().sendMessage(finalMessage);
        } else {
            sender.sendMessage(new TextComponentString("Player " + player.getName() + " does not have a rank to remove."));
        }

        sender.sendMessage(new TextComponentString("Removed rank from " + player.getName()));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, net.minecraft.util.math.BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
