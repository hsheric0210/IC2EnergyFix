package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import ic2.core.block.machine.tileentity.TileEntitySteamGenerator;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntitySteamGenerator.class)
public abstract class MixinTileEntitySteamGenerator
{
	private final int maxHeatupHU = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/steamGenerator/maxHeatupHU");
	private final int baseHUNeed = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/steamGenerator/huNeedBase");
	private final float huNeedRatio = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/steamGenerator/huNeedRatio");
	private final float huNeedMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/steamGenerator/huNeedMultiplier");

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

	@ModifyConstant(method = "heatupmax", constant = @Constant(intValue = 1200), remap = false)
	private int injectHeatupmax(final int _1200)
	{
		return maxHeatupHU;
	}

	@ModifyVariable(method = "getOutputfluid", at = @At("HEAD"), name = "hu_need", ordinal = 0, remap = false)
	private int injectGetOutputfluid(@SuppressWarnings("unused") final int hu_need_per_mb)
	{
		return baseHUNeed + Math.round(pressurevalve / huNeedRatio * huNeedMultiplier);
	}
}
