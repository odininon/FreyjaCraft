package com.freyja.freyjacraft.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

import java.util.List;
import java.util.Random;

/**
 * @author Freyja
 *         Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class LootBall extends Item {
    private final Random rand;

    public LootBall(int par1) {
        super(par1);

        this.rand = new Random();

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void addList(ItemStack ball, List<ItemStack> items) {
        NBTTagCompound tagCompound = ball.getTagCompound();
        if (!ball.hasTagCompound()) {
            tagCompound = new NBTTagCompound();
        }

        NBTTagList nbtTagList = new NBTTagList();
        NBTTagCompound compound = new NBTTagCompound();

        for (ItemStack item : items) {
            item.writeToNBT(compound);
            nbtTagList.appendTag(compound);
        }

        tagCompound.setTag("Items", nbtTagList);
        ball.setTagCompound(tagCompound);
    }


    @ForgeSubscribe
    public void pickup(EntityItemPickupEvent event) {
        EntityPlayer player = event.entityPlayer;
        ItemStack entityItem = event.item.getEntityItem();

        if (entityItem.getItem() instanceof LootBall) {
            NBTTagCompound tagCompound = entityItem.getTagCompound();

            if (!entityItem.hasTagCompound()) {
                tagCompound = new NBTTagCompound();
            }

            NBTTagList items = tagCompound.getTagList("Items");

            for (int i = 0; i < items.tagCount(); i++) {
                NBTTagCompound compound = (NBTTagCompound) items.tagAt(i);

                if (!player.inventory.addItemStackToInventory(ItemStack.loadItemStackFromNBT(compound))) {
                    return;
                }
            }

            GameRegistry.onPickupNotification(player, event.item);
            event.item.getEntityItem().stackSize = 0;

            event.item
                    .playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand
                            .nextFloat()) * 0.7F + 1.0F) * 2.0F);
            player.onItemPickup(event.item, event.item.getEntityItem().stackSize);

            event.item.setDead();

            event.setResult(Event.Result.DENY);
        }
    }
}
