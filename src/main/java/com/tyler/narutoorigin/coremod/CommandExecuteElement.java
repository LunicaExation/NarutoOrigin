package com.tyler.narutoorigin.coremod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class CommandExecuteElement extends CommandBase {

    @Override
    public String getName() {
        return "executeElement";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/executeElement <playerName> <elementType>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws WrongUsageException {
        if (args.length < 2) {
            throw new WrongUsageException("Usage: /executeElement <playerName> <elementType>");
        }

        String playerName = args[0];
        String elementType = args[1];

        switch (elementType.toLowerCase()) {
            case "fire":
                server.getCommandManager().executeCommand(server, "/give " + playerName + " narutomod:katon");
                break;
            case "water":
                server.getCommandManager().executeCommand(server, "/give " + playerName + " narutomod:suiton");
                break;
            case "earth":
                server.getCommandManager().executeCommand(server, "/give " + playerName + " narutomod:doton");
                break;
            case "lightning":
                server.getCommandManager().executeCommand(server, "/give " + playerName + " narutomod:raiton");
                break;
            case "wind":
                server.getCommandManager().executeCommand(server, "/give " + playerName + " narutomod:futon");
                break;
            default:
                throw new WrongUsageException("Unknown element type: " + elementType);
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
