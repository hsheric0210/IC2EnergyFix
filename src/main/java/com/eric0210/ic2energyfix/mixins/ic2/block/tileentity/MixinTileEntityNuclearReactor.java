package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityNuclearReactorElectric.class)
public abstract class MixinTileEntityNuclearReactor
{
	private final int inputTankCapacity = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/nuclearReactor/inputTankCapacity");
	private final int outputTankCapacity = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/nuclearReactor/outputTankCapacity");
	private final int newTickRate = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/nuclearReactor/tickRate");

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 10000, ordinal = 0))
	public int injectInputTankCapacity(final int _10000)
	{
		return inputTankCapacity;
	}

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 10000, ordinal = 1))
	public int injectOutputTankCapacity(final int _10000)
	{
		return outputTankCapacity;
	}

	@Inject(method = "getTickRate", at = @At("HEAD"), cancellable = true, remap = false)
	public void injectGetTickRate(final CallbackInfoReturnable<? super Integer> callback)
	{
		callback.setReturnValue(newTickRate);
	}
}
