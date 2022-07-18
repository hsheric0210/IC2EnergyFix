package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import ic2.core.block.machine.tileentity.TileEntityInduction;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityInduction.class)
public class MixinTileEntityInduction
{
	private final int newOperationLength = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/induction/operationLength");

	@ModifyConstant(method = "gaugeProgressScaled", constant = @Constant(intValue = 4000), remap = false)
	public int injectGaugeProgressScaled(final int _4000)
	{
		return newOperationLength;
	}

	@ModifyConstant(method = "updateEntityServer", constant = @Constant(intValue = 4000), slice = @Slice(from = @At(value = "INVOKE", target = "Lic2/core/block/machine/tileentity/TileEntityInduction;getActive()Z", ordinal = 0), to = @At(value = "INVOKE", target = "Lic2/core/block/machine/tileentity/TileEntityInduction;canOperate()Z", ordinal = 0)), remap = false)
	public int injectNewOperationLength(final int _4000)
	{
		return newOperationLength;
	}
}
