package com.eric0210.ic2energyfix.mixins.ic2.item;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import ic2.core.item.tool.ItemToolMiningLaser;
import ic2.core.util.ConfigUtil;

@Mixin(ItemToolMiningLaser.class)
public class MixinItemToolMiningLaser
{
	private final float powerMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/tool/miningLaser/powerMultiplier");

	@ModifyVariable(method = "shootLaser(Lnet/minecraft/world/World;Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;FFIZZDDD)Z", at = @At("HEAD"), ordinal = 1, remap = false)
	public float shootLaser(final float power)
	{
		return power * powerMultiplier;
	}
}
