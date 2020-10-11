package nl.studiominus.olifantenpad;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class PathLinks
{
    public static class PathLink
    {
        public Block from;
        public Block to;
        public float requiredDepth;

        public PathLink(Block from, Block to, float requiredDepth)
        {
            this.from = from;
            this.to = to;
            this.requiredDepth = requiredDepth;
        }

        public PathLink(String from, String to, float requiredDepth)
        {
            Identifier fromBlock = Identifier.tryParse(from);
            Identifier toBlock = Identifier.tryParse(to);
            this.requiredDepth = requiredDepth;

            if (fromBlock == null || toBlock == null)
                throw new InvalidIdentifierException("An invalid pathlink has been detected: {" + from + ", " + to + "}");

            this.from = Registry.BLOCK.get(Identifier.tryParse(from));
            this.to = Registry.BLOCK.get(Identifier.tryParse(to));
        }
    }

    public static PathLink[] LINKS;
}
