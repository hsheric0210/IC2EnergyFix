package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.mixins.ic2.block.MixinTileEntityInventory;
import com.eric0210.ic2energyfix.utils.ReflectionHelper;

import net.minecraftforge.fluids.FluidTank;

import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityNuclearReactorElectric.class)
public abstract class MixinTileEntityNuclearReactor extends MixinTileEntityInventory
{
	private final int inputTankSize = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/nuclearreactor/inputTankSize");
	private final int outputTankSize = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/nuclearreactor/outputTankSize");
	private final int tickrate = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/nuclearreactor/tickrate");

	@Inject(method = "<init>", at = @At("RETURN"))
	public void injectInit(@SuppressWarnings("unused") final CallbackInfo callback)
	{
		ReflectionHelper.tamperFinalField(getClass(), "inputTank", this, new FluidTank(inputTankSize));
		ReflectionHelper.tamperFinalField(getClass(), "outputTank", this, new FluidTank(outputTankSize));
	}

	@Inject(method = "getTickRate", at = @At("HEAD"), cancellable = true, remap = false)
	public void injectGetTickRate(final CallbackInfoReturnable<? super Integer> callback)
	{
		callback.setReturnValue(tickrate);
	}
}
