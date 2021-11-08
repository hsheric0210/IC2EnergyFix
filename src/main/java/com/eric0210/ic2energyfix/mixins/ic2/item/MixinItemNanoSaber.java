package com.eric0210.ic2energyfix.mixins.ic2.item;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import ic2.core.item.ItemUpgradeModule;
import ic2.core.item.tool.ItemNanoSaber;
import ic2.core.util.ConfigUtil;

@Mixin(ItemNanoSaber.class)
public class MixinItemNanoSaber
{
	private final float newDiggingSpeed = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/tool/nanosaber/digSpeed");

	@ModifyConstant(method = "getDigSpeed", constant = @Constant(floatValue = 4.0F), remap = false)
	public float injectGetProcessTimeMultiplier(final float _4_0f)
	{
		return newDiggingSpeed;
	}
}
