package com.tyler.narutoorigin.coremod;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.narutomod.procedure.ProcedureLocateEntityCommandExecuted;

import java.util.HashMap;

public class CommandExecuteTailedBeast extends CommandBase {

    @Override
    public String getName() {
        return "executeTailedBeast";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/executeTailedBeast <playerName> <tailedBeast>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws WrongUsageException {
        if (args.length < 2) {
            throw new WrongUsageException("Usage: /executeTailedBeast <playerName> <tailedBeast>");
        }

        String playerName = args[0];
        String tailedBeast = args[1];
        EntityPlayerMP player = server.getPlayerList().getPlayerByUsername(playerName);

        if (player != null) {
            String tailedBeastId = "";

            switch (tailedBeast.toLowerCase()) {
                case "shukaku":
                    tailedBeastId = "1";
                    break;
                case "matatabi":
                    tailedBeastId = "2";
                    break;
                case "isobu":
                    tailedBeastId = "3";
                    break;
                case "son-goku":
                    tailedBeastId = "4";
                    break;
                case "kokuo":
                    tailedBeastId = "5";
                    break;
                case "saiken":
                    tailedBeastId = "6";
                    break;
                case "chomei":
                    tailedBeastId = "7";
                    break;
                case "gyuki":
                    tailedBeastId = "8";
                    break;
                case "kurama":
                    tailedBeastId = "9";
                    break;
                default:
                    sender.sendMessage(new TextComponentString("Unknown Tailed Beast: " + tailedBeast));
                    return;
            }

            // Set up command parameters
            HashMap<String, String> cmdparams = new HashMap<>();
            cmdparams.put("0", "jinchuriki");
            cmdparams.put("1", "assign");
            cmdparams.put("2", playerName);
            cmdparams.put("3", tailedBeastId);

            // Set up dependencies
            HashMap<String, Object> $_dependencies = new HashMap<>();
            $_dependencies.put("entity", player);
            $_dependencies.put("cmdparams", cmdparams);

            // Execute the procedure
            ProcedureLocateEntityCommandExecuted.executeProcedure($_dependencies);

            sender.sendMessage(new TextComponentString("Tailed Beast " + tailedBeast + " has been assigned to " + playerName));
        } else {
            sender.sendMessage(new TextComponentString("Player not found"));
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
