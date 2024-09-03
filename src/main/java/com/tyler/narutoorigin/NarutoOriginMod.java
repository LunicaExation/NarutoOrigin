package com.tyler.narutoorigin;

import com.tyler.narutoorigin.config.NarutoOriginsConfig;
import com.tyler.narutoorigin.coremod.*;

import com.tyler.narutoorigin.ranks.CommandAssignRank;
import com.tyler.narutoorigin.ranks.CommandRemoveRank;
import com.tyler.narutoorigin.ranks.RankManager;
import com.tyler.narutoorigin.viewinv.CommandViewInv;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.List;

@Mod(modid = NarutoOriginMod.MODID, version = NarutoOriginMod.VERSION)
public class NarutoOriginMod {
    public static final String MODID = "narutoorigin";
    public static final String VERSION = "1.11.78";



    public static PlayerDataManager playerDataManager;
    public static CommandSimulate commandSimulate;
    public static NarutoOriginMod instance;

    // Füge dieses Feld hinzu, um die Simulationsergebnisse zu speichern
    public static List<String[]> simulationResults;

    // Hinzufügen des RankManager-Felds
    public static RankManager rankManager;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Initialize the configuration
        NarutoOriginsConfig.init(event);
        // Set the instance
        instance = this;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        // Initialize PlayerDataManager with the current MinecraftServer
        playerDataManager = new PlayerDataManager(event.getServer());

        // Initialize RankManager
        rankManager = new RankManager();

        // Register commands
        commandSimulate = new CommandSimulate();
        event.registerServerCommand(commandSimulate);
        event.registerServerCommand(new CommandClaimOrigin(commandSimulate, playerDataManager)); // Pass playerDataManager
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

        // Register the RankManager commands
        event.registerServerCommand(new CommandAssignRank(rankManager));
        event.registerServerCommand(new CommandRemoveRank(rankManager));

        // Register the ClanEffectEventHandler to the Forge event bus
        MinecraftForge.EVENT_BUS.register(new ClanEffectEventHandler(playerDataManager));
    }
}
