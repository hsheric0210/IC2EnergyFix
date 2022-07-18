package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.utils.ReflectionHelper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ic2.core.block.machine.tileentity.TileEntityAdvMiner;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityAdvMiner.class)
public class MixinTileEntityAdvMiner
{
	private final int newWorkTick = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/advMiner/workTick");
	private final int newScanEnergy = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/advMiner/scanEnergy");
	private final int newMineEnergy = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/advMiner/mineEnergy");

	@Inject(method = "<init>(I)V", at = @At("RETURN"), remap = false)
	public void injectInit(@SuppressWarnings("unused") final CallbackInfo callback)
	{
		ReflectionHelper.tamperFinalField(getClass(), "workTick", this, newWorkTick);
		ReflectionHelper.tamperFinalField(getClass(), "scanEnergy", this, newScanEnergy);
		ReflectionHelper.tamperFinalField(getClass(), "mineEnergy", this, newMineEnergy);
	}
}
