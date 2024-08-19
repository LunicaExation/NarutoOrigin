package com.tyler.narutoorigin.coremod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandDoItForMe extends CommandBase {

    @Override
    public String getName() {
        return "DoItForMe";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/DoItForMe";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        // Überprüfe, ob keepInventory und keepNinjaXp bereits auf true gesetzt sind
        boolean keepInventory = server.getEntityWorld().getGameRules().getBoolean("keepInventory");
        boolean keepNinjaXp = server.getEntityWorld().getGameRules().getBoolean("keepNinjaXp");

        if (keepInventory && keepNinjaXp) {
            // Beide Gamerules sind bereits aktiviert
            sender.sendMessage(new TextComponentString("This command is already active."));
        } else {
            // Führe die Befehle aus, um die Regeln zu aktivieren
            server.getCommandManager().executeCommand(server, "gamerule keepInventory true");
            server.getCommandManager().executeCommand(server, "gamerule keepNinjaXp true");

            // Sende eine Bestätigungsmeldung an den Spieler
            sender.sendMessage(new TextComponentString("Gamerules keepInventory and keepNinjaXp have been set to true."));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        // Jeder Spieler kann diesen Befehl ausführen, unabhängig von der Berechtigungsstufe
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        // Setze das Berechtigungslevel auf 0, damit jeder den Befehl ausführen kann
        return 0;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }
}
