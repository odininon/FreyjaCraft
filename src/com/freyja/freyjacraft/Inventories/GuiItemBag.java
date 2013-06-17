package com.freyja.freyjacraft.Inventories;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

/**
 * @author Freyja
 *         Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class GuiItemBag extends GuiContainer {


    public GuiItemBag(InventoryPlayer inventory, ItemStack heldItem) {
        super(new ContainerItemBag(inventory, heldItem));
    }

    @Override
    public void initGui() {

        this.xSize = 256;
        this.ySize = 222;

        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        mc.renderEngine.bindTexture("/mods/FreyjaCraft/textures/gui/bag.png");
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
