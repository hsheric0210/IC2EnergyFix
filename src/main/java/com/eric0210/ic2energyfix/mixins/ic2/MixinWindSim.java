package com.eric0210.ic2energyfix.mixins.ic2;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import ic2.core.WindSim;
import ic2.core.util.ConfigUtil;

@Mixin(WindSim.class)
public class MixinWindSim
{
	private final double defaultMultiplier = ConfigUtil.getDouble(IC2EnergyFixConfig.get(), "balance/wind/defaultMultiplier");
	private final double rainMultiplier = ConfigUtil.getDouble(IC2EnergyFixConfig.get(), "balance/wind/rainMultiplier");
	private final double stormMultilier = ConfigUtil.getDouble(IC2EnergyFixConfig.get(), "balance/wind/stormMultiplier");

	@ModifyConstant(method = "getWindAt", constant = @Constant(doubleValue = 2.4), remap = false)
	private double injectDefaultModifier(final double _2_4)
	{
		return defaultMultiplier;
	}

	@ModifyConstant(method = "getWindAt", constant = @Constant(doubleValue = 1.25), remap = false)
	private double injectRainModifier(final double _1_25)
	{
		return rainMultiplier;
	}

	@ModifyConstant(method = "getWindAt", constant = @Constant(doubleValue = 1.5), remap = false)
	private double injectStormModifier(final double _1_5)
	{
		return stormMultilier;
	}
}
