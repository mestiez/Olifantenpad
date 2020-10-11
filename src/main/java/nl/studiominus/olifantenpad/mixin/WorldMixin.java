package nl.studiominus.olifantenpad.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import nl.studiominus.olifantenpad.DepthMapState;
import nl.studiominus.olifantenpad.OlifantenpadInitialiser;
import nl.studiominus.olifantenpad.PathLinks;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin
{
    @Shadow
    public abstract boolean isClient();

    @Shadow
    public abstract @Nullable MinecraftServer getServer();

    @Shadow public abstract RegistryKey<World> getRegistryKey();

    @Inject(at = @At("RETURN"), method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z")
    public void setBlockState(BlockPos pos, BlockState state, int flags, int maxUpdateDepth, CallbackInfoReturnable<Boolean> cir)
    {
        if (cir.getReturnValue() && !isClient())
        {
            MinecraftServer server = getServer();
            if (server == null)
                return;

            DepthMapState depthMapState = DepthMapState.getInstance(server, getRegistryKey());
            depthMapState.depthMap.remove(pos);
            depthMapState.setDirty(true);

            OlifantenpadInitialiser.log(Level.DEBUG, "Depth entry at " + pos + " reset...");
        }
    }
}
