package com.eric0210.ic2energyfix.mixins.ic2.item;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import ic2.core.init.InternalName;
import ic2.core.item.reactor.ItemReactorHeatSwitch;
import ic2.core.util.ConfigUtil;

@Mixin(ItemReactorHeatSwitch.class)
public class MixinItemReactorHeatSwitch
{
	private static final int switchReactorHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatSwitch/heatStorage");
	private static final int switchReactorSwitchSide = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatSwitch/switchSide");
	private static final int switchReactorSwitchReactor = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatSwitch/switchReactor");

	private static final int switchReactorCoreHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatSwitchCore/heatStorage");
	private static final int switchReactorCoreSwitchSide = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatSwitchCore/switchSide");
	private static final int switchReactorCoreSwitchReactor = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatSwitchCore/switchReactor");

	private static final int switchReactorSpreadHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatSwitchSpread/heatStorage");
	private static final int switchReactorSpreadSwitchSide = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatSwitchSpread/switchSide");
	private static final int switchReactorSpreadSwitchReactor = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatSwitchSpread/switchReactor");

	private static final int switchReactorDiamondHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatSwitchDiamond/heatStorage");
	private static final int switchReactorDiamondSwitchSide = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatSwitchDiamond/switchSide");
	private static final int switchReactorDiamondSwitchReactor = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/heatSwitchDiamond/switchReactor");

	@ModifyArgs(method = "<init>", at = @At("HEAD"), remap = false)
	private static void injectArguments(final Args args)
	{
		final String internalName = String.valueOf(args.get(0));

		if (internalName.equalsIgnoreCase(InternalName.reactorHeatSwitch.name()))
			args.setAll(internalName, switchReactorHeatStorage, switchReactorSwitchSide, switchReactorSwitchReactor);
		else if (internalName.equalsIgnoreCase(InternalName.reactorHeatSwitchCore.name()))
			args.setAll(internalName, switchReactorCoreHeatStorage, switchReactorCoreSwitchSide, switchReactorCoreSwitchReactor);
		else if (internalName.equalsIgnoreCase(InternalName.reactorHeatSwitchSpread.name()))
			args.setAll(internalName, switchReactorSpreadHeatStorage, switchReactorSpreadSwitchSide, switchReactorSpreadSwitchReactor);
		else if (internalName.equalsIgnoreCase(InternalName.reactorHeatSwitchDiamond.name()))
			args.setAll(internalName, switchReactorDiamondHeatStorage, switchReactorDiamondSwitchSide, switchReactorDiamondSwitchReactor);
	}
}
