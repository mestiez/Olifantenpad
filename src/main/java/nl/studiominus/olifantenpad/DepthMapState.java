package nl.studiominus.olifantenpad;

import com.google.gson.Gson;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Objects;

public class DepthMapState extends PersistentState
{
    public static final String KEY = OlifantenpadInitialiser.MOD_ID + "depth_map_state";

    public HashMap<BlockPos, Float> depthMap = new HashMap<>();
    private Gson gson;

    private DepthMapState()
    {
        super(KEY);
        gson = new Gson();
    }

    @Override
    public CompoundTag toTag(CompoundTag tag)
    {
        for (BlockPos entry : depthMap.keySet())
        {
            float depth = depthMap.get(entry);
            tag.putFloat(gson.toJson(entry), depth);
        }
        System.out.println(depthMap.size() + " path depth entries saved to world nbt");
        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag)
    {
        depthMap.clear();

        for (String entry : tag.getKeys())
        {
            float depth = tag.getFloat(entry);
            BlockPos pos = gson.fromJson(entry, BlockPos.class);

            depthMap.put(pos, depth);
        }

        System.out.println(depthMap.size() + " path depth entries loaded from world nbt");
    }

    public static DepthMapState getInstance(MinecraftServer server, RegistryKey<World> dimension)
    {
        PersistentStateManager manager = Objects.requireNonNull(server.getWorld(dimension)).getPersistentStateManager();
        return manager.getOrCreate(DepthMapState::new, KEY);
    }
}
