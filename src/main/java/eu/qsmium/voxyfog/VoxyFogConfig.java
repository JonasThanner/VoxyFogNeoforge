package eu.qsmium.voxyfog;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;


public final class VoxyFogConfig
{
    public static final VoxyFogConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;
    static
    {
        Pair<VoxyFogConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(VoxyFogConfig::new);
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }


    //The final stored variable of a real config object
    public final ModConfigSpec.ConfigValue<Boolean> enabled;
    public final ModConfigSpec.ConfigValue<Float> fogStartPercentage;
    public final ModConfigSpec.ConfigValue<Float> fogEndPercentage;

    //Default call
    private VoxyFogConfig(ModConfigSpec.Builder builder)
    {
        enabled = builder.define("enabled", true);
        fogStartPercentage = builder.define("fogStartPercentage", 0.85f);
        fogEndPercentage = builder.define("fogEndPercentage", 0.98f);
    }

    public static <T> T SafeConfigGetValue(ModConfigSpec.ConfigValue<T> value)
    {
        return VoxyFogConfig.CONFIG_SPEC.isLoaded() ? value.get() : value.getDefault();
    }
}

