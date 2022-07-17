package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;

import ic2.core.block.machine.tileentity.TileEntitySteamGenerator;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntitySteamGenerator.class)
public abstract class MixinTileEntitySteamGenerator
{
	private final int baseHeatupHU = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/steamGenerator/baseHeatupHU");
	private final int baseHUNeed = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/steamGenerator/baseHUNeed");
	private final float huNeedRatio = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/steamGenerator/huNeedRatio");
	private final float huNeedMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/steamGenerator/huNeedMultiplier");
	private final boolean enableCalcification = ConfigUtil.getBool(IC2EnergyFixConfig.get(), "balance/steamGenerator/enableCalcification");

	@Shadow(remap = false)
	private int pressure;

	@Shadow(remap = false)
	private int calcification;

	@ModifyConstant(method = "work", constant = @Constant(intValue = 1200), remap = false)
	private int injectBaseHeatupHU(final int _1200)
	{
		return baseHeatupHU;
	}

	@ModifyVariable(method = "work", at = @At(value = "FIELD", target = "Lic2/core/block/machine/tileentity/TileEntitySteamGenerator;systemHeat:F", ordinal = 0, remap = false), name = "hUneeded", remap = false)
	private float injectHUNeeded(@SuppressWarnings("unused") final float hUNeeded)
	{
		return baseHUNeed + pressure / huNeedRatio * huNeedMultiplier;
	}

	@ModifyVariable(method = "work", at = @At(value = "FIELD", target = "Lic2/core/block/machine/tileentity/TileEntitySteamGenerator;systemHeat:F", ordinal = 0, remap = false), name = "targetTemp", remap = false)
	private float injectTargetTemp(@SuppressWarnings("unused") final float targetTemp)
	{
		return baseHUNeed + pressure / huNeedRatio * huNeedMultiplier * 2.74F;
	}

	@Redirect(method = "work", at = @At(value = "FIELD", target = "Lic2/core/block/machine/tileentity/TileEntitySteamGenerator;calcification:I", ordinal = 0, opcode = Opcodes.PUTFIELD, remap = false), remap = false)
	private void injectDisableCalcification(final TileEntitySteamGenerator instance, final int newValue)
	{
		if (enableCalcification)
			calcification = newValue;
	}
}
