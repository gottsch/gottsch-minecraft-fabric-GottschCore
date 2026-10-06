
package mod.gottsch.fabric.gottschcore.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.block.enums.BlockHalf;

/**
 * @author by Mark Gottschling on 5/7/2025
 */
public class HalfBlock extends Block implements IHalfBlock {

    public HalfBlock(Settings properties) {
        super(properties);
        this.setDefaultState(getDefaultState()
                .with(HALF, BlockHalf.BOTTOM));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(HALF);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        Direction direction = context.getSide();
        BlockPos pos = context.getBlockPos();
        return getDefaultState().with(HALF, direction != Direction.DOWN && (direction == Direction.UP || !(context.getHitPos().y - (double) pos.getY() > 0.5D)) ? BlockHalf.BOTTOM : BlockHalf.TOP);
    }

    @Override
    public BlockHalf getHalf(BlockState state) {
        return (BlockHalf) state.get(HALF);
    }
}
