package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import net.minecraftforge.fluids.FluidStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ic2.core.block.machine.tileentity.TileEntityMatter;
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;

@Mixin(TileEntityMatter.class)
public abstract class MixinTileEntityMatter extends MixinTileEntityLiquidTankElectricMachine
{
	@Inject(method = "attemptGeneration", at = @At("HEAD"), cancellable = true, remap = false)
	public void attemptGeneration(final CallbackInfoReturnable<? super Boolean> callback)
	{
		final int count = (int) ((energy - energy % maxEnergy) / maxEnergy);
		final int newFluidAmount = Math.min(fluidTank.getCapacity() - fluidTank.getFluidAmount(), count);
		if (newFluidAmount == fluidTank.getCapacity() - fluidTank.getFluidAmount())
			callback.setReturnValue(false);
		else
		{
			fill(null, new FluidStack(BlocksItems.getFluid(InternalName.fluidUuMatter), newFluidAmount), true);
			energy -= maxEnergy * count;
			callback.setReturnValue(true);
		}
	}
}
