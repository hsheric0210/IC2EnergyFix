package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ic2.core.block.machine.tileentity.TileEntityMatter;
import ic2.core.ref.FluidName;

@Mixin(TileEntityMatter.class)
public abstract class MixinTileEntityMatter extends MixinTileEntityElectricMachine
{
	@Shadow(remap = false)
	@Final
	public FluidTank fluidTank;

	@Inject(method = "attemptGeneration", at = @At("HEAD"), cancellable = true, remap = false)
	public void attemptGeneration(final CallbackInfoReturnable<? super Boolean> callback)
	{
		final int count = (int) ((energy.getEnergy() - energy.getEnergy() % energy.getCapacity()) / energy.getCapacity());
		final int newFluidAmount = Math.min(fluidTank.getCapacity() - fluidTank.getFluidAmount(), count);
		if (newFluidAmount == fluidTank.getCapacity() - fluidTank.getFluidAmount())
			callback.setReturnValue(false);
		else
		{
			fluidTank.fillInternal(new FluidStack(FluidName.uu_matter.getInstance(), newFluidAmount), true);
			energy.useEnergy(energy.getCapacity());
			callback.setReturnValue(true);
		}
	}
}
