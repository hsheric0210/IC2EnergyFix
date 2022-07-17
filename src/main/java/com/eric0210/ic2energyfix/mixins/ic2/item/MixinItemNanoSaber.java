package com.eric0210.ic2energyfix.mixins.ic2.item;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import ic2.core.item.tool.ItemNanoSaber;
import ic2.core.util.ConfigUtil;

@Mixin(ItemNanoSaber.class)
public class MixinItemNanoSaber
{
	private final float webDigSpeed = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/tool/nanosaber/webDigSpeed");
	private final float digSpeed = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/tool/nanosaber/digSpeed");

	@ModifyConstant(method = "getDestroySpeed", constant = @Constant(floatValue = 50.0F), remap = false)
	public float injectWebDigSpeed(final float _50_0f)
	{
		return webDigSpeed;
	}

	@ModifyConstant(method = "getDestroySpeed", constant = @Constant(floatValue = 4.0F), remap = false)
	public float injectDigSpeed(final float _4_0f)
	{
		return digSpeed;
	}
}
