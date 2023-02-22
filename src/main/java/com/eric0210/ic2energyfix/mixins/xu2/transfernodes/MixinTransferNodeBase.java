package com.eric0210.ic2energyfix.mixins.xu2.transfernodes;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.rwtema.extrautils2.transfernodes.TransferNodeBase;
import com.rwtema.extrautils2.transfernodes.Upgrade;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import ic2.core.util.ConfigUtil;

@Mixin(TransferNodeBase.class)
public class MixinTransferNodeBase
{
	private final int baseWaitTicks = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "xu2/transfernode/baseWaitTick");
	private final float tickrateMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "xu2/transfernode/tickrateMultiplier");

	@ModifyConstant(method = "func_73660_a", constant = @Constant(intValue = 20), remap = false)
	public int setBaseWaitTicks(final int prev)
	{
		return baseWaitTicks;
	}

	@Redirect(method = "func_73660_a", at = @At(value = "INVOKE", target = "Lcom/rwtema/extrautils2/transfernodes/TransferNodeBase;stepCooldown()I"), remap = false)
	public int applyTickrateMultiplier(final TransferNodeBase instance)
	{
		return (int) ((instance.getUpgradeLevel(Upgrade.SPEED) + 1) * tickrateMultiplier);
	}
}
