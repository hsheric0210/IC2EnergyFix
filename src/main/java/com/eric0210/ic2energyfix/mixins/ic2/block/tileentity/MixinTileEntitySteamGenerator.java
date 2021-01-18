package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import ic2.core.block.machine.tileentity.TileEntitySteamGenerator;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntitySteamGenerator.class)
public abstract class MixinTileEntitySteamGenerator
{
	private final int maxHeatupHU = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/steamgenerator/maxHeatupHU");
	private final int baseHUNeed = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/steamgenerator/baseHUNeed");
	private final float huNeedRatio = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/steamgenerator/huNeedRatio");
	private final float huNeedMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/steamgenerator/huNeedMultiplier");

	@Shadow(remap = false)
	private int heatinput;

	@Shadow(remap = false)
	private int pressurevalve;

	@Shadow(remap = false)
	private int requestHeat(@SuppressWarnings("unused") final int requestHeat)
	{
		return 0;
	}

	@Shadow(remap = false)
	private void heatup(@SuppressWarnings("unused") final int heatinput)
	{
	}

	@Inject(method = "heatupmax", at = @At("HEAD"), cancellable = true, remap = false)
	private void heatupmax(final CallbackInfoReturnable<Boolean> callback)
	{
		heatinput = requestHeat(maxHeatupHU);
		if (heatinput > 0)
		{
			heatup(heatinput);
			callback.setReturnValue(true);
		}
		callback.setReturnValue(false);
	}

	@ModifyVariable(method = "getOutputfluid", at = @At("HEAD"), name = "hu_need", ordinal = 0, remap = false)
	private int getOutputfluid(@SuppressWarnings("unused") final int hu_need_per_mb)
	{
		return baseHUNeed + Math.round(pressurevalve / huNeedRatio * huNeedMultiplier);
	}
}
