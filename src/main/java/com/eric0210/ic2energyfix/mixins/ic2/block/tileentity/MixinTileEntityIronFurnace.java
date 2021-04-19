package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.mixins.MixinTileEntityInventory;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ic2.core.block.invslot.InvSlotConsumableFuel;
import ic2.core.block.machine.tileentity.TileEntityIronFurnace;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityIronFurnace.class)
public abstract class MixinTileEntityIronFurnace extends MixinTileEntityInventory
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
	public void gaugeProgressScaled(final int i, final CallbackInfoReturnable<? super Integer> callback)
	{
		callback.setReturnValue(progress * i / newOperationLength);
	}

	@Inject(method = "updateEntityServer", at = @At("HEAD"), cancellable = true, remap = false)
	protected void updateEntityServer(final CallbackInfo callback)
	{
		// noinspection UnnecessarySuperQualifier
		super.updateEntityServer();

		boolean needsInvUpdate = false;
		if (fuel <= 0 && canOperate())
		{
			fuel = maxFuel = fuelSlot.consumeFuel();
			if (fuel > 0)
				needsInvUpdate = true;
		}

		final boolean burning = isBurning();

		if (burning && canOperate())
		{
			++progress;
			if (progress >= newOperationLength)
			{
				progress = 0;
				operate();
				needsInvUpdate = true;
			}
		}
		else
			progress = 0;

		if (fuel > 0)
			--fuel;

		if (getActive() != burning)
		{
			setActive(burning);
			needsInvUpdate = true;
		}

		if (needsInvUpdate)
			func_70296_d();

		callback.cancel();
	}
}
