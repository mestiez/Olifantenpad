package nl.studiominus.olifantenpad.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(Entity.class)
public abstract class EntityMixin
{
    @Shadow public float prevHorizontalSpeed;
    @Shadow public float horizontalSpeed;
    @Shadow @Final protected Random random;

    @Shadow public abstract boolean isSneaking();

    @Shadow protected abstract BlockPos getLandingPos();

    @Shadow public World world;

    @Shadow public abstract boolean isOnGround();

    @Shadow public abstract Vec3d getVelocity();
}
