package com.eric0210.ic2energyfix.mixins.ic2.item;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

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

	@ModifyArgs(method = "<init>", at = @At("HEAD"), remap = false)
	private static void injectArguments(final Args args)
	{
		final String internalName = String.valueOf(args.get(0));

		if (internalName.equalsIgnoreCase(ItemName.heat_exchanger.name()))
			args.setAll(internalName, heatExchangerHeatStorage, heatExchangerSwitchSide, heatExchangerSwitchReactor);
		else if (internalName.equalsIgnoreCase(ItemName.reactor_heat_exchanger.name()))
			args.setAll(internalName, reactorHeatExchangerHeatStorage, reactorHeatExchangerSwitchSide, reactorHeatExchangerSwitchReactor);
		else if (internalName.equalsIgnoreCase(ItemName.component_heat_exchanger.name()))
			args.setAll(internalName, componentHeatExchangerHeatStorage, componentHeatExchangerSwitchSide, componentHeatExchangerSwitchReactor);
		else if (internalName.equalsIgnoreCase(ItemName.advanced_heat_exchanger.name()))
			args.setAll(internalName, advancedHeatExchangerHeatStorage, advancedHeatExchangerSwitchSide, advancedHeatExchangerSwitchReactor);
	}
}
