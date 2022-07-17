package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ic2.core.block.invslot.InvSlotConsumableFuel;
import ic2.core.block.machine.tileentity.TileEntityIronFurnace;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityIronFurnace.class)
public abstract class MixinTileEntityIronFurnace
{
	private final int newOperationLength = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generic/ironFurnaceOperationLength");

	@Shadow(remap = false)
	public int fuel;

	@Shadow(remap = false)
	public int maxFuel;

	@Shadow(remap = false)
	public short progress;

	@Shadow(remap = false)
	@Final
	public InvSlotConsumableFuel fuelSlot;

	@Shadow(remap = false)
	public abstract boolean canOperate();

	@Shadow(remap = false)
	public abstract boolean isBurning();

	@Shadow(remap = false)
	public abstract void operate();

	@Inject(method = "gaugeProgressScaled", at = @At("HEAD"), cancellable = true, remap = false)
	public void injectGaugeProgressScaled(final int i, final CallbackInfoReturnable<? super Integer> callback)
	{
		callback.setReturnValue(progress * i / newOperationLength);
	}

	@ModifyConstant(method = "updateEntityServer", constant = @Constant(intValue = 160), slice = @Slice(from = @At(value = "INVOKE", target = "Lic2/core/block/machine/tileentity/TileEntityIronFurnace;canOperate()Z"), to = @At(value = "INVOKE", target = "Lic2/core/block/machine/tileentity/TileEntityIronFurnace;operate()V")), remap = false)
	private int injectOperationLength(final int _160)
	{
		return newOperationLength;
	}
}
