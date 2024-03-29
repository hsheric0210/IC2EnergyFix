package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.utils.ReflectionHelper;

import net.minecraftforge.fluids.FluidTank;

import ic2.api.energy.EnergyNet;
import ic2.api.item.ElectricItem;
import ic2.core.block.generator.tileentity.TileEntitySemifluidGenerator;
import ic2.core.block.invslot.InvSlotCharge;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntitySemifluidGenerator.class)
public abstract class MixinTileEntitySemifluidGenerator extends MixinTileEntityLiquidTankInventory
{
	private final int tankSize = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generator/semiFluid/tankSize");
	private final double outputMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/generator/semiFluid/outputMultiplier");
	private final double outputFixed = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/generator/semiFluid/outputFixed");
	private final int maxEUStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generator/semiFluid/maxStorage");

	@Shadow(remap = false)
	@Final
	public InvSlotCharge chargeSlot;

	@Shadow(remap = false)
	public double storage;

	@Shadow(remap = false)
	protected double production;

	@Shadow(remap = false)
	public abstract int getSourceTier();

	@Shadow(remap = false)
	protected abstract boolean gainFuel();

	@Shadow(remap = false)
	protected abstract boolean gainEnergy();

	@Inject(method = "<init>", at = @At("RETURN"))
	public void injectInit(@SuppressWarnings("unused") final CallbackInfo callback)
	{
		ReflectionHelper.tamperFinalField(getClass().getSuperclass(), "fluidTank", this, new FluidTank(tankSize));
	}

	@Inject(method = "getOfferedEnergy", at = @At("HEAD"), remap = false, cancellable = true)
	public void injectGetOfferedEnergy(final CallbackInfoReturnable<? super Double> callback)
	{
		callback.setReturnValue(Math.min(storage, outputFixed > 0 ? outputFixed : EnergyNet.instance.getPowerFromTier(getSourceTier()) * outputMultiplier));
	}

	@Inject(method = "updateEntityServer", at = @At("HEAD"), cancellable = true, remap = false)
	protected void injectUpdateEntityServer(final CallbackInfo callback)
	{
		boolean needsInvUpdate = needsFluid() && gainFuel();

		final boolean newActive = gainEnergy();
		if (storage > maxEUStorage)
			storage = maxEUStorage;

		if (storage >= 1.0D && chargeSlot.get() != null)
		{
			final double used = ElectricItem.manager.charge(chargeSlot.get(), storage, 1, false, false);
			storage -= used;
			if (used > 0.0D)
				needsInvUpdate = true;
		}

		if (needsInvUpdate)
			func_70296_d();

		if (getActive() != newActive)
			setActive(newActive);

		callback.cancel();
	}

	@Inject(method = "isConverting", at = @At("HEAD"), cancellable = true, remap = false)
	public void injectIsConverting(final CallbackInfoReturnable<? super Boolean> callback)
	{
		callback.setReturnValue(getTankAmount() > 0 && storage + production <= maxEUStorage);
	}

	@Inject(method = "gaugeStorageScaled", at = @At("HEAD"), cancellable = true, remap = false)
	public void injectGaugeStorageScaled(final int i, final CallbackInfoReturnable<? super Integer> callback)
	{
		callback.setReturnValue((int) (storage * i / maxEUStorage));
	}
}
