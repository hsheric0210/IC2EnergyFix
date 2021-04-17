package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Final;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.utils.ReflectionHelper;

import net.minecraftforge.fluids.FluidTank;

import ic2.core.block.machine.tileentity.TileEntityLiquidHeatExchanger;
import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityLiquidHeatExchanger.class)
public class MixinTileEntityLiquidHeatExchanger
{
	private final int maxHPT = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/liquidheatexchanger/maxHeatExchangePerTick");
	private final int inputTankSize = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/liquidheatexchanger/inputTankSize");
	private final int outputTankSize = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/liquidheatexchanger/outputTankSize");

	@Shadow(remap = false)
	private boolean newActive;

	@Shadow(remap = false)
	@Final
	public FluidTank inputTank;

	@Shadow(remap = false)
	@Final
	public FluidTank outputTank;

	@Shadow(remap = false)
	@Final
	public InvSlotConsumable heatexchangerslots;

	@Shadow(remap = false)
	@Final
	public InvSlotOutput hotoutputSlot;

	@Shadow(remap = false)
	@Final
	public InvSlotOutput cooloutputSlot;

	@Shadow(remap = false)
	@Final
	public InvSlotConsumableLiquid hotfluidinputSlot;

	@Shadow(remap = false)
	@Final
	public InvSlotConsumableLiquid coolfluidinputSlot;

	@Shadow(remap = false)
	@Final
	public InvSlotUpgrade upgradeSlot;

	@Inject(method = "<init>", at = @At("RETURN"))
	public void init(@SuppressWarnings("unused") final CallbackInfo callback)
	{
		ReflectionHelper.tamperFinalField(getClass(), "inputTank", this, new FluidTank(inputTankSize));
		ReflectionHelper.tamperFinalField(getClass(), "outputTank", this, new FluidTank(outputTankSize));
	}

	@Inject(method = "getMaxHeatEmittedPerTick", at = @At("HEAD"), cancellable = true, remap = false)
	public void getMaxHeatEmittedPerTick(final CallbackInfoReturnable<? super Integer> callback)
	{
		int total = 0;
		final int per = maxHPT / heatexchangerslots.size();
		for (int i = 0; i < heatexchangerslots.size(); ++i)
			if (heatexchangerslots.get(i) != null)
				total += per;
		callback.setReturnValue(total);
	}
}
