package com.eric0210.ic2energyfix.mixins.minecraft;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TileEntity.class)
public abstract class MixinTileEntity
{
	@Shadow
	protected World world; // worldObj - field_145850_b

	@Shadow
	protected BlockPos pos; // pos -
}
