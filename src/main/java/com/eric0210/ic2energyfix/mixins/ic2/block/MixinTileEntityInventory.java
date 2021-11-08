package com.eric0210.ic2energyfix.mixins.ic2.block;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import ic2.core.block.TileEntityInventory;

@Mixin(TileEntityInventory.class)
public abstract class MixinTileEntityInventory extends MixinTileEntityBlock
{
	@Shadow(remap = false)
	public abstract void func_70296_d(); // markDirty - func_70296_d
}
