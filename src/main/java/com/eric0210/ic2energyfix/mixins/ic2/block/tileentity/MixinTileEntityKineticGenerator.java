package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.mixins.MixinTileEntityInventory;
import com.eric0210.ic2energyfix.utils.ReflectionHelper;

import ic2.api.energy.EnergyNet;
import ic2.core.block.generator.tileentity.TileEntityKineticGenerator;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityKineticGenerator.class)
public abstract class MixinTileEntityKineticGenerator extends MixinTileEntityInventory
{
	private final double outputMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/generator/kinetic/outputMultiplier");
	private final double outputFixed = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/generator/kinetic/outputFixed");
	private final int maxStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generator/kinetic/maxStorage");
	private final int guiTickrate = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generator/kinetic/guiTickrate");

	@Shadow(remap = false)
	public int updateTicker;

	@Shadow(remap = false)
	private double guiproduction;

	@Shadow(remap = false)
	private double production;

	@Shadow(remap = false)
	private int receivedkinetic;

	@Shadow(remap = false)
	public double EUstorage;

	@Shadow(remap = false)
	public abstract int getSourceTier();

	@Inject(method = "<init>", at = @At("RETURN"))
	public void init(@SuppressWarnings("unused") final CallbackInfo callback)
	{
		ReflectionHelper.tamperFinalField(getClass(), "maxEUStorage", this, maxStorage);
	}

	@Inject(method = "getOfferedEnergy", at = @At("HEAD"), cancellable = true, remap = false)
	public void getOfferedEnergy(final CallbackInfoReturnable<? super Double> callback)
	{
		callback.setReturnValue(Math.min(EUstorage, outputFixed == -1 ? EnergyNet.instance.getPowerFromTier(getSourceTier()) * outputMultiplier : outputFixed));
	}

	@Inject(method = "getTickRate", at = @At("HEAD"), cancellable = true, remap = false)
	public void getTickRate(final CallbackInfoReturnable<? super Integer> callback)
	{
		callback.setReturnValue(guiTickrate);
	}
}
