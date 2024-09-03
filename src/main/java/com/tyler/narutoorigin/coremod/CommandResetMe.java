package com.tyler.narutoorigin.coremod;

import com.tyler.narutoorigin.config.NarutoOriginsConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandResetMe extends CommandBase {

    @Override
    public String getName() {
        return "resetMe"; // Der Befehl bleibt derselbe
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/resetMe";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) sender;

            // Leere das Inventar des Spielers, der den Befehl ausgeführt hat
            player.inventory.clear(); // Leert das Inventar des Spielers

            // Keine Benachrichtigung an den Spieler ausgeben
        } else {
            throw new CommandException("This command can only be executed by a player.");
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true; // Erlaubt es jedem Spieler, den Befehl auszuführen
    }

 @Override
public int getRequiredPermissionLevel() {
    return 0; // Jeder kann dieses Kommando ausführen
}

}
