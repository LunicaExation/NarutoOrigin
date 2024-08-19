package com.tyler.narutoorigin.coremod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class CommandExecuteDojutsu extends CommandBase {

    @Override
    public String getName() {
        return "executeDojutsu";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/executeDojutsu <playerName> <dojutsuType>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws WrongUsageException {
        if (args.length < 2) {
            throw new WrongUsageException("Usage: /executeDojutsu <playerName> <dojutsuType>");
        }

        String playerName = args[0];
        String dojutsuType = args[1];

        switch (dojutsuType.toLowerCase()) {
            case "sharingan":
                server.getCommandManager().executeCommand(server, "advancement grant " + playerName + " from narutomod:sharinganopened");
                server.getCommandManager().executeCommand(server, "advancement revoke " + playerName + " from narutomod:mangekyosharinganopened");
                break;
            case "byakugan":
                server.getCommandManager().executeCommand(server, "advancement grant " + playerName + " from narutomod:byakuganopened");
                server.getCommandManager().executeCommand(server, "advancement revoke " + playerName + " from narutomod:tenseigan_achieved");
                break;
            case "rinnegan":
                server.getCommandManager().executeCommand(server, "advancement grant " + playerName + " from narutomod:rinneganawakened");
                server.getCommandManager().executeCommand(server, "advancement revoke " + playerName + " from narutomod:rinnesharinganactivated");
                break;
        }
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
