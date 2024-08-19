package com.tyler.narutoorigin.coremod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.Arrays;
import java.util.List;

public class CommandPlayerClick extends CommandBase {

    private static final List<String> ALLOWED_COMMANDS = Arrays.asList("executeKKG", "executeDojutsu", "executeElement", "executeMedic", "executeTaijutsu","executeTailedBeast");

    @Override
    public String getName() {
        return "playerClick";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/playerClick <command> <args>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) {
            throw new CommandException("Usage: /playerClick <command> <args>");
        }

        String command = args[0];
        if (!ALLOWED_COMMANDS.contains(command)) {
            sender.sendMessage(new TextComponentString("This command is not allowed to be executed via playerClick."));
            return;
        }

        if (sender instanceof EntityPlayerMP) {
            server.addScheduledTask(() -> {
                String[] commandArgs = new String[args.length - 1];
                System.arraycopy(args, 1, commandArgs, 0, commandArgs.length);

                String fullCommand = "/" + command + " " + String.join(" ", commandArgs);
                server.getCommandManager().executeCommand(server, fullCommand);
            });
        }
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
