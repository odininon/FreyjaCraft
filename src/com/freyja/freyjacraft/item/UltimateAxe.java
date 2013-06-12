package com.freyja.freyjacraft.item;

import com.freyja.freyjacraft.FreyjaCraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Freyja
 *         Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class UltimateAxe extends ItemAxe
{
    public UltimateAxe(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1, par2EnumToolMaterial);
    }


    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player)
    {
        World world = player.worldObj;
        int blockId = world.getBlockId(x, y, z);

        if (!world.isRemote) {
            int meta = world.getBlockMetadata(x, y, z);

            List<ItemStack> itemDrops = new ArrayList<ItemStack>();

            if (Block.blocksList[blockId].blockMaterial != Material.wood) {
                return super.onBlockStartBreak(itemstack, x, y, z, player);
            }

            breakAllWood(world, x, y, z, blockId, meta, itemDrops);


            if (!player.capabilities.isCreativeMode) {
                ItemStack lootBall = new ItemStack(FreyjaCraft.items.inverse().get("Loot Bag"));
                LootBall.addList(lootBall, itemDrops);

                float f = 0.7F;
                double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2, lootBall);
                entityitem.delayBeforeCanPickup = 10;
                world.spawnEntityInWorld(entityitem);
            }
        }
        return true;
    }

    public void breakAllWood(World world, int x, int y, int z, int blockId, int meta, List<ItemStack> itemDrops)
    {
        for (int j = -1; j <= 1; j++) {
            for (int k = -1; k <= 1; k++) {
                for (int l = -1; l <= 1; l++) {
                    if (j == 0 && k == 0 && l == 0) { continue; }

                    if (world.getBlockId(x + j, y + k, z + l) == blockId && world.getBlockMetadata(x + j, y + k, z + l) == meta && !world
                            .isAirBlock(x + j, y + k, z + l)) {
                        world.destroyBlock(x + j, y + k, z + l, false);
                        itemDrops.add(new ItemStack(blockId, 1, meta));
                        breakAllWood(world, x + j, y + k, z + l, blockId, meta, itemDrops);
                    }
                }
            }
        }
    }
}
