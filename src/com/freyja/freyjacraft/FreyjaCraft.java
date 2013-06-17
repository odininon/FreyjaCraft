package com.freyja.freyjacraft;

import com.freyja.freyjacraft.block.notGateBlock;
import com.freyja.freyjacraft.handler.ConfigurationHandler;
import com.freyja.freyjacraft.handler.GuiHandler;
import com.freyja.freyjacraft.handler.PluginHandler;
import com.freyja.freyjacraft.item.LootBall;
import com.freyja.freyjacraft.item.TestBag;
import com.freyja.freyjacraft.item.UltimateAxe;
import com.freyja.freyjacraft.lib.Settings;
import com.freyja.freyjacraft.lib.Strings;
import com.freyja.freyjacraft.plugins.FreyjaPlugin;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.ItemData;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author Freyja
 *         Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */

@Mod(modid = Strings.MODID, name = Strings.MODNAME, version = "1.0.0")
@NetworkMod(channels = {Strings.MODID})
public class FreyjaCraft
{
    @Mod.Instance(Strings.MODID)
    public static FreyjaCraft INSTANCE;

    public static Logger logger;

    public static CreativeTabs tab = new CreativeTabs(Strings.MODNAME);

    public static BiMap<Block, String> blocks = HashBiMap.create();
    public static BiMap<Item, String> items = HashBiMap.create();

    // Holds Item id and modId for every item.
    public Map<Integer, String> itemMap = new HashMap<Integer, String>();


    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        PluginHandler.init(event.getAsmData().getAll(FreyjaPlugin.class.getCanonicalName()));

        registerBlocks();
        registerItems();

        for (Block block : blocks.keySet()) {
            GameRegistry.registerBlock(block, blocks.get(block));
            LanguageRegistry.addName(block, blocks.get(block));
        }

        for (Item item : items.keySet()) {
            GameRegistry.registerItem(item, items.get(item));
            LanguageRegistry.addName(item, items.get(item));
        }

        // Gets itemData for all items
        NBTTagList list = new NBTTagList();
        GameData.writeItemData(list);
        for (int i = 0; i < list.tagCount(); i++) {
            ItemData itemData = new ItemData((NBTTagCompound) list.tagAt(i));
            this.itemMap.put(itemData.getItemId(), itemData.getModId());
        }
        NetworkRegistry.instance().registerGuiHandler(INSTANCE, new GuiHandler());
    }

    private void registerItems()
    {
        items.put(new UltimateAxe(Settings.UlimateAxe, EnumToolMaterial.EMERALD).setUnlocalizedName("ultimateAxe").setCreativeTab(tab), "Ultimate Axe");
        items.put(new LootBall(Settings.LootBall).setUnlocalizedName("lootBall"), "Loot Bag");
        items.put(new TestBag(Settings.ItemBag, 16).setUnlocalizedName("testBag").setCreativeTab(tab), "Test Bag");
    }

    private void registerBlocks()
    {
        blocks.put(new notGateBlock(Settings.NoteGate).setUnlocalizedName("notGate").setCreativeTab(tab), "Not Gate");

    }

    @Mod.Init
    public void init(FMLInitializationEvent event)
    {

    }

    @Mod.PostInit
    public void postInit(FMLPostInitializationEvent event)
    {
        PluginHandler.initPlugins();
    }
}
