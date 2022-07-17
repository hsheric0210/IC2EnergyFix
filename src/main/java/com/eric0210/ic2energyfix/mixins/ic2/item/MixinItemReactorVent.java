package com.eric0210.ic2energyfix.mixins.ic2.item;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import ic2.core.item.reactor.ItemReactorVent;
import ic2.core.ref.ItemName;
import ic2.core.util.ConfigUtil;

@Mixin(ItemReactorVent.class)
public class MixinItemReactorVent
{
	private static final int heatVentHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatVent/heatStorage");
	private static final int heatVentSelfVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatVent/selfVent");
	private static final int heatVentheatVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatVent/heatVent");

	private static final int reactorHeatVentHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/reactorHeatVent/heatStorage");
	private static final int reactorHeatVentSelfVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/reactorHeatVent/selfVent");
	private static final int reactorHeatVentheatVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/reactorHeatVent/heatVent");

	private static final int overclockedHeatVentHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/overclockedHeatVent/heatStorage");
	private static final int overclockedHeatVentSelfVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/overclockedHeatVent/selfVent");
	private static final int overclockedHeatVentheatVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/overclockedHeatVent/heatVent");

	private static final int advancedHeatVentHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/advancedHeatVent/heatStorage");
	private static final int advancedHeatVentSelfVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/advancedHeatVent/selfVent");
	private static final int advancedHeatVentheatVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/advancedHeatVent/heatVent");

	@ModifyArgs(method = "<init>", at = @At("HEAD"), remap = false)
	private static void injectArguments(final Args args)
	{
		final String internalName = String.valueOf(args.get(0));

		if (internalName.equalsIgnoreCase(ItemName.heat_vent.name()))
			args.setAll(internalName, heatVentHeatStorage, heatVentSelfVent, heatVentheatVent);
		else if (internalName.equalsIgnoreCase(ItemName.reactor_heat_vent.name()))
			args.setAll(internalName, reactorHeatVentHeatStorage, reactorHeatVentSelfVent, reactorHeatVentheatVent);
		else if (internalName.equalsIgnoreCase(ItemName.overclocked_heat_vent.name()))
			args.setAll(internalName, overclockedHeatVentHeatStorage, overclockedHeatVentSelfVent, overclockedHeatVentheatVent);
		else if (internalName.equalsIgnoreCase(ItemName.advanced_heat_vent.name()))
			args.setAll(internalName, advancedHeatVentHeatStorage, advancedHeatVentSelfVent, advancedHeatVentheatVent);
	}
}
