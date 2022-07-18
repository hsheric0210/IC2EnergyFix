package com.eric0210.ic2energyfix;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.fml.relauncher.CoreModManager;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.libraries.LibraryManager;
import net.minecraftforge.fml.relauncher.libraries.ModList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

/**
 * TODO
 * - More mining laser modes
 * - Adjustable dynamite explosion magnitude
 */
public class IC2EnergyFix implements IFMLLoadingPlugin
{
	public static Logger logger;

    public IC2EnergyFix()
    {
		logger = LogManager.getLogger("IC2EnergyFix");

        // HACK: Add IC2 to classpath
        // Industrialcraft2 is no longer coremod since Minecraft version 1.12
        // Thus they're aren't loaded before IC2EnergyFix loaded, leading the injection into failure.
        // To solve this, we must load the IC2 jar at the classloader
        addIC2ToClassPath();

        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.ic2energyfix.json");
    }

    private boolean addIC2ToClassPath()
    {
		// Find IC2 mod file URLs
		final List<URL> urlList = findIC2Jar();

        // Query the FMLLaunchHandler instance
        try
        {
            final Field instanceField = FMLLaunchHandler.class.getDeclaredField("INSTANCE");
            final Field classLoaderField = FMLLaunchHandler.class.getDeclaredField("classLoader");
            instanceField.setAccessible(true);
            classLoaderField.setAccessible(true);
            final FMLLaunchHandler instance = (FMLLaunchHandler) instanceField.get(null); // It's a static field
            final LaunchClassLoader classLoader = (LaunchClassLoader) classLoaderField.get(instance); // Yeah! We got a classLoader instance!
            for (final URL url : urlList)
			{
				classLoader.addURL(url);
				classLoader.getSources().remove(url); // Remove from 'sources' to prevent further problems
				logger.info("Added industrialcraft 2 mod jar to classpath: {}", url);
			}
            return true;
        }
        catch (final NoSuchFieldException | IllegalAccessException e)
        {
            return false;
        }
    }

    private List<URL> findIC2Jar()
    {
		final List<URL> urlList = new ArrayList<>(1);
        // Query minecraftDir
		File minecraftDir;
        try
        {
            final Field mcDirField = CoreModManager.class.getDeclaredField("mcDir");
            mcDirField.setAccessible(true);
            minecraftDir = (File) mcDirField.get(null);
        }
        catch (final NoSuchFieldException | IllegalAccessException e)
        {
			logger.error("Failed to access/read 'CoreModManager.mcDir'", e);
			return urlList;
        }

        final List<File> modList = LibraryManager.gatherLegacyCanidates(minecraftDir);
		for (final File modFile : modList)
			if (modFile.getName().startsWith("industrialcraft-2"))
			{
				logger.info("Found industrialcraft 2 mod jar: {}", modFile.getAbsolutePath());
				try
				{
					urlList.add(modFile.toURI().toURL());
				}
				catch (final MalformedURLException e)
				{
					logger.error(MessageFormat.format("Can''t convert {0} to URL", modFile.getAbsolutePath()), e);
				}
			}

		// Clear the MEMORY to prevent further problems
		try
		{
			final Field cacheField = ModList.class.getDeclaredField("cache");
			cacheField.setAccessible(true);
			final Map<String, ModList> cache = (Map<String, ModList>) cacheField.get(null);
			cache.remove("MEMORY");
		}
		catch (final NoSuchFieldException | IllegalAccessException e)
		{
			logger.error("Failed to access/read 'ModList.cache'", e);
			return urlList;
		}

		return urlList;
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return ZERO_LENGTH_STRING_ARRAY;
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(final Map data)
    {
    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }

    private static final String[] ZERO_LENGTH_STRING_ARRAY = new String[0];
}
