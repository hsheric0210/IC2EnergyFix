package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityBaseGenerator.class)
public class MixinTileEntityBaseGenerator
{
	private final double baseOutputMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/generator/outputMultiplier");
	private final double baseOutputStatic = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/generator/outputStatic");

	@Shadow(remap = false)
	public double storage;

	@Shadow(remap = false)
	public double power;

	@Inject(method = "getOfferedEnergy", at = @At("HEAD"), remap = false, cancellable = true)
	public void getOfferedEnergy(final CallbackInfoReturnable<Double> callback)
	{
		callback.setReturnValue(Math.min(storage, baseOutputStatic == -1 ? power * baseOutputMultiplier : baseOutputStatic));
	}
}
