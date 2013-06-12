package com.freyja.freyjacraft.handler;

import com.freyja.freyjacraft.FreyjaCraft;
import com.freyja.freyjacraft.plugins.FreyjaPlugin;
import cpw.mods.fml.common.discovery.ASMDataTable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

/**
 * @author Freyja
 *         Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
public class PluginHandler
{
    private static List<Class<FreyjaPlugin>> plugins = new ArrayList<Class<FreyjaPlugin>>();

    public static void init(Set<ASMDataTable.ASMData> all)
    {
        if (all != null && !all.isEmpty()) {
            for (ASMDataTable.ASMData data : all) {

                String className = data.getClassName();

                try {
                    Class<FreyjaPlugin> clazz = (Class<FreyjaPlugin>) Class.forName(className);
                    FreyjaPlugin annot = clazz.getAnnotation(FreyjaPlugin.class);
                    className = getClassDisplayName(clazz);

                    plugins.add(clazz);
                    FreyjaCraft.logger.info("Plugin loaded: " + className);

                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    private static String getClassDisplayName(Class<FreyjaPlugin> clazz)
    {
        return clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1);
    }

    public static void initPlugins()
    {
        for (Class<FreyjaPlugin> clazz : plugins) {
            String className = getClassDisplayName(clazz);

            try {
                if (postInit(clazz)) {
                    FreyjaCraft.logger.info("Plugin initialized... " + className);
                }
            } catch (Exception ex) {
                FreyjaCraft.logger.info("Plugin failed to initialize.");
                FreyjaCraft.logger.log(Level.INFO, "Reason for failure: {0}", ex.getLocalizedMessage());
            }
        }

    }

    private static boolean postInit(Class<FreyjaPlugin> clazz)
    {
        boolean successful = false;
        Method[] methods = clazz.getDeclaredMethods();
        if (methods == null) {
            return false;
        }

        for (Method method : methods) {
            if (method != null && method.isAnnotationPresent(FreyjaPlugin.init.class)) {
                try {
                    method.invoke(clazz.newInstance());
                    successful = true;
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return successful;
    }
}
