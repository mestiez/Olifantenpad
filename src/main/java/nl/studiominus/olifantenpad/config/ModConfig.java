package nl.studiominus.olifantenpad.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import nl.studiominus.olifantenpad.OlifantenpadInitialiser;

@Config(name = OlifantenpadInitialiser.MOD_ID)
public class ModConfig implements ConfigData
{
    @ConfigEntry.Gui.Tooltip
    public float pathSoftnessMultiplier = 1f;

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(max = 100)
    public int chance = 80;

    @ConfigEntry.Gui.Tooltip
    public boolean sneakSafe = true;

    @ConfigEntry.Gui.Excluded
    public PathConfigEntry[] paths = new PathConfigEntry[]{
            new PathConfigEntry("minecraft:grass_block", "minecraft:coarse_dirt", 10f),
            new PathConfigEntry("minecraft:coarse_dirt", "minecraft:grass_path", 10f),
    };

    public class PathConfigEntry
    {
        public String fromID;
        public String toID;
        public float requiredDepth;

        public PathConfigEntry(String from, String to, float depth)
        {
            fromID = from;
            toID = to;
            requiredDepth = depth;
        }
    }
}
