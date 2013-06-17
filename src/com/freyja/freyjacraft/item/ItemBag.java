package com.freyja.freyjacraft.item;

import com.freyja.freyjacraft.FreyjaCraft;
import com.freyja.freyjacraft.lib.GuiIds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Freyja
 *         Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public abstract class ItemBag extends Item {

    private int size;

    public ItemBag(int itemId, int size) {
        super(itemId);
        setMaxDamage(0);
        setHasSubtypes(true);
        this.size = size;
    }

    public ItemBag(int itemId) {
        this(itemId, 16);
    }

    public List<ItemStack> getItemsInBag(ItemStack itemBag) {
        List<ItemStack> items = new ArrayList<ItemStack>();

        NBTTagCompound tagCompound = itemBag.getTagCompound();

        if (!itemBag.hasTagCompound()) {
            tagCompound = new NBTTagCompound();
        }

        NBTTagList itemsList = tagCompound.getTagList("Items");

        for (int i = 0; i < itemsList.tagCount(); i++) {
            NBTTagCompound tag = (NBTTagCompound) itemsList.tagAt(i);

            items.add(ItemStack.loadItemStackFromNBT(tag));
        }

        return items;
    }

    public int numberOfItemsInBag(ItemStack itemBag) {
        return getItemsInBag(itemBag).size();
    }

    public boolean canAcceptMoreItem(ItemStack itemBag, ItemStack newItem) {
        List<ItemStack> items = getItemsInBag(itemBag);

        if (items.size() < getSize()) {
            return true;
        } else if (items.size() == getSize()) {
            for (ItemStack stack : items) {
                if (ItemStack.areItemStacksEqual(stack, newItem)) {
                    if (stack.stackSize < stack.getMaxStackSize()) {
                        return (stack.getMaxStackSize() - stack.stackSize) < newItem.stackSize;
                    }
                }
            }
        }
        return false;
    }

    public boolean canAcceptMoreAny(ItemStack itemBag) {
        return canAcceptMoreItem(itemBag, null);
    }

    public int getSize() {
        return size;
    }

    public ItemBag setSize(int size) {
        this.size = size;
        return this;
    }

    public void addList(ItemStack bag, List<ItemStack> items) {
        NBTTagCompound tagCompound = new NBTTagCompound();

        NBTTagList nbtTagList = new NBTTagList();
        NBTTagCompound compound = new NBTTagCompound();

        for (ItemStack item : items) {
            item.writeToNBT(compound);
            nbtTagList.appendTag(compound);
        }

        tagCompound.setTag("Items", nbtTagList);
        bag.setTagCompound(tagCompound);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
        if (!world.isRemote) {
            entityPlayer.openGui(FreyjaCraft.INSTANCE, GuiIds.ITEMBAG, world, x, y, z);
            return true;
        }
        return false;
    }
}
