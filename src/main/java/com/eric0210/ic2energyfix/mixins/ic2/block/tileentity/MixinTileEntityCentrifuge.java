package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import ic2.core.block.machine.tileentity.TileEntityCentrifuge;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityCentrifuge.class)
public class MixinTileEntityCentrifuge
{
	private static final int newOperationLength = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generic/centrifugeOperationLength");

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 500))
	private static int injectOperationLength(final int _500)
	{
		return newOperationLength;
	}
}
