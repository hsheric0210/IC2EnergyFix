package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import ic2.core.block.machine.tileentity.TileEntityOreWashing;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityOreWashing.class)
public class MixinTileEntityOreWashing
{
	private static final int operationLength = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generic/oreWasherOperationLength");

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 500))
	private static int injectOperationLength(final int _500)
	{
		return operationLength;
	}
}
