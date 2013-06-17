package com.freyja.freyjacraft.Inventories;

import com.freyja.freyjacraft.item.ItemBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author Freyja
 *         Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ContainerItemBag extends Container {

    private EntityPlayer player;

    public ContainerItemBag(IInventory inventory, ItemStack heldItem) {
        this.player = ((InventoryPlayer) inventory).player;

        layoutContainer(inventory, heldItem);
    }

    private void layoutContainer(IInventory inventory, final ItemStack heldItem) {
        ItemBag item = (ItemBag) heldItem.getItem();

        for (int l = 0; l < 3; ++l) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlotToContainer(new Slot(inventory, i1 + l * 9 + 9, 49 + i1 * 18, 140 + l * 18));
            }
        }

        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            final int finalHotbarSlot = hotbarSlot;
            addSlotToContainer(new Slot(inventory, finalHotbarSlot, 49 + 18 * hotbarSlot, 198) {

                @Override
                public boolean canTakeStack(EntityPlayer entityPlayer) {
                    return !(player.inventory.getStackInSlot(finalHotbarSlot) == player.getHeldItem());
                }
            });
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }
}
