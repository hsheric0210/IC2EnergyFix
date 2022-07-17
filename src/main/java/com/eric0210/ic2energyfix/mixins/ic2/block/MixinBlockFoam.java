package com.eric0210.ic2energyfix.mixins.ic2.block;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ic2.core.block.BlockFoam;
import ic2.core.util.ConfigUtil;

@Mixin(BlockFoam.class)
public class MixinBlockFoam
{
	private final float hardenChanceMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/block/foam/hardenChanceMultiplier");
	private final int baseLightReverseMultiplier = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/block/foam/baseLightReverseMultiplier");

	@ModifyConstant(method = "randomTick", constant = @Constant(floatValue = 4096.0F), remap = false)
	private float injectHardenChanceMultiplier(final float _4096)
	{
		return hardenChanceMultiplier;
	}

	@ModifyConstant(method = "randomTick", constant = @Constant(intValue = 16), remap = false)
	private int injectBaseLightReverseMultiplier(final int _16)
	{
		return baseLightReverseMultiplier;
	}
}
