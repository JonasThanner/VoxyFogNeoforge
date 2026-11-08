package eu.qsmium.voxyfog;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;


@Mod(VoxyFog.MODID)
public class VoxyFog
{
    public static final String MODID = "voxyfog";

    public VoxyFog(ModContainer container)
    {
        container.registerConfig(ModConfig.Type.COMMON, VoxyFogConfig.CONFIG_SPEC);
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

}
