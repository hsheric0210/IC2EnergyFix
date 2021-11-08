package com.eric0210.ic2energyfix.mixins.ic2.item;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import ic2.core.item.ElectricItemManager;
import ic2.core.item.ItemUpgradeModule;
import ic2.core.util.ConfigUtil;

@Mixin(ElectricItemManager.class)
public class MixinElectricItemManager
{
	private final boolean ignoreChargeTransferLimit = ConfigUtil.getBool(IC2EnergyFixConfig.get(), "balance/electricItem/ignoreChargeTransferLimit");
	private final boolean ignoreDischargeTransferLimit = ConfigUtil.getBool(IC2EnergyFixConfig.get(), "balance/electricItem/ignoreDischargeTransferLimit");

	@ModifyVariable(method = "charge", at = @At("HEAD"), ordinal = 0, remap = false)
	public boolean injectChargeTransferLimit(final boolean ignoreTransferLimit)
	{
		return ignoreTransferLimit || ignoreChargeTransferLimit;
	}

	@ModifyVariable(method = "discharge", at = @At("HEAD"), ordinal = 0, remap = false)
	public boolean injectDischargeTransferLimit(final boolean ignoreTransferLimit)
	{
		return ignoreTransferLimit || ignoreDischargeTransferLimit;
	}
}
