package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import ic2.core.block.machine.tileentity.TileEntityCanner;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityCanner.class)
public class MixinTileEntityCanner
{
	private static final int operationLength = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generic/cannerOperationLength");

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 200))
	private static int injectOperationLength(final int _200)
	{
		return operationLength;
	}
}
