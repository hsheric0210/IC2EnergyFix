package com.eric0210.ic2energyfix.mixins.ic2.item;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import ic2.core.item.reactor.ItemReactorVent;
import ic2.core.ref.ItemName;
import ic2.core.util.ConfigUtil;

@Mixin(ItemReactorVent.class)
public class MixinItemReactorVent
{
	private static final int heatVentHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatVent/heatStorage");
	private static final int heatVentSelfVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatVent/selfVent");
	private static final int heatVentReactorVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatVent/reactorVent");

	private static final int reactorHeatVentHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/reactorHeatVent/heatStorage");
	private static final int reactorHeatVentSelfVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/reactorHeatVent/selfVent");
	private static final int reactorHeatVentReactorVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/reactorHeatVent/reactorVent");

	private static final int overclockedHeatVentHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/overclockedHeatVent/heatStorage");
	private static final int overclockedHeatVentSelfVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/overclockedHeatVent/selfVent");
	private static final int overclockedHeatVentReactorVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/overclockedHeatVent/reactorVent");

	private static final int advancedHeatVentHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/advancedHeatVent/heatStorage");
	private static final int advancedHeatVentSelfVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/advancedHeatVent/selfVent");
	private static final int advancedHeatVentReactorVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/advancedHeatVent/reactorVent");


	private static ItemName Name;

	@ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 0, argsOnly = true, remap = false)
	private static ItemName injectHeatStorage(final ItemName internalName)
	{
		Name = internalName;
		return internalName;
	}

	@ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 0, argsOnly = true, remap = false)
	private static int injectHeatStorage(final int heatStorage)
	{
		switch (Name)
		{
			case heat_vent:
				return heatVentHeatStorage;

			case reactor_heat_vent:
				return reactorHeatVentHeatStorage;

			case overclocked_heat_vent:
				return overclockedHeatVentHeatStorage;

			case advanced_heat_vent:
				return advancedHeatVentHeatStorage;

			default:
				return heatStorage;
		}
	}

	@ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 1, argsOnly = true, remap = false)
	private static int injectSelfVent(final int selfVent)
	{
		switch (Name)
		{
			case heat_vent:
				return heatVentSelfVent;

			case reactor_heat_vent:
				return reactorHeatVentSelfVent;

			case overclocked_heat_vent:
				return overclockedHeatVentSelfVent;

			case advanced_heat_vent:
				return advancedHeatVentSelfVent;

			default:
				return selfVent;
		}
	}

	@ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 2, argsOnly = true, remap = false)
	private static int injectReactorVent(final int heatVent)
	{
		switch (Name)
		{
			case heat_vent:
				return heatVentReactorVent;

			case reactor_heat_vent:
				return reactorHeatVentReactorVent;

			case overclocked_heat_vent:
				return overclockedHeatVentReactorVent;

			case advanced_heat_vent:
				return advancedHeatVentReactorVent;

			default:
				return heatVent;
		}
	}
}
