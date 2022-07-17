package com.eric0210.ic2energyfix.mixins.ic2;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ic2.core.IC2;
import ic2.core.util.Log;
import ic2.core.util.LogCategory;

@Mixin(IC2.class)
public class MixinIC2
{
	@Shadow(remap = false)
	public static Log log;

	@Inject(method = "load", at = @At(value = "INVOKE", target = "Lic2/core/init/MainConfig;load()V", shift = Shift.AFTER), remap = false)
	public void injectLoadConfig(final CallbackInfo callback)
	{
		IC2EnergyFixConfig.load();
		log.info(LogCategory.General, "[IC2EnergyFix] Configuration Loaded");
	}
}
