package com.eric0210.ic2energyfix.mixins.ic2.item;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import ic2.core.init.InternalName;
import ic2.core.item.reactor.ItemReactorVent;
import ic2.core.util.ConfigUtil;

@Mixin(ItemReactorVent.class)
public class MixinItemReactorVent
{
	private static final int reactorVentHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/vent/heatStorage");
	private static final int reactorVentSelfVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/vent/selfVent");
	private static final int reactorVentReactorVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/vent/reactorVent");

	private static final int reactorVentCoreHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/ventCore/heatStorage");
	private static final int reactorVentCoreSelfVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/ventCore/selfVent");
	private static final int reactorVentCoreReactorVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/ventCore/reactorVent");

	private static final int reactorVentGoldHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/ventGold/heatStorage");
	private static final int reactorVentGoldSelfVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/ventGold/selfVent");
	private static final int reactorVentGoldReactorVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/ventGold/reactorVent");

	private static final int reactorVentDiamondHeatStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/ventDiamond/heatStorage");
	private static final int reactorVentDiamondSelfVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/ventDiamond/selfVent");
	private static final int reactorVentDiamondReactorVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/reactor/ventDiamond/reactorVent");

	@ModifyArgs(method = "<init>", at = @At("HEAD"), remap = false)
	private static void injectArguments(final Args args)
	{
		final String internalName = String.valueOf(args.get(0));

		if (internalName.equalsIgnoreCase(InternalName.reactorVent.name()))
			args.setAll(internalName, reactorVentHeatStorage, reactorVentSelfVent, reactorVentReactorVent);
		else if (internalName.equalsIgnoreCase(InternalName.reactorVentCore.name()))
			args.setAll(internalName, reactorVentCoreHeatStorage, reactorVentCoreSelfVent, reactorVentCoreReactorVent);
		else if (internalName.equalsIgnoreCase(InternalName.reactorVentGold.name()))
			args.setAll(internalName, reactorVentGoldHeatStorage, reactorVentGoldSelfVent, reactorVentGoldReactorVent);
		else if (internalName.equalsIgnoreCase(InternalName.reactorVentDiamond.name()))
			args.setAll(internalName, reactorVentDiamondHeatStorage, reactorVentDiamondSelfVent, reactorVentDiamondReactorVent);
	}
}
