package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ic2.core.block.machine.tileentity.TileEntityCropHarvester;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityCropHarvester.class)
public class MixinTileEntityCropHarvester
{
	private final int tickRate = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/cropHarvester/tickRate");
	private final int horizontalRange = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/cropHarvester/horizontalRange");
	private final int verticalRange = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/cropHarvester/verticalRange");

	@Shadow(remap = false)
	public int scanX;

	@Shadow(remap = false)
	public int scanY;

	@Shadow(remap = false)
	public int scanZ;

	@Inject(method = "<init>", at = @At("RETURN"), remap = false)
	public void injectInit(final CallbackInfo ci)
	{
		scanX = scanZ = -horizontalRange;
		scanY = -verticalRange;
	}

	@ModifyConstant(method = "updateEntityServer", constant = @Constant(longValue = 10L), remap = false)
	private long injectTickRate(final long _10L)
	{
		return tickRate;
	}

	@ModifyConstant(method = "scan", constant = @Constant(intValue = -4), remap = false)
	public int injectScanHStart(final int _4)
	{
		return -horizontalRange;
	}

	@ModifyConstant(method = "scan", constant = @Constant(intValue = 4), remap = false)
	public int injectScanHEnd(final int _4)
	{
		return horizontalRange;
	}

	@ModifyConstant(method = "scan", constant = @Constant(intValue = -1, ordinal = 0), remap = false)
	public int injectScanVStart(final int _1)
	{
		return -verticalRange;
	}

	@ModifyConstant(method = "scan", constant = @Constant(intValue = 1, ordinal = 0), remap = false)
	public int injectScanVEnd(final int _1)
	{
		return verticalRange;
	}
}
