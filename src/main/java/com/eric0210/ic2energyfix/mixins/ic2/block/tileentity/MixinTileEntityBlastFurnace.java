package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ic2.core.block.machine.tileentity.TileEntityBlastFurnace;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityBlastFurnace.class)
public class MixinTileEntityBlastFurnace
{
	private final int progressNeededPreOffset = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/machine/blastFurnace/progressNeededPreOffset");
	private final float progressNeededMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/machine/blastFurnace/progressNeededMultiplier");
	private final int progressNeededPostOffset = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/machine/blastFurnace/progressNeededPostOffset");

	@Shadow(remap = false)
	protected int progressNeeded;

	@Inject(method = "updateEntityServer", at = @At(value = "FIELD", target = "Lic2/core/block/machine/tileentity/TileEntityBlastFurnace;progressNeeded:I", shift = Shift.AFTER, ordinal = 0))
	public void injectProgressNeededMultiplier(@SuppressWarnings("unused") final CallbackInfo callback)
	{
		progressNeeded += progressNeededPreOffset;
		progressNeeded *= progressNeededMultiplier;
		progressNeeded += progressNeededPostOffset;
	}
}
