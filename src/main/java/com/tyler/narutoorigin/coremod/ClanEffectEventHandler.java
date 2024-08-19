package com.tyler.narutoorigin.coremod;

import com.tyler.narutoorigin.coremod.ClanEffectManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClanEffectEventHandler {

    private final PlayerDataManager playerDataManager;

    public ClanEffectEventHandler(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerLoggedInEvent event) {
        applyEffects((EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        applyEffects((EntityPlayerMP) event.player);
    }

    private void applyEffects(EntityPlayerMP player) {
        PlayerData playerData = playerDataManager.getPlayerData(player.getName());

        if (playerData.hasClaimedOrigin()) {
            ClanEffectManager.applyEffectsBasedOnClan(player, playerData.getClan());
        }
    }
}
