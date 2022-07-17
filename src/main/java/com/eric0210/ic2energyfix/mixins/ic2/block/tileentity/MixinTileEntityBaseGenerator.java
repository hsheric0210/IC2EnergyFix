package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import ic2.core.block.comp.Energy;
import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.invslot.InvSlotCharge;

@Mixin(TileEntityBaseGenerator.class)
public class MixinTileEntityBaseGenerator
{
	@Shadow(remap = false)
	@Final
	public InvSlotCharge chargeSlot;

	@Shadow(remap = false)
	@Final
	protected Energy energy;
}
