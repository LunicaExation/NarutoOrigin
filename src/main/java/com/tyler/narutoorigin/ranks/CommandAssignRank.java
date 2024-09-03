package com.tyler.narutoorigin.ranks;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class CommandAssignRank extends CommandBase {
    private final RankManager rankManager;

    public CommandAssignRank(RankManager rankManager) {
        this.rankManager = rankManager;
    }

    @Override
    public String getName() {
        return "assignrank";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/assignrank <player> <rank>";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // Nur Spieler mit OP-Level 2 oder höher können diesen Befehl ausführen (normalerweise Admins)
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) {
            throw new CommandException("Usage: /assignrank <player> <rank>", new Object[0]);
        }

        EntityPlayer player = getPlayer(server, sender, args[0]);
        Rank rank = Rank.fromString(args[1]);

        if (rank == null) {
            throw new CommandException("Invalid rank: " + args[1]);
        }

        rankManager.assignRank(player, rank);

        // Nachricht im Chat für alle Spieler
        TextComponentString rankMessage = new TextComponentString(rank.getName());
        rankMessage.setStyle(new Style().setColor(rank.getColor()).setBold(true));  // Rang in Farbe und fett

        TextComponentString playerMessage = new TextComponentString(player.getName());
        playerMessage.setStyle(new Style().setColor(TextFormatting.WHITE).setBold(true));  // Spielername in weiß und fett

        TextComponentString finalMessage = new TextComponentString("");
        finalMessage.appendSibling(playerMessage);
        finalMessage.appendText(" is now ");
        finalMessage.appendSibling(rankMessage);

        server.getPlayerList().sendMessage(finalMessage);

        // Bestätigung für den Befehlssender
        sender.sendMessage(new TextComponentString("Assigned rank " + rank.getDisplayName() + " to " + player.getName()));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, net.minecraft.util.math.BlockPos targetPos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        } else if (args.length == 2) {
            List<String> ranks = new ArrayList<>();
            for (Rank rank : Rank.values()) {
                ranks.add(rank.getName());
            }
            return getListOfStringsMatchingLastWord(args, ranks);
        }
        return super.getTabCompletions(server, sender, args, targetPos);
    }
}
