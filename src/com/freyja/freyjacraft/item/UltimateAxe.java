package com.freyja.freyjacraft.item;

import com.freyja.freyjacraft.FreyjaCraft;
import com.freyja.freyjacraft.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Freyja
 *         Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class UltimateAxe extends ItemAxe {
    public UltimateAxe(int par1, EnumToolMaterial par2EnumToolMaterial) {
        super(par1, par2EnumToolMaterial);
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Strings.MODID + ":" + this.getUnlocalizedName());
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) {
        World world = player.worldObj;

        int stackSize = 0;

        if (!world.isRemote) {

            int blockId = world.getBlockId(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);
            List<ItemStack> itemDrops = new ArrayList<ItemStack>();

            int i = Block.blocksList[blockId].idDropped(0, new Random(), 0);
            int i1 = Block.blocksList[blockId].damageDropped(meta);
            ItemStack itemStack = new ItemStack(i, 1, i1);
            for (ItemStack stack : player.inventory.mainInventory) {
                if (stack == null) {
                    stackSize += itemStack.getMaxStackSize();
                } else if (ItemStack.areItemStacksEqual(stack, itemStack)) {
                    stackSize += (stack.getMaxStackSize() - stack.stackSize);
                }
            }

            if (Block.blocksList[blockId].blockMaterial != Material.wood) {
                return super.onBlockStartBreak(itemstack, x, y, z, player);
            }

            breakAllWood(world, x, y, z, blockId, itemDrops, stackSize, itemstack, player);

            if (!player.capabilities.isCreativeMode && itemDrops.size() > 0) {
                ItemStack lootBall = new ItemStack(FreyjaCraft.items.inverse().get("Loot Bag"));
                LootBall.addList(lootBall, itemDrops);

                float f = 0.7F;
                double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, lootBall);
                entityitem.delayBeforeCanPickup = 10;
                world.spawnEntityInWorld(entityitem);
            } else if (player.capabilities.isCreativeMode || itemDrops.size() == 0) {
                return false;
            }
        }
        return true;
    }

    public void breakAllWood(World world, int x, int y, int z, int blockId, List<ItemStack> itemDrops, int stackSize, ItemStack itemstack, EntityPlayer player) {
        int meta = world.getBlockMetadata(x, y, z);
        ArrayList<ItemStack> drops = Block.blocksList[blockId].getBlockDropped(world, x, y, z, meta, 0);

        if (drops.size() >= stackSize) return;

        world.destroyBlock(x, y, z, false);
        itemstack.damageItem(1, player);
        for (ItemStack stack : drops) {
            itemDrops.add(stack);
        }

        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
                for (int l = -1; l <= 1; l++) {
                    if (j == 0 && k == 0 && l == 0) {
                        continue;
                    }

                    if (world.getBlockId(x + j, y + k, z + l) == blockId && !world
                            .isAirBlock(x + j, y + k, z + l)) {
                        breakAllWood(world, x + j, y + k, z + l, blockId, itemDrops, stackSize, itemstack, player);
                    }
                }
            }
        }
    }
}
