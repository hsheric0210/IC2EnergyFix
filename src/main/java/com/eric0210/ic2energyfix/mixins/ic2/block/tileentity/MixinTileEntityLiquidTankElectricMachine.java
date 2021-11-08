package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import ic2.core.block.TileEntityLiquidTankElectricMachine;

@Mixin(TileEntityLiquidTankElectricMachine.class)
public abstract class MixinTileEntityLiquidTankElectricMachine extends MixinTileEntityElectricMachine
{
	@Shadow(remap = false)
	@Final
	protected FluidTank fluidTank;

	@Shadow(remap = false)
	public abstract int fill(ForgeDirection from, FluidStack resource, boolean doFill);
}
