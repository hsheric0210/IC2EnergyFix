package com.eric0210.ic2energyfix.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import ic2.core.block.TileEntityBlock;

@Mixin(TileEntityBlock.class)
public abstract class MixinTileEntityBlock extends MixinTileEntity
{
	@Shadow(remap = false)
	public abstract short getFacing();

	@Shadow(remap = false)
	public abstract boolean getActive();

	@Shadow(remap = false)
	public abstract void setActive(boolean active);

	@Shadow(remap = false)
	protected void updateEntityServer()
	{
	}
}
