package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.mixins.minecraft.MixinTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import ic2.api.crops.Crops;
import ic2.core.crop.TileEntityCrop;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityCrop.class)
public class MixinTileEntityCrop extends MixinTileEntity
{
	private final boolean humidityBiomeBonus = ConfigUtil.getBool(IC2EnergyFixConfig.get(), "balance/crop/humidity/biomeBonusCheck");
	private final boolean humidityFarmlandWetnessBonus = ConfigUtil.getBool(IC2EnergyFixConfig.get(), "balance/crop/humidity/farmlandWetnessBonusCheck");

	private final boolean nutrientsBiomeBonus = ConfigUtil.getBool(IC2EnergyFixConfig.get(), "balance/crop/nutrients/biomeBonusCheck");
	private final boolean nutrientsDirtBonus = ConfigUtil.getBool(IC2EnergyFixConfig.get(), "balance/crop/nutrients/belowDirtBonusCheck");

	private final boolean airQualityHeightBonus = ConfigUtil.getBool(IC2EnergyFixConfig.get(), "balance/crop/airQuality/heightBonusCheck");
	private final boolean airQualityFreshnessBonus = ConfigUtil.getBool(IC2EnergyFixConfig.get(), "balance/crop/airQuality/freshnessBonusCheck");
	private final boolean airQualityCanSeeTheSkyBonus = ConfigUtil.getBool(IC2EnergyFixConfig.get(), "balance/crop/airQuality/canSeeTheSkyBonusCheck");

	@ModifyVariable(method = "updateBiomeHumidityBonus", at = @At(value = "FIELD", target = "Lic2/core/crop/TileEntityCrop;biomeHumidityBonus:B", shift = Shift.BEFORE), name = "rainfallBonus", remap = false)
	public int injectBiomeHumidityRainfallBonus(final int value)
	{
		return humidityBiomeBonus ? value : 10;
	}

	@ModifyVariable(method = "updateBiomeHumidityBonus", at = @At(value = "FIELD", target = "Lic2/core/crop/TileEntityCrop;biomeHumidityBonus:B", shift = Shift.BEFORE), name = "coefficientBonus", remap = false)
	public int injectBiomeHumidityCoefficientBonus(final int value)
	{
		return humidityBiomeBonus ? value : 10;
	}

	@Redirect(method = "updateTerrainHumidity", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getValue(Lnet/minecraft/block/properties/IProperty;)Ljava/lang/Comparable;"), remap = false)
	public Integer injectHumidityFarmlandWetnessBonus(final IBlockState instance, final IProperty<? super Integer> tiProperty)
	{
		return humidityFarmlandWetnessBonus ? (Integer) instance.getValue(tiProperty) : 7;
	}

	@Redirect(method = "updateTerrainNutrients", at = @At(value = "INVOKE", target = "Lic2/api/crops/Crops;getNutrientBiomeBonus(Lnet/minecraft/world/biome/Biome;)I"), remap = false)
	public int injectNutrientsBiomeBonus(final Crops instance, final Biome biome)
	{
		return nutrientsBiomeBonus ? instance.getNutrientBiomeBonus(biome) : 10;
	}

	@Redirect(method = "updateTerrainNutrients", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;"), remap = false)
	public Block injectNutrientsDirtBonus(final IBlockState instance)
	{
		return nutrientsDirtBonus ? instance.getBlock() : Blocks.DIRT; // Always-succeeding comparison
	}

	@Redirect(method = "updateTerrainAirQuality", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;getY()I", ordinal = 0), remap = false)
	public int injectAirQualityHeightBonus(final BlockPos instance)
	{
		return airQualityHeightBonus ? instance.getY() : 124; // (124 - 64) / 15 = 4
	}

	@Redirect(method = "updateTerrainAirQuality", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isBlockNormalCube(Lnet/minecraft/util/math/BlockPos;Z)Z"), remap = false)
	public boolean injectAirQualityFreshnessBonus1(final World instance, final BlockPos pos, boolean _default)
	{
		return airQualityFreshnessBonus && instance.isBlockNormalCube(pos, _default);
	}

	@Redirect(method = "updateTerrainAirQuality", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getTileEntity(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/tileentity/TileEntity;"), remap = false)
	public TileEntity injectAirQualityFreshnessBonus2(final World instance, final BlockPos pos)
	{
		return airQualityFreshnessBonus ? instance.getTileEntity(pos) : null;
	}

	@Redirect(method = "updateTerrainAirQuality", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;canSeeSky(Lnet/minecraft/util/math/BlockPos;)Z"), remap = false)
	public boolean injectAirQualityCanSeeTheSkyBonus(final World instance, final BlockPos pos)
	{
		return !airQualityCanSeeTheSkyBonus || instance.canSeeSky(pos);
	}
}
