package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.utils.ReflectionHelper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import ic2.core.block.machine.tileentity.TileEntityCondenser;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityCondenser.class)
public class MixinTileEntityCondenser
{
	private final int passiveCooling = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/condenser/passiveCooling");
	private final int activeCoolingPerVent = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/condenser/activeCoolingPerVent");

	@Inject(method = "<init>", at = @At("RETURN"))
	public void init(@SuppressWarnings("unused") final CallbackInfo callback)
	{
		ReflectionHelper.tamperFinalField(getClass(), "passivecolling", this, passiveCooling > Short.MAX_VALUE ? Short.MAX_VALUE : (short) passiveCooling);
		ReflectionHelper.tamperFinalField(getClass(), "activecollingperVent", this, activeCoolingPerVent > Short.MAX_VALUE ? Short.MAX_VALUE : (short) activeCoolingPerVent);
	}
}
