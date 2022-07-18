package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import ic2.core.block.machine.tileentity.TileEntityPump;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityPump.class)
public class MixinTileEntityPump
{
	private static final int newOperationLength = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generic/pumpOperationLength");

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 20, ordinal = 1))
	private int injectOperationLength(final int _20)
	{
		return newOperationLength;
	}
}
