package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.machine.tileentity.TileEntityLiquidHeatExchanger;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityLiquidHeatExchanger.class)
public class MixinTileEntityLiquidHeatExchanger
{
	private final int maxHeatExchangePerTick = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/liquidHeatExchanger/maxHeatExchangePerTick");
	private final int inputTankCapacity = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/liquidHeatExchanger/inputTankCapacity");
	private final int outputTankCapacity = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/liquidHeatExchanger/outputTankCapacity");

	@Shadow(remap = false)
	@Final
	public InvSlotConsumable heatexchangerslots;

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 2000, ordinal = 0))
	public int injectInputTankCapacity(final int _2000)
	{
		return inputTankCapacity;
	}

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 2000, ordinal = 1))
	public int injectOutputTankCapacity(final int _2000)
	{
		return outputTankCapacity;
	}

	@ModifyConstant(method = "getMaxHeatEmittedPerTick", constant = @Constant(intValue = 10), remap = false)
	public int injectHeatPerExchangerFix(final int _10)
	{
		return maxHeatExchangePerTick / heatexchangerslots.size();
	}
}
