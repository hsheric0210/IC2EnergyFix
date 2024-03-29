package com.eric0210.ic2energyfix;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import ic2.core.IC2;
import ic2.core.util.Config;
import ic2.core.util.Config.ParseException;

public final class IC2EnergyFixConfig
{
	private static Config config;

	public static void load()
	{
		config = new Config("ic2energyfix config");

		try
		{
			config.load(IC2EnergyFix.class.getResourceAsStream("/assets/ic2/config/IC2EnergyFix.ini"));
		}
		catch (final IOException e)
		{
			throw new RuntimeException("Error loading ic2energyfix config", e);
		}

		final File configFile = getFile();

		try
		{
			if (configFile.exists())
				config.load(configFile);
		}
		catch (final ParseException | IOException e)
		{
			throw new IllegalStateException("Error loading ic2energyfix config", e);
		}
		finally
		{
			save();
		}
	}

	private static void save()
	{
		try
		{
			get().save(getFile());
		}
		catch (final IOException | RuntimeException var1)
		{
			throw new RuntimeException("Error saving user config", var1);
		}
	}

	public static Config get()
	{
		return Objects.requireNonNull(config, "config is null");
	}

	private static File getFile()
	{
		final File folder = new File(IC2.platform.getMinecraftDir(), "config");
		folder.mkdirs();
		return new File(folder, "IC2EnergyFix.ini");
	}

	private IC2EnergyFixConfig()
	{
	}
}
