package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.mixins.MixinTileEntity;

import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import ic2.core.block.machine.tileentity.TileEntitySolarDestiller;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntitySolarDestiller.class)
public abstract class MixinTileEntitySolarDestiller extends MixinTileEntity
{
	private final int hotTickrate = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/solardestiller/hotBiomeTickrate");
	private final int coldTickrate = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/solardestiller/coldBiomeTickrate");
	private final int defaultTickrate = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/solardestiller/defaultTickrate");

	@Inject(method = "recycleChance", at = @At("HEAD"), cancellable = true, remap = false)
	public void getTickRate(final CallbackInfoReturnable<? super Integer> callback)
	{
		if (BiomeDictionary.isBiomeOfType(field_145850_b.getWorldChunkManager().getBiomeGenAt(field_145851_c, field_145849_e), Type.HOT))
			callback.setReturnValue(hotTickrate);

		if (BiomeDictionary.isBiomeOfType(field_145850_b.getWorldChunkManager().getBiomeGenAt(field_145851_c, field_145849_e), Type.COLD))
			callback.setReturnValue(coldTickrate);

		callback.setReturnValue(defaultTickrate);

		callback.cancel();
	}
}
