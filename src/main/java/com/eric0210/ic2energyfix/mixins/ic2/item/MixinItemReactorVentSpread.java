package com.eric0210.ic2energyfix.mixins.ic2.item;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import ic2.core.item.reactor.ItemReactorVentSpread;
import ic2.core.util.ConfigUtil;

@Mixin(ItemReactorVentSpread.class)
public class MixinItemReactorVentSpread
{
	private static final int newSideVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/componentHeatVent/sideVent");

	@ModifyVariable(method = "<init>", at = @At("HEAD"), remap = false, argsOnly = true)
	private static int injectSideVent(final int _sideVent)
	{
		return newSideVent;
	}
}
