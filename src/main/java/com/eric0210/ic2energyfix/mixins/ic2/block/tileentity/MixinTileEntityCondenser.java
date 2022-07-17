package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;

import ic2.core.block.machine.tileentity.TileEntityCondenser;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityCondenser.class)
public class MixinTileEntityCondenser
{
	private final int passiveCooling = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/condenser/passiveCooling");
	private final int activeCoolingPerVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/condenser/activeCoolingPerVent");

	@ModifyConstant(method = "work", constant = @Constant(intValue = 100, ordinal = 0), slice = @Slice(from = @At(value = "INVOKE", target = "Lic2/core/block/machine/tileentity/TileEntityCondenser;getVents()B", remap = false), to = @At(value = "INVOKE", target = "Lic2/core/block/comp/Energy;useEnergy(D)Z", remap = false)), remap = false)
	private int injectPassiveCooling(final int _100)
	{
		return passiveCooling;
	}

	@ModifyConstant(method = "work", constant = @Constant(intValue = 100, ordinal = 1), slice = @Slice(from = @At(value = "INVOKE", target = "Lic2/core/block/machine/tileentity/TileEntityCondenser;getVents()B", remap = false), to = @At(value = "INVOKE", target = "Lic2/core/block/comp/Energy;useEnergy(D)Z", remap = false)), remap = false)
	private int injectActiveCooling(final int _100)
	{
		return activeCoolingPerVent;
	}
}
