package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import ic2.core.util.ConfigUtil;
import org.spongepowered.asm.mixin.Mixin;

import ic2.core.block.machine.tileentity.TileEntityRecycler;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileEntityRecycler.class)
public class MixinTileEntityRecycler
{
	private static final int recycleChance = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/recycler/recycleChance");

	@Inject(method = "recycleChance", at = @At("HEAD"), cancellable = true, remap = false)
	public void getTickRate(final CallbackInfoReturnable<? super Integer> callback)
	{
		callback.setReturnValue(recycleChance);
	}
}
