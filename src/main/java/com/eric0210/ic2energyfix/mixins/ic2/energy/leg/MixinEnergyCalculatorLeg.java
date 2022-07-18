package com.eric0210.ic2energyfix.mixins.ic2.energy.leg;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import ic2.api.energy.IEnergyNet;
import ic2.core.energy.leg.EnergyCalculatorLeg;
import ic2.core.util.ConfigUtil;

@Mixin(EnergyCalculatorLeg.class)
public abstract class MixinEnergyCalculatorLeg
{
	private static final boolean disableSyncSourceTierLimit = ConfigUtil.getBool(IC2EnergyFixConfig.get(), "balance/energy/energyCalculator/disableSyncSourceTierLimit");
	private static final boolean disableDistributeSourceTierLimit = ConfigUtil.getBool(IC2EnergyFixConfig.get(), "balance/energy/energyCalculator/disableDistributeSourceTierLimit");

	@Redirect(method = "runSyncStep(Lic2/core/energy/grid/EnergyNetLocal;)Z", at = @At(value = "INVOKE", target = "Lic2/api/energy/IEnergyNet;getPowerFromTier(I)D", remap = false), remap = false)
	private double injectDisableSyncSourceTierLimit(final IEnergyNet instance, final int tier)
	{
		return disableSyncSourceTierLimit ? Double.POSITIVE_INFINITY : instance.getPowerFromTier(tier);
	}

	@Redirect(method = "distributeMultiple", at = @At(value = "INVOKE", target = "Lic2/api/energy/IEnergyNet;getPowerFromTier(I)D", remap = false), remap = false)
	private static double injectDisableDistributeSourceTierLimit(final IEnergyNet instance, final int tier)
	{
		return disableDistributeSourceTierLimit ? Double.POSITIVE_INFINITY : instance.getPowerFromTier(tier);
	}
}
