package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import ic2.core.block.machine.tileentity.TileEntityFermenter;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityFermenter.class)
public class MixinTileEntityFermenter
{
	private final int heatRequest = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/fermenter/heatRequest");
	private final int inputTankCapacity = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/fermenter/inputTankCapacity");
	private final int outputTankCapacity = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/fermenter/outputTankCapacity");

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 10000))
	public int injectInputTankCapacity(final int _10000)
	{
		return inputTankCapacity;
	}

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 2000))
	public int injectOutputTankCapacity(final int _2000)
	{
		return outputTankCapacity;
	}

	@ModifyConstant(method = "work", constant = @Constant(intValue = 100), remap = false)
	private int injectHeatRequest(final int _100)
	{
		return heatRequest;
	}
}
