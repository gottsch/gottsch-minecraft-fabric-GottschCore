package mod.gottsch.fabric.gottschcore.block;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.block.enums.BlockHalf;

/**
 * @author by Mark Gottschling on 5/7/2025
 */
public interface IHalfBlock {
    public static final EnumProperty<BlockHalf> HALF = Properties.BLOCK_HALF;

    public BlockHalf getHalf(BlockState state);
}
