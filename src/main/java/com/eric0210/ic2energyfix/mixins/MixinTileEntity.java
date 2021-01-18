package com.eric0210.ic2energyfix.mixins;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TileEntity.class)
public abstract class MixinTileEntity
{
	@Shadow(remap = false)
	protected World field_145850_b;

	@Shadow(remap = false)
	public int field_145851_c;

	@Shadow(remap = false)
	public int field_145848_d;

	@Shadow(remap = false)
	public int field_145849_e;
}
