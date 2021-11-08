package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.mixins.minecraft.MixinTileEntity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
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

	@Redirect(method = "updateHumidity", at = @At(value = "INVOKE", target = "Lic2/api/crops/Crops;getHumidityBiomeBonus(Lnet/minecraft/world/biome/BiomeGenBase;)I"), remap = false)
	public int injectHumidityBiomeBonus(final Crops instance, final BiomeGenBase biomeGenBase)
	{
		return humidityBiomeBonus ? instance.getHumidityBiomeBonus(biomeGenBase) : 10;
	}

	@Redirect(method = "updateHumidity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlockMetadata(III)I", ordinal = 0), remap = false)
	public int injectHumidityFarmlandWetnessBonus(final World instance, final int x, final int y, final int z)
	{
		return humidityFarmlandWetnessBonus ? instance.getBlockMetadata(x, y - 1, z) : 7;
	}

	@Redirect(method = "updateNutrients", at = @At(value = "INVOKE", target = "Lic2/api/crops/Crops;getNutrientBiomeBonus(Lnet/minecraft/world/biome/BiomeGenBase;)I"), remap = false)
	public int injectNutrientsBiomeBonus(final Crops instance, final BiomeGenBase biomeGenBase)
	{
		return nutrientsBiomeBonus ? instance.getNutrientBiomeBonus(biomeGenBase) : 10;
	}

	@Redirect(method = "updateNutrients", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getBlock(III)Lnet/minecraft/block/Block;"), remap = false)
	public Block injectNutrientsDirtBonus(final World instance, final int x, final int y, final int z)
	{
		return nutrientsDirtBonus ? instance.getBlock(x,y,z) : Blocks.dirt; // Always-succeeding comparison
	}

	@Redirect(method = "updateAirQuality", at = @At(value = "FIELD", target = "Lic2/core/crop/TileEntityCrop;yCoord:I", ordinal = 0), remap = false)
	public int injectAirQualityHeightBonus(final TileEntityCrop instance)
	{
		return airQualityHeightBonus ? instance.yCoord : 124; // (124 - 64) / 15 = 4
	}

	@Redirect(method = "updateAirQuality", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isBlockNormalCubeDefault(IIIZ)Z"), remap = false)
	public boolean injectAirQualityFreshnessBonus1(final World instance, final int x, final int y, final int z, final boolean flag)
	{
		return airQualityFreshnessBonus && instance.isBlockNormalCubeDefault(x, y, z, flag);
	}

	@Redirect(method = "updateAirQuality", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getTileEntity(III)Lnet/minecraft/tileentity/TileEntity;"), remap = false)
	public TileEntity injectAirQualityFreshnessBonus2(final World instance, final int x, final int y, final int z)
	{
		return airQualityFreshnessBonus ? instance.getTileEntity(x, y, z) : null;
	}

	@Redirect(method = "updateAirQuality", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;canBlockSeeTheSky(III)Z"), remap = false)
	public boolean injectAirQualityCanSeeTheSkyBonus(final World instance, final int x, final int y, final int z)
	{
		return !airQualityCanSeeTheSkyBonus || instance.canBlockSeeTheSky(x, y, z);
	}
}
