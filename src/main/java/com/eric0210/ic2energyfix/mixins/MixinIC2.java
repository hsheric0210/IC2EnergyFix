package com.eric0210.ic2energyfix.mixins;

import com.eric0210.ic2energyfix.IC2EnergyFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import ic2.core.IC2;

@Mixin(IC2.class)
public abstract class MixinIC2
{
	@Inject(method = "load", at = @At(value = "INVOKE", target = "Lic2/core/init/MainConfig;load()V", shift = Shift.AFTER), remap = false)
	public void load(final CallbackInfo callback)
	{
		IC2EnergyFixConfig.load();
		IC2EnergyFix.info("Configuration loaded");
	}
}
