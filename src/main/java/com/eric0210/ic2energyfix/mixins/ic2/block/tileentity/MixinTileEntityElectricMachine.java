package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.mixins.ic2.block.MixinTileEntityInventory;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import ic2.core.block.invslot.InvSlotDischarge;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;

@Mixin(TileEntityElectricMachine.class)
public abstract class MixinTileEntityElectricMachine extends MixinTileEntityInventory
{
	@Shadow(remap = false)
	public double energy;

	@Shadow(remap = false)
	public int maxEnergy;

	@Shadow(remap = false)
	private boolean addedToEnergyNet;

	@Shadow(remap = false)
	private int tier;

	@Shadow(remap = false)
	private float guiChargeLevel;

	@Shadow(remap = false)
	@Final
	public InvSlotDischarge dischargeSlot;
}
