package com.tyler.narutoorigin.gui;

import com.tyler.narutoorigin.NarutoOriginMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int ORIGIN_SELECTION_GUI = 0;  // ID für das Origin Selection GUI

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;  // Server-seitige GUI-Elemente werden hier nicht benötigt
    }

   @Override
public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    if (ID == ORIGIN_SELECTION_GUI) {
        if (player instanceof EntityPlayerMP) {
            return new GuiOriginSelection((EntityPlayerMP) player, NarutoOriginMod.simulationResults.get(0), NarutoOriginMod.commandSimulate, NarutoOriginMod.playerDataManager);
        }
    }
    return null;
}

    }

