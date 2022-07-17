package com.eric0210.ic2energyfix.mixins.ic2.block;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.utils.ReflectionHelper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ic2.core.block.BlockFoam.FoamType;
import ic2.core.util.ConfigUtil;

@Mixin(FoamType.class)
public abstract class MixinFoamType
{
	private static final int DEFAULT_NORMALFOAM_HARDEN_TIME = 300;
	private static final int DEFAULT_REINFORCEDFOAM_HARDEN_TIME = 600;

	private final int normalFoamBaseHardenTime = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/block/foam/normal/hardenTime");
	private final int reinforcedFoamBaseHardenTime = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/block/foam/reinforced/hardenTime");

	@Inject(method = "<init>", at = @At("RETURN"))
	private void injectBaseHardenTime(final String enumName, final int enumOrdinal, final int hardenTime, final CallbackInfo ci)
	{
		if (hardenTime == DEFAULT_NORMALFOAM_HARDEN_TIME)
			ReflectionHelper.tamperFinalField(FoamType.class, "hardenTime", this, normalFoamBaseHardenTime);
		else if (hardenTime == DEFAULT_REINFORCEDFOAM_HARDEN_TIME)
			ReflectionHelper.tamperFinalField(FoamType.class, "hardenTime", this, reinforcedFoamBaseHardenTime);
	}
}
