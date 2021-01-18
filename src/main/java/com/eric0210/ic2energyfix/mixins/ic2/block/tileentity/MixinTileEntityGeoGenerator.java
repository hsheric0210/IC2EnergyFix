package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.mixins.MixinTileEntityLiquidTankInventory;
import com.eric0210.ic2energyfix.utils.ReflectionHelper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

import ic2.api.energy.EnergyNet;
import ic2.api.item.ElectricItem;
import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
import ic2.core.block.invslot.InvSlotCharge;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityGeoGenerator.class)
public abstract class MixinTileEntityGeoGenerator extends MixinTileEntityLiquidTankInventory
{
	private final int tankSize = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generator/geothermal/tankSize");
	private final double outputMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/generator/geothermal/outputMultiplier");
	private final double outputStatic = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/generator/geothermal/outputStatic");
	private final int maxEUStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generator/geothermal/maxStorage");

	@Shadow(remap = false)
	@Final
	public InvSlotCharge chargeSlot;

	@Shadow(remap = false)
	@Final
	public InvSlotConsumableLiquid fluidSlot;

	@Shadow(remap = false)
	@Final
	public InvSlotOutput outputSlot;

	@Shadow(remap = false)
	public double storage;

	@Shadow(remap = false)
	@Final
	public int production;

	@Shadow(remap = false)
	public abstract int getSourceTier();

	@Shadow(remap = false)
	public abstract boolean gainEnergy();

	@Inject(method = "<init>", at = @At("RETURN"))
	public void init(@SuppressWarnings("unused") final CallbackInfo callback)
	{
		ReflectionHelper.tamperFinalField(getClass().getSuperclass(), "fluidTank", this, new FluidTank(tankSize));
	}

	@Inject(method = "getOfferedEnergy", at = @At("HEAD"), cancellable = true, remap = false)
	public void getOfferedEnergy(final CallbackInfoReturnable<Double> callback)
	{
		callback.setReturnValue(Math.min(storage, outputStatic == -1 ? EnergyNet.instance.getPowerFromTier(getSourceTier()) * outputMultiplier : outputStatic));
	}

	@Inject(method = "updateEntityServer", at = @At("HEAD"), cancellable = true, remap = false)
	protected void updateEntityServer(final CallbackInfo callback)
	{

		boolean needsInvUpdate = false;
		if (needsFluid())
		{
			final MutableObject<ItemStack> output = new MutableObject<>();
			if (fluidSlot.transferToTank(fluidTank, output, true) && (output.getValue() == null || outputSlot.canAdd(output.getValue())))
			{
				needsInvUpdate = fluidSlot.transferToTank(fluidTank, output, false);
				if (output.getValue() != null)
					outputSlot.add(output.getValue());
			}
		}

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
	public void isConverting(final CallbackInfoReturnable<Boolean> callback)
	{
		callback.setReturnValue(getTankAmount() > 0 && storage + production <= maxEUStorage);
	}

	@Inject(method = "gaugeStorageScaled", at = @At("HEAD"), cancellable = true, remap = false)
	public void gaugeStorageScaled(final int i, final CallbackInfoReturnable<Integer> callback)
	{
		callback.setReturnValue((int) (storage * i / maxEUStorage));
	}
}