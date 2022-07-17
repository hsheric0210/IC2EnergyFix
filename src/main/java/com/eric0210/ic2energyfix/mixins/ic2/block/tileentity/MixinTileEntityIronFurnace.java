package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

import ic2.core.block.machine.tileentity.TileEntityIronFurnace;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityIronFurnace.class)
public abstract class MixinTileEntityIronFurnace
{
	private final int operationLength = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generic/ironFurnaceOperationLength");

	@ModifyConstant(method = "updateEntityServer", constant = @Constant(intValue = 160), slice = @Slice(from = @At(value = "INVOKE", target = "Lic2/core/block/machine/tileentity/TileEntityIronFurnace;canOperate()Z"), to = @At(value = "INVOKE", target = "Lic2/core/block/machine/tileentity/TileEntityIronFurnace;operate()V")), remap = false)
	private int injectOperationLength(final int _160)
	{
		return operationLength;
	}

	@ModifyConstant(method = "getProgress", constant = @Constant(doubleValue = 160.0), remap = false)
	private double injectProgressOperationLength(final double _160)
	{
		return operationLength;
	}

	@ModifyConstant(method = "getGuiValue", constant = @Constant(doubleValue = 160.0), remap = false)
	private double injectGuiOperationLength(final double _160)
	{
		return operationLength;
	}
}
