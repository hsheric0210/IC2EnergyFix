package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import ic2.core.block.machine.tileentity.TileEntityMacerator;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityMacerator.class)
public class MixinTileEntityMacerator
{
	private static final int newOperationLength = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generic/maceratorOperationLength");

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 300))
	private static int injectOperationLength(final int _300)
	{
		return newOperationLength;
	}
}
