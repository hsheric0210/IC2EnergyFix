package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.eric0210.ic2energyfix.mixins.MixinTileEntityLiquidTankElectricMachine;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
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

		fill(null, new FluidStack(BlocksItems.getFluid(InternalName.fluidUuMatter), newFluidAmount), true);
		energy -= maxEnergy * count;
		callback.setReturnValue(true);
	}
}
