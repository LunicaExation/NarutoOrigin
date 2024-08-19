package com.tyler.narutoorigin.coremod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class CommandExecuteMedic extends CommandBase {

    @Override
    public String getName() {
        return "executeMedic";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/executeMedic <playerName>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws WrongUsageException {
        if (args.length < 1) {
            throw new WrongUsageException("Usage: /executeMedic <playerName>");
        }

        String playerName = args[0];

        server.getCommandManager().executeCommand(server, "advancement grant " + playerName + " from narutomod:achievementmedicalgenin");
    }

 @Override
public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
    // Erlaubt nur Admins oder dem Server selbst, den Befehl auszuführen
    return sender.canUseCommand(4, getName());
}

@Override
public int getRequiredPermissionLevel() {
    // Setzt das erforderliche Berechtigungslevel auf 4, was bedeutet, dass nur der Server oder Admins den Befehl ausführen können
    return 4;
    }
}
