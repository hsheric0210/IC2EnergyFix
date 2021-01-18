package com.eric0210.ic2energyfix.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import ic2.core.block.TileEntityInventory;

@Mixin(TileEntityInventory.class)
public abstract class MixinTileEntityInventory extends MixinTileEntityBlock
{
	@Shadow(remap = false)
	public abstract void func_70296_d();
}
