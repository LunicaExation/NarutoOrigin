package com.tyler.narutoorigin;

import com.tyler.narutoorigin.viewinv.CommandViewInv;
import com.tyler.narutoorigin.config.NarutoOriginsConfig;
import com.tyler.narutoorigin.coremod.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = NarutoOriginMod.MODID, version = NarutoOriginMod.VERSION)
public class NarutoOriginMod {
    public static final String MODID = "narutoorigin";
    public static final String VERSION = "1.10.90";

    private static PlayerDataManager playerDataManager;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Initialize the configuration
        NarutoOriginsConfig.init(event);

        // Register other items, blocks, etc., if needed
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        // Initialize PlayerDataManager with the current MinecraftServer
        playerDataManager = new PlayerDataManager(event.getServer());

        // Register commands
        CommandSimulate simulateCommand = new CommandSimulate();
        event.registerServerCommand(simulateCommand);
        event.registerServerCommand(new CommandClaimOrigin(simulateCommand, playerDataManager)); // Pass playerDataManager
        event.registerServerCommand(new CommandDoItForMe());
        event.registerServerCommand(new CommandExecuteDojutsu());
        event.registerServerCommand(new CommandExecuteKKG());
        event.registerServerCommand(new CommandExecuteMedic());
        event.registerServerCommand(new CommandExecuteTaijutsu());
        event.registerServerCommand(new CommandExecuteElement());
        event.registerServerCommand(new CommandExecuteTailedBeast());
        event.registerServerCommand(new CommandMakeMeAShinobi(playerDataManager)); // Pass playerDataManager
        event.registerServerCommand(new CommandResetMe());
        event.registerServerCommand(new CommandPlayerClick());
        event.registerServerCommand(new CommandViewInv());

        // Register the ClanEffectEventHandler to the Forge event bus
        MinecraftForge.EVENT_BUS.register(new ClanEffectEventHandler(playerDataManager));
    }
}
