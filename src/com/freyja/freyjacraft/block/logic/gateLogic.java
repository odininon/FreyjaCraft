package com.freyja.freyjacraft.block.logic;

import com.freyja.freyjacraft.util.Position;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author Freyja
 *         Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class gateLogic extends TileEntity {

    // 0 - NOT gate
    private final int type;

    public gateLogic(int i) {
        this.type = i;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

    public int rotate(int metadata) {
        return (metadata + 1) & 3;
    }

    public ForgeDirection getDirection(int meta) {
        return ForgeDirection.getOrientation((meta + 3) & 6);
    }

    public boolean getInput(int meta) {
        Position position = new Position(this.xCoord, this.yCoord, this.zCoord, getDirection(meta));

        position.moveForwards(1);
        Block block = Block.blocksList[worldObj.getBlockId((int) position.x, (int) position.y, (int) position.z)];

        return block != null && worldObj.getIndirectPowerOutput((int) position.x, (int) position.y, (int) position.z, getDirection(meta).ordinal());
    }

    public boolean getOutput(int meta) {
        return !getInput(meta);
    }
}
