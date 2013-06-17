package com.freyja.freyjacraft.handler;

import com.freyja.freyjacraft.Inventories.ContainerItemBag;
import com.freyja.freyjacraft.Inventories.GuiItemBag;
import com.freyja.freyjacraft.lib.GuiIds;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * @author Freyja
 *         Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GuiIds.ITEMBAG:
                return new ContainerItemBag(player.inventory, player.getHeldItem());
            default: return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GuiIds.ITEMBAG:
             return new GuiItemBag(player.inventory, player.getHeldItem());
            default: return null;
        }
    }
}
