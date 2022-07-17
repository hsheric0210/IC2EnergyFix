package com.eric0210.ic2energyfix.mixins.ic2.item;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import ic2.core.item.tool.ItemToolMiningLaser;
import ic2.core.util.ConfigUtil;

@Mixin(ItemToolMiningLaser.class)
public class MixinItemToolMiningLaser
{
	private final float rangeMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/tool/miningLaser/rangeMultiplier");
	private final float powerMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/tool/miningLaser/powerMultiplier");

	@ModifyVariable(method = "shootLaser(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lic2/core/util/Vector3;Lic2/core/util/Vector3;Lnet/minecraft/entity/EntityLivingBase;FFIZZ)Z", at = @At("HEAD"), ordinal = 0, remap = false)
	public float injectRangeMultiplier(final float range)
	{
		return range * rangeMultiplier;
	}

	@ModifyVariable(method = "shootLaser(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lic2/core/util/Vector3;Lic2/core/util/Vector3;Lnet/minecraft/entity/EntityLivingBase;FFIZZ)Z", at = @At("HEAD"), ordinal = 1, remap = false)
	public float injectPowerMultiplier(final float power)
	{
		return power * powerMultiplier;
	}
}
