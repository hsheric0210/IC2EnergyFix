package com.eric0210.ic2energyfix.mixins.ic2.item;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import ic2.core.init.InternalName;
import ic2.core.item.reactor.ItemReactorVent;
import ic2.core.item.reactor.ItemReactorVentSpread;
import ic2.core.util.ConfigUtil;

@Mixin(ItemReactorVentSpread.class)
public class MixinItemReactorVentSpread
{
	private static final int sideVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/ventSpread/sideVent");

	@ModifyArg(method = "<init>", at = @At("HEAD"), remap = false)
	private static int injectSideVent(final int _sideVent)
	{
		return sideVent;
	}
}
