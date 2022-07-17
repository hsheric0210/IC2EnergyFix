package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import ic2.core.block.generator.tileentity.TileEntityGeoGenerator;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityGeoGenerator.class)
public class MixinTileEntityGeoGenerator extends MixinTileEntityBaseGenerator
{
	private static final int energyCapacity = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generator/geothermal/energyCapacity");
	private static final int fluidTankCapacity = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generator/geothermal/fluidTankCapacity");

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 2400))
	private static int injectEnergyCapacity(final int _2400)
	{
		return energyCapacity;
	}

	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 8000))
	private static int injectTankCapacity(final int _8000)
	{
		return fluidTankCapacity;
	}
}
