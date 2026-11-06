package eu.qsmium.voxyfog;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;

public class VoxyFog
{
    public VoxyFog(ModContainer container)
    {
        container.registerConfig(ModConfig.Type.COMMON, VoxyFogConfig.CONFIG_SPEC);
    }

}
