package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.utils.ReflectionHelper;

import net.minecraftforge.fluids.FluidTank;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ic2.core.block.machine.tileentity.TileEntityFermenter;
import ic2.core.block.machine.tileentity.TileEntityRecycler;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityFermenter.class)
public class MixinTileEntityFermenter
{
	private static final int heatRequest = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/fermenter/heatRequest");
	private final int outputTankSize = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/fermenter/outputTankSize");

	@Inject(method = "<init>", at = @At("RETURN"))
	public void injectInit(@SuppressWarnings("unused") final CallbackInfo callback)
	{
		ReflectionHelper.tamperFinalField(getClass(), "outputTank", this, new FluidTank(outputTankSize));
	}

	@ModifyConstant(method = "work", constant = @Constant(intValue = 100), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getTileEntity(III)Lnet/minecraft/tileentity/TileEntity;"), to = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/FluidTank;drain(IZ)Lnet/minecraftforge/fluids/FluidStack;")), remap = false)
	private int injectHeatRequest(final int _100)
	{
		return heatRequest;
	}
}
