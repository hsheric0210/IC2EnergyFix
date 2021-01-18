package com.eric0210.ic2energyfix.mixins.ic2.block.tileentity;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.eric0210.ic2energyfix.IC2EnergyFixConfig;
import com.eric0210.ic2energyfix.mixins.MixinTileEntityInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IHeatSource;
import ic2.core.block.generator.tileentity.TileEntityStirlingGenerator;
import ic2.core.util.ConfigUtil;

@Mixin(TileEntityStirlingGenerator.class)
public abstract class MixinTileEntityStirlingGenerator extends MixinTileEntityInventory
{
	private final double outputMultiplier = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/generator/stirling/outputMultiplier");
	private final double outputStatic = ConfigUtil.getFloat(IC2EnergyFixConfig.get(), "balance/generator/stirling/outputStatic");
	private final int maxStorage = ConfigUtil.getInt(IC2EnergyFixConfig.get(), "balance/generator/stirling/maxStorage");

	@Shadow(remap = false)
	public double EUstorage;

	@Shadow(remap = false)
	public double production;

	@Shadow(remap = false)
	public int receivedheat;

	@Shadow(remap = false)
	@Final
	public double productionpeerheat;

	@Shadow(remap = false)
	public abstract int getSourceTier();

	@Shadow(remap = false)
	protected abstract boolean gainEnergy();

	@Inject(method = "getOfferedEnergy", at = @At("HEAD"), remap = false, cancellable = true)
	public void getOfferedEnergy(final CallbackInfoReturnable<Double> callback)
	{
		callback.setReturnValue(Math.min(EUstorage, outputStatic == -1 ? EnergyNet.instance.getPowerFromTier(getSourceTier()) * outputMultiplier : outputStatic));
	}

	@Inject(method = "gainEnergy", at = @At("HEAD"), remap = false, cancellable = true)
	protected void gainEnergy(final CallbackInfoReturnable<Boolean> callback)
	{
		final ForgeDirection dir = ForgeDirection.getOrientation(getFacing());
		final TileEntity te = field_145850_b.getTileEntity(field_145851_c + dir.offsetX, field_145848_d + dir.offsetY, field_145849_e + dir.offsetZ);
		if (te instanceof IHeatSource)
		{
			final int heatbandwith = ((IHeatSource) te).maxrequestHeatTick(dir.getOpposite());

			receivedheat = ((IHeatSource) te).requestHeat(dir.getOpposite(), heatbandwith);
			if (receivedheat != 0)
			{
				production = receivedheat * productionpeerheat;
				final double generated = Math.min(production, maxStorage - EUstorage);
				if (generated > 0.0D)
				{
					EUstorage += generated;
					callback.setReturnValue(true);
				}
			}
		}
		production = 0.0D;
		receivedheat = 0;
		callback.setReturnValue(false);
	}

	@Inject(method = "updateEntityServer", at = @At("HEAD"), cancellable = true, remap = false)
	protected void updateEntityServer(final CallbackInfo callback)
	{
		final boolean needsInvUpdate = false;
		final boolean newActive = gainEnergy();
		if (EUstorage > maxStorage)
			EUstorage = maxStorage;

		if (needsInvUpdate)
			func_70296_d();

		if (getActive() != newActive)
			setActive(newActive);

		callback.cancel();
	}

	@Inject(method = "gaugeEUStorageScaled", at = @At("HEAD"), cancellable = true, remap = false)
	public void gaugeEUStorageScaled(final int i, final CallbackInfoReturnable<Integer> callback)
	{
		callback.setReturnValue((int) (EUstorage * i / maxStorage));
	}
}
