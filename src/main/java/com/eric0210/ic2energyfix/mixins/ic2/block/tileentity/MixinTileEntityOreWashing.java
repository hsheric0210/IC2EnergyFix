package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.utils.ReflectionHelper;

import ic2.core.block.machine.tileentity.TileEntityOreWashing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ic2.core.util.ConfigUtil;

@Mixin(TileEntityOreWashing.class)
public class MixinTileEntityOreWashing
{
	private final int newOperationLength = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generic/oreWasherOperationLength");

	@Inject(method = "<init>", at = @At("RETURN"))
	public void init(@SuppressWarnings("unused") final CallbackInfo callback)
	{
		ReflectionHelper.setOperationLength(this, newOperationLength);
	}
}
