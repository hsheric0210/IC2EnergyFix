package com.eric0210.ic2energyfix;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

public class IC2EnergyFix implements IFMLLoadingPlugin
{
    public IC2EnergyFix()
    {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.ic2energyfix.json");
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return ZERO_LENGTH_STRING_ARRAY;
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(final Map data)
    {
    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }

    private static final String[] ZERO_LENGTH_STRING_ARRAY = new String[0];
}
