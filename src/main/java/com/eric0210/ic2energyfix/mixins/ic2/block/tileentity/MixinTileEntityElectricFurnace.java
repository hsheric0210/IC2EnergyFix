package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import ic2.core.block.machine.tileentity.TileEntityElectricFurnace;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityElectricFurnace.class)
public class MixinTileEntityElectricFurnace
{
	private static final int newOperationLength = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generic/electricFurnaceOperationLength");

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 100))
	private static int injectOperationLength(final int _100)
	{
		return newOperationLength;
	}
}
