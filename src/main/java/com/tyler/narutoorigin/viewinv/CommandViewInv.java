package com.tyler.narutoorigin.viewinv;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.Collections;
import java.util.List;

public class CommandViewInv extends CommandBase {

    @Override
    public String getName() {
        return "viewInv"; // Der Befehl heißt jetzt /viewInv
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/viewInv <player>"; // Nutzungshinweis
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new CommandException("Usage: /viewInv <player>");
        }

        // Finde den Zielspieler anhand des Namens
        EntityPlayerMP targetPlayer = server.getPlayerList().getPlayerByUsername(args[0]);
        if (targetPlayer == null) {
            throw new CommandException("Player not found.");
        }

        // Prüfe, ob der Befehl von einem Spieler ausgeführt wird
        if (sender instanceof EntityPlayerMP) {
            EntityPlayerMP senderPlayer = (EntityPlayerMP) sender;

            // Zeige das Inventar des Zielspielers an
            senderPlayer.displayGUIChest(targetPlayer.inventory);
        } else {
            sender.sendMessage(new TextComponentString("This command can only be executed by a player."));
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        // Tab-Vervollständigung für den Spielernamen
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(2, this.getName()); // Nur Admins dürfen den Befehl ausführen
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2; // Setzt die Berechtigungsstufe auf 2, was bedeutet, dass nur Admins/OPs den Befehl ausführen können
    }
}
