package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ic2.core.block.machine.tileentity.TileEntityRecycler;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityRecycler.class)
public class MixinTileEntityRecycler
{
	private static final int recycleChance = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/recycler/recycleChance");
	private static final int operationLength = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/recycler/operationLength");

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 45))
	private static int injectOperationLength(@SuppressWarnings("unused") final int _45)
	{
		return operationLength;
	}

	@Inject(method = "recycleChance", at = @At("HEAD"), cancellable = true, remap = false)
	private static void injectRecycleChance(final CallbackInfoReturnable<? super Integer> callback)
	{
		callback.setReturnValue(recycleChance);
	}
}
