package com.eric0210.ic2energyfix;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class IC2EnergyFix implements IFMLLoadingPlugin
{
	private static final Logger LOGGER = Logger.getLogger("IC2EnergyFix");

	public IC2EnergyFix()
	{
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins.ic2energyfix.json");
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

	public static void info(final String message)
	{
		LOGGER.info(message);
	}

	public static void warn(final String message)
	{
		LOGGER.warning(message);
	}

	public static void warn(final String message, final Throwable thrown)
	{
		LOGGER.log(Level.WARNING, message, thrown);
	}

	public static void severe(final String message)
	{
		LOGGER.severe(message);
	}

	public static void severe(final String message, final Throwable thrown)
	{
		LOGGER.log(Level.SEVERE, message, thrown);
	}

	public static void debug(final String message)
	{
		LOGGER.log(Level.FINE, "[DEBUG] " + message);
	}

	private static final String[] ZERO_LENGTH_STRING_ARRAY = new String[0];
}
