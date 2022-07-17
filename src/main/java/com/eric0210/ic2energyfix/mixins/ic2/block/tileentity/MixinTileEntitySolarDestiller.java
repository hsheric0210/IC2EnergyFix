package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import ic2.core.block.machine.tileentity.TileEntitySolarDestiller;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntitySolarDestiller.class)
public class MixinTileEntitySolarDestiller
{
	private final int hotTickrate = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/solarDestiller/hotBiomeTickrate");
	private final int coldTickrate = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/solarDestiller/coldBiomeTickrate");
	private final int defaultTickrate = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/solarDestiller/defaultTickrate");

	@ModifyConstant(method = "getTickRate", constant = @Constant(intValue = 36), remap = false)
	private int injectHotBiomeTickRate(final int _36)
	{
		return hotTickrate;
	}

	@ModifyConstant(method = "getTickRate", constant = @Constant(intValue = 144), remap = false)
	private int injectColdBiomeTickRate(final int _144)
	{
		return coldTickrate;
	}

	@ModifyConstant(method = "getTickRate", constant = @Constant(intValue = 72), remap = false)
	private int injectDefaultTickRate(final int _72)
	{
		return defaultTickrate;
	}
}
