package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import java.util.Locale;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import ic2.core.block.generator.tileentity.TileEntitySemifluidGenerator;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntitySemifluidGenerator.class)
public abstract class MixinTileEntitySemifluidGenerator extends MixinTileEntityBaseGenerator
{
	private static final int energyCapacity = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generator/semifluid/energyCapacity");
	private static final int fluidTankCapacity = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generator/semifluid/fluidTankCapacity");

	@Shadow(remap = false)
	public static void addFuel(final String fluidName, final long energyPerMb, final long energyPerTick)
	{
	}

	private static int getEnergyPerMb(final String fluidName)
	{
		return ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generator/semifluid/" + fluidName + "/energyPerMb");
	}

	private static int getEnergyPerTick(final String fluidName)
	{
		return ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generator/semifluid/" + fluidName + "/energyPerTick");
	}

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 32000))
	private static int injectEnergyCapacity(final int _32000)
	{
		return energyCapacity;
	}

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 10000))
	private static int injectTankCapacity(final int _10000)
	{
		return fluidTankCapacity;
	}

	@Redirect(method = "init", at = @At(value = "INVOKE", target = "Lic2/core/block/generator/tileentity/TileEntitySemifluidGenerator;addFuel(Ljava/lang/String;JJ)V", remap = false), remap = false)
	private static void injectFuelEnergyModifier(final String fluidName, long energyPerMb, long energyPerTick)
	{
		switch (fluidName.toLowerCase(Locale.ROOT))
		{
			case "oil":
				energyPerMb = getEnergyPerMb("oil");
				energyPerTick = getEnergyPerTick("oil");
				break;
			case "fuel":
				energyPerMb = getEnergyPerMb("fuel");
				energyPerTick = getEnergyPerTick("fuel");
				break;
			case "biomass":
				energyPerMb = getEnergyPerMb("biomass");
				energyPerTick = getEnergyPerTick("biomass");
				break;
			case "bio.ethanol":
				energyPerMb = getEnergyPerMb("bioethanol");
				energyPerTick = getEnergyPerTick("bioethanol");
				break;
			case "ic2biogas":
				energyPerMb = getEnergyPerMb("biogas");
				energyPerTick = getEnergyPerTick("biogas");
				break;
			case "ic2creosote":
				energyPerMb = getEnergyPerMb("creosote");
				energyPerTick = getEnergyPerTick("creosote");
				break;
			default:
		}

		addFuel(fluidName, energyPerMb, energyPerTick);
	}
}
