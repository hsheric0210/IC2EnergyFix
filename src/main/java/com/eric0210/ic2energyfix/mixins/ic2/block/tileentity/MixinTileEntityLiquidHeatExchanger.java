package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ic2.core.block.invslot.InvSlotConsumable;
import ic2.core.block.machine.tileentity.TileEntityLiquidHeatExchanger;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityLiquidHeatExchanger.class)
public class MixinTileEntityLiquidHeatExchanger
{
	private final int maxHeatExchangePerTick = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/liquidHeatExchanger/maxHeatExchangePerTick");
	private final int inputTankCapacity = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/liquidHeatExchanger/inputTankCapacity");
	private final int outputTankCapacity = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/liquidHeatExchanger/outputTankCapacity");

	@Shadow(remap = false)
	@Final
	public InvSlotConsumable heatexchangerslots;

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 2000, ordinal = 0))
	public int injectInputTankCapacity(final int _2000)
	{
		return inputTankCapacity;
	}

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 2000, ordinal = 1))
	public int injectOutputTankCapacity(final int _2000)
	{
		return outputTankCapacity;
	}

	// Previous implementation; doesn't work because ModifyConstant doesn't supports IINC instruction
	// @ModifyConstant(method = "getMaxHeatEmittedPerTick", constant = @Constant(intValue = 10), remap = false)
	// public int injectHeatPerExchangerFix(final int _10)
	// {
	// return maxHeatExchangePerTick / heatexchangerslots.size();
	// }

	private int count = 0;

	@Inject(method = "getMaxHeatEmittedPerTick", at = @At(value = "JUMP", opcode = Opcodes.IFNE, ordinal = 0, shift = Shift.AFTER), remap = false)
	public void injectHeatPerExchangerFix1(final CallbackInfoReturnable<? super Integer> cir)
	{
		count += maxHeatExchangePerTick/10;
	}

	@Inject(method = "getMaxHeatEmittedPerTick", at = @At(value = "RETURN", shift = Shift.BEFORE), cancellable = true, remap = false)
	public void injectHeatPerExchangerFix2(final CallbackInfoReturnable<? super Integer> cir)
	{
		cir.setReturnValue(count);
		count = 0;
	}
}
