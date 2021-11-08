package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ic2.core.block.machine.tileentity.TileEntityCropmatron;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityCropmatron.class)
public class MixinTileEntityCropmatron
{
	private final int scanH = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/cropmatron/scanH");
	private final int scanV = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/cropmatron/scanV");

	@Shadow(remap = false)
	public int scanX;

	@Shadow(remap = false)
	public int scanY;

	@Shadow(remap = false)
	public int scanZ;

	@Inject(method = "<init>", at = @At("RETURN"), remap = false)
	public void injectInit(final CallbackInfo ci)
	{
		scanX = scanZ = -scanH;
		scanY = -scanV;
	}

	@ModifyConstant(method = "scan", constant = @Constant(intValue = -5), remap = false)
	public int injectScanHStart(final int minus5)
	{
		return -scanH;
	}

	@ModifyConstant(method = "scan", constant = @Constant(intValue = 5), remap = false)
	public int injectScanHEnd(final int _5)
	{
		return scanH;
	}

	@ModifyConstant(method = "scan", constant = @Constant(intValue = -1), remap = false)
	public int injectScanVStart(final int minus1)
	{
		return -scanV;
	}

	@ModifyConstant(method = "scan", constant = @Constant(intValue = 1), remap = false)
	public int injectScanVEnd(final int _1)
	{
		return scanV;
	}
}
