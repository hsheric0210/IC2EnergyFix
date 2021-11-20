package com.eric0210.ic2energyfix.mixins.ic2.block;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ic2.core.block.BlockFoam;
import ic2.core.block.BlockReinforcedFoam;
import ic2.core.util.ConfigUtil;

@Mixin(BlockReinforcedFoam.class)
public abstract class MixinBlockReinforcedFoam
{
	private final int chance = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/block/reinforcedFoam/tickChance");
	private final int tickRate = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/block/reinforcedFoam/tickRate");

	@ModifyConstant(method = "updateTick", constant = @Constant(intValue = 1000), remap = false)
	private int injectRNG(final int _1000)
	{
		return chance;
	}

	@Inject(method = "tickRate", at = @At("HEAD"), cancellable = true, remap = false)
	private void injectTickRate(final CallbackInfoReturnable<? super Integer> cir)
	{
		cir.setReturnValue(tickRate);
	}
}
