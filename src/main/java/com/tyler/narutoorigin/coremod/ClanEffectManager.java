package com.tyler.narutoorigin.coremod;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class ClanEffectManager {

    public static void applyEffectsBasedOnClan(EntityPlayerMP player, String clan) {
        if (player == null || clan == null || clan.isEmpty()) {
            return;
        }


        // Andere Clanspezifische Effekte
        switch (clan) {
            case "Hyuga":
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, Integer.MAX_VALUE, 1, true, false)); // Resistance II
                break;
            case "Uchiha":
            player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, Integer.MAX_VALUE, 1, true, false)); // Strength II
                break;
                case "Senju":
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, Integer.MAX_VALUE, 2, true, false)); // Resistance III
                player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, Integer.MAX_VALUE, 2, true, false)); // Health Boost III
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, Integer.MAX_VALUE, 2, true, false)); // Regeneration III
                break;
            // Weitere Clan-Effekte...
            case "Lee":
                player.addPotionEffect(new PotionEffect(MobEffects.SPEED, Integer.MAX_VALUE, 2, true, false)); // Speed III
                player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, Integer.MAX_VALUE, 3, true, false)); // Strength IIII
                break;
            case "Uzumaki":
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, Integer.MAX_VALUE, 0, true, false)); // Regeneration I
                player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, Integer.MAX_VALUE, 1, true, false)); // Health Boost II
                break;
            case "Kaguya":
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, Integer.MAX_VALUE, 1, true, false)); // Resistance II
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, Integer.MAX_VALUE, 0, true, false)); // Regeneration I
                break;
            case "Otsusuki":
                player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, Integer.MAX_VALUE, 2, true, false)); // Resistance III
                player.addPotionEffect(new PotionEffect(MobEffects.HEALTH_BOOST, Integer.MAX_VALUE, 2, true, false)); // Health Boost III
                player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, Integer.MAX_VALUE, 2, true, false)); // Strength III
                break;
            case "Namikaze":
                player.addPotionEffect(new PotionEffect(MobEffects.SPEED, Integer.MAX_VALUE, 2, true, false)); // Speed III
                break;
            default:
                break;
        }
    }

    // Methode zum Entfernen der Clan-basierten Effekte
    public static void removeEffectsBasedOnClan(EntityPlayerMP player) {
        if (player == null) {
            return;
        }

        // Entferne alle spezifischen Effekte, die durch Clans hinzugefügt werden
        player.removePotionEffect(MobEffects.STRENGTH);
        player.removePotionEffect(MobEffects.RESISTANCE);
        player.removePotionEffect(MobEffects.HEALTH_BOOST);
        player.removePotionEffect(MobEffects.REGENERATION);
        player.removePotionEffect(MobEffects.SPEED);

    }

}
