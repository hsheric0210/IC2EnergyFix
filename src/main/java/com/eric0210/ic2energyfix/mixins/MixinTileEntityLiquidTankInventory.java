package com.eric0210.ic2energyfix.mixins;

import net.minecraftforge.fluids.FluidTank;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import ic2.core.block.TileEntityLiquidTankInventory;

@Mixin(TileEntityLiquidTankInventory.class)
public abstract class MixinTileEntityLiquidTankInventory extends MixinTileEntityInventory
{
	@Shadow(remap = false)
	@Final
	protected FluidTank fluidTank;
	
	@Shadow(remap = false)
	public abstract boolean needsFluid();
	
	@Shadow(remap = false)
	public abstract int getTankAmount();
}
