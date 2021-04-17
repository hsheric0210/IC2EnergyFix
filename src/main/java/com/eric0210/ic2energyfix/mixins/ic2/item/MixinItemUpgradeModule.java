package com.eric0210.ic2energyfix.mixins.ic2.item;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import ic2.core.item.ItemUpgradeModule;
import ic2.core.util.ConfigUtil;

@Mixin(ItemUpgradeModule.class)
public class MixinItemUpgradeModule
{
	private final double overclockProcessTimeMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/upgrade/overclock/processTimeMultiplier");
	private final double overclockEnergyDemandMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/upgrade/overclock/energyDemandMultiplier");

	private final int energystorageExtraEnergyStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/upgrade/energystorage/extraEnergyStorage");
	
	@Inject(method = "getProcessTimeMultiplier", at = @At(value = "RETURN", shift = Shift.BEFORE, ordinal = 1), cancellable = true, remap = false)
	public void processTimeMultiplier(final CallbackInfoReturnable<? super Double> callback)
	{
		callback.setReturnValue(overclockProcessTimeMultiplier);
	}

	@Inject(method = "getEnergyDemandMultiplier", at = @At(value = "RETURN", shift = Shift.BEFORE, ordinal = 1), cancellable = true, remap = false)
	public void energyDemandMultiplier(final CallbackInfoReturnable<? super Double> callback)
	{
		callback.setReturnValue(overclockEnergyDemandMultiplier);
	}

	@Inject(method = "getExtraEnergyStorage", at = @At(value = "RETURN", shift = Shift.BEFORE, ordinal = 1), cancellable = true, remap = false)
	public void extraEnergyStorage(final CallbackInfoReturnable<? super Integer> callback)
	{
		callback.setReturnValue(energystorageExtraEnergyStorage);
	}
}
