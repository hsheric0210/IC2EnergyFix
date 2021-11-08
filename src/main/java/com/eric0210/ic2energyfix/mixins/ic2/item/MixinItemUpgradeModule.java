package com.eric0210.ic2energyfix.mixins.ic2.item;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import ic2.core.item.ItemUpgradeModule;
import ic2.core.util.ConfigUtil;

@Mixin(ItemUpgradeModule.class)
public class MixinItemUpgradeModule
{
	private final double overclockProcessTimeMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/upgrade/overclock/processTimeMultiplier");
	private final double overclockEnergyDemandMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/upgrade/overclock/energyDemandMultiplier");

	private final int energystorageExtraEnergyStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/upgrade/energyStorage/extraEnergyStorage");

	@ModifyConstant(method = "getProcessTimeMultiplier", constant = @Constant(doubleValue = 0.7), remap = false)
	public double injectGetProcessTimeMultiplier(final double _0_7)
	{
		return overclockProcessTimeMultiplier;
	}

	@ModifyConstant(method = "getEnergyDemandMultiplier", constant = @Constant(doubleValue = 1.6), remap = false)
	public double injectGetEnergyDemandMultiplier(final double _1_6)
	{
		return overclockEnergyDemandMultiplier;
	}

	@ModifyConstant(method = "getExtraEnergyStorage", constant = @Constant(intValue = 10000), remap = false)
	public int injectGetExtraEnergyStorage(final int _10000)
	{
		return energystorageExtraEnergyStorage;
	}
}
