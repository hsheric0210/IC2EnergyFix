package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import ic2.core.block.invslot.InvSlotDischarge;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;

@Mixin(TileEntityElectricMachine.class)
public abstract class MixinTileEntityElectricMachine
{
	@Shadow(remap = false)
	@Final
	protected double energy;

	@Shadow(remap = false)
	@Final
	public InvSlotDischarge dischargeSlot;
}
