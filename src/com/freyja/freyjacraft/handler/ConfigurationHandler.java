package com.freyja.freyjacraft.handler;

import com.freyja.freyjacraft.FreyjaCraft;
import com.freyja.freyjacraft.lib.Settings;
import net.minecraftforge.common.Configuration;

import java.io.File;
import java.util.logging.Level;

/**
 * @author Freyja
 *         Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class ConfigurationHandler
{
    public static Configuration config;

    public static void init(File suggestedConfigurationFile)
    {
        config = new Configuration(suggestedConfigurationFile, false);

        try {
            config.load();

            Settings.LootBall = config.getItem("LootBall", Settings.LootBall_DEFAULT).getInt();
            Settings.UlimateAxe = config.getItem("UltimateAxe", Settings.UltimateAxe_DEFAULT).getInt();
            Settings.NoteGate = config.getBlock("Note Gate", Settings.NoteGate_DEFAULT).getInt();
            Settings.ItemBag = config.getItem("ItemBag", Settings.LootBall_DEFAULT).getInt();

        } catch (Exception ex) {
            FreyjaCraft.logger.info("Error Loading configuration.");
            FreyjaCraft.logger.log(Level.INFO, "Caused by {0}", ex.getLocalizedMessage());
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}
