package com.eric0210.ic2energyfix.mixins.ic2.item;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import ic2.core.item.reactor.ItemReactorHeatSwitch;
import ic2.core.ref.ItemName;
import ic2.core.util.ConfigUtil;

@Mixin(ItemReactorHeatSwitch.class)
public class MixinItemReactorHeatSwitch
{
	private static final int heatExchangerHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatExchanger/heatStorage");
	private static final int heatExchangerSwitchSide = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatExchanger/switchSide");
	private static final int heatExchangerSwitchReactor = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatExchanger/switchReactor");

	private static final int reactorHeatExchangerHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/reactorHeatExchanger/heatStorage");
	private static final int reactorHeatExchangerSwitchSide = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/reactorHeatExchanger/switchSide");
	private static final int reactorHeatExchangerSwitchReactor = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/reactorHeatExchanger/switchReactor");

	private static final int componentHeatExchangerHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/componentHeatExchanger/heatStorage");
	private static final int componentHeatExchangerSwitchSide = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/componentHeatExchanger/switchSide");
	private static final int componentHeatExchangerSwitchReactor = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/componentHeatExchanger/switchReactor");

	private static final int advancedHeatExchangerHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/advancedHeatExchanger/heatStorage");
	private static final int advancedHeatExchangerSwitchSide = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/advancedHeatExchanger/switchSide");
	private static final int advancedHeatExchangerSwitchReactor = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/advancedHeatExchanger/switchReactor");

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
			case heat_exchanger:
				return heatExchangerHeatStorage;

			case reactor_heat_exchanger:
				return reactorHeatExchangerHeatStorage;

			case component_heat_exchanger:
				return componentHeatExchangerHeatStorage;

			case advanced_heat_exchanger:
				return advancedHeatExchangerHeatStorage;

			default:
				return heatStorage;
		}
	}

	@ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 1, argsOnly = true, remap = false)
	private static int injectSwitchSide(final int switchSide)
	{
		switch (Name)
		{
			case heat_exchanger:
				return heatExchangerSwitchSide;

			case reactor_heat_exchanger:
				return reactorHeatExchangerSwitchSide;

			case component_heat_exchanger:
				return componentHeatExchangerSwitchSide;

			case advanced_heat_exchanger:
				return advancedHeatExchangerSwitchSide;

			default:
				return switchSide;
		}
	}

	@ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 2, argsOnly = true, remap = false)
	private static int injectSwitchReactor(final int switchReactor)
	{
		switch (Name)
		{
			case heat_exchanger:
				return heatExchangerSwitchReactor;

			case reactor_heat_exchanger:
				return reactorHeatExchangerSwitchReactor;

			case component_heat_exchanger:
				return componentHeatExchangerSwitchReactor;

			case advanced_heat_exchanger:
				return advancedHeatExchangerSwitchReactor;

			default:
				return switchReactor;
		}
	}
}
