package mod.gottsch.fabric.gottschcore.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.block.Block;
import net.minecraft.util.BlockRotation;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.block.enums.BlockHalf;

/**
 * @author by Mark Gottschling on 5/7/2025
 */
public class FacingHalfBlock extends HalfBlock implements IFacingBlock {

    public FacingHalfBlock(Settings properties) {
        super(properties);
        this.setDefaultState(getDefaultState()
                .with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        Direction direction = context.getSide();
        BlockPos pos = context.getBlockPos();
        return super.getPlacementState(context)
                .with(FACING, context.getPlayerLookDirection().getOpposite()).with(HALF, direction != Direction.DOWN && (direction == Direction.UP || !(context.getHitPos().y - (double) pos.getY() > 0.5D)) ? BlockHalf.BOTTOM : BlockHalf.TOP);
    }

    /** @deprecated */
    @Deprecated
    @Override
    public BlockState rotate(BlockState state, BlockRotation rot) {
        return (BlockState)state.with(FACING, rot.rotate(this.getFacing(state)));
    }

    public Direction getFacing(BlockState state) {
        return (Direction) state.get(FACING);
    }
}
