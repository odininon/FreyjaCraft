package com.freyja.freyjacraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * @author Freyja
 *         Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ItemBag extends Item {

    public ItemBag(int par1) {
        super(par1);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void getSubItems(int id, CreativeTabs creativeTabs, List items) {
        for (int i = 0; i < 16; i++) {
            items.add(new ItemStack(id, 1, i));
        }
    }
}
