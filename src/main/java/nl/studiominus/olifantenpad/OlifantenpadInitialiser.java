package nl.studiominus.olifantenpad;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;

import nl.studiominus.olifantenpad.config.ModConfig;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OlifantenpadInitialiser implements net.fabricmc.api.ModInitializer
{
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "olifantenpad";
    public static final String MOD_NAME = "Olifantenpad";

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        ModConfig config = AutoConfig.register(ModConfig.class, GsonConfigSerializer::new).getConfig();

        PathLinks.LINKS = new PathLinks.PathLink[config.paths.length];

        for (int i = 0; i < config.paths.length; i++)
        {
            ModConfig.PathConfigEntry configEntry = config.paths[i];
            PathLinks.PathLink createdLink = new PathLinks.PathLink(
                    configEntry.fromID,
                    configEntry.toID,
                    configEntry.requiredDepth);
            PathLinks.LINKS[i] = createdLink;
        }
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}