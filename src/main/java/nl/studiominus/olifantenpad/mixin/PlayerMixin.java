package nl.studiominus.olifantenpad.mixin;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import nl.studiominus.olifantenpad.DepthMapState;
import nl.studiominus.olifantenpad.PathLinks;
import nl.studiominus.olifantenpad.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends EntityMixin
{
    @Shadow
    public abstract boolean isSwimming();

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info)
    {
        if (world.isClient())
            return;

        if (isSwimming() || !isOnGround()) return;

        double speed = Math.abs(prevHorizontalSpeed - horizontalSpeed);

        DegradeGround((float) speed);
    }

    private void DegradeGround(float intensity)
    {
        MinecraftServer server = world.getServer();
        if (server == null)
            return;

        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        if (config.chance < random.nextInt(100) || intensity < 0.01 || (isSneaking() && config.sneakSafe))
            return;

        DepthMapState depthMapState = DepthMapState.getInstance(server, world.getRegistryKey());
        BlockPos pos = getLandingPos();
        BlockState state = world.getBlockState(pos);

        float currentDepth = depthMapState.depthMap.getOrDefault(pos, 0f) + intensity;
        depthMapState.depthMap.put(pos, currentDepth);

        currentDepth *= config.pathSoftnessMultiplier;

        depthMapState.setDirty(true);

        for (PathLinks.PathLink link : PathLinks.LINKS)
        {
            if (link.from == state.getBlock() && currentDepth > link.requiredDepth)
            {
                world.setBlockState(pos, link.to.getDefaultState(), 11);
            }
        }
    }
}
