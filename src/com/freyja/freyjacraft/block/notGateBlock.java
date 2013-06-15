package com.freyja.freyjacraft.block;

import com.freyja.freyjacraft.block.logic.gateLogic;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author Freyja
 *         Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class notGateBlock extends BlockContainer {
    public notGateBlock(int par1) {
        super(par1, Material.circuits);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new gateLogic(0);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        if (!world.isRemote) {
            int metadata = world.getBlockMetadata(x, y, z);
            TileEntity blockTileEntity = world.getBlockTileEntity(x, y, z);

            if (blockTileEntity instanceof gateLogic) {
                world.setBlockMetadataWithNotify(x, y, z, ((gateLogic) blockTileEntity).rotate(metadata), 2);
            }
            return true;
        }
        return false;
    }

    @Override
    public int isProvidingStrongPower(IBlockAccess world, int x, int y, int z, int side) {
        gateLogic tileEntity = (gateLogic) world.getBlockTileEntity(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);
        return (tileEntity.getOutput(metadata)) ? (side == tileEntity.getDirection(metadata).ordinal()) ? 15 : 0 : 0;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        gateLogic tileEntity = (gateLogic) world.getBlockTileEntity(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);
        return (tileEntity.getOutput(metadata)) ? (side == tileEntity.getDirection(metadata).ordinal()) ? 15 : 0 : 0;
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }
}
