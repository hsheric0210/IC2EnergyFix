package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.utils.ReflectionHelper;

import ic2.core.block.machine.tileentity.TileEntityPump;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ic2.core.util.ConfigUtil;

@Mixin(TileEntityPump.class)
public class MixinTileEntityPump
{
	private final int newOperationLength = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generic/pumpOperationLength");

	@Inject(method = "<init>", at = @At("RETURN"))
	public void injectInit(@SuppressWarnings("unused") final CallbackInfo callback)
	{
		ReflectionHelper.tamperFinalField(getClass(), "defaultOperationLength", this, newOperationLength);
		ReflectionHelper.tamperFinalField(getClass(), "operationLength", this, newOperationLength);
	}
}
