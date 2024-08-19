package com.tyler.narutoorigin.coremod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class CommandExecuteKKG extends CommandBase {

    @Override
    public String getName() {
        return "executeKKG";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/executeKKG <playerName> <kkgType>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws WrongUsageException {
        if (args.length < 2) {
            throw new WrongUsageException("Usage: /executeKKG <playerName> <kkgType>");
        }

        String playerName = args[0];
        String kkgType = args[1];

        switch (kkgType.toLowerCase()) {
            case "explosion":
                server.getCommandManager().executeCommand(server, "advancement grant " + playerName + " from narutomod:bakuton_acquired");
                break;
            case "boil":
                server.getCommandManager().executeCommand(server, "advancement grant " + playerName + " from narutomod:futton_acquired");
                break;
            case "ice":
                server.getCommandManager().executeCommand(server, "advancement grant " + playerName + " from narutomod:hyoton_acquired");
                break;
            case "magnet":
                server.getCommandManager().executeCommand(server, "advancement grant " + playerName + " from narutomod:jiton_acquired");
                break;
            case "dust":
                server.getCommandManager().executeCommand(server, "advancement grant " + playerName + " from narutomod:kekkei_tota_awakened");
                break;
            case "storm":
                server.getCommandManager().executeCommand(server, "advancement grant " + playerName + " from narutomod:ranton_acquired");
                break;
            case "scorch":
                server.getCommandManager().executeCommand(server, "advancement grant " + playerName + " from narutomod:shakuton_acquired");
                break;
            case "shikotsumyaku":
                server.getCommandManager().executeCommand(server, "advancement grant " + playerName + " from narutomod:shikotsumyaku_acquired");
                break;
            case "lava":
                server.getCommandManager().executeCommand(server, "advancement grant " + playerName + " from narutomod:yooton_acquired");
                break;
            case "wood":
                server.getCommandManager().executeCommand(server, "advancement grant " + playerName + " from narutomod:mokuton_acquired");
                break;
            default:
                throw new WrongUsageException("Unknown KKG type: " + kkgType);
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(4, getName());
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 4;
    }
}
