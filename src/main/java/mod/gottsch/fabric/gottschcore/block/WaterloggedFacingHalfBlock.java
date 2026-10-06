package mod.gottsch.fabric.gottschcore.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.world.WorldAccess;
import net.minecraft.block.Block;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;

/**
 * 
 * @author Mark Gottschling on May 7, 2025
 *
 */
public class WaterloggedFacingHalfBlock extends FacingHalfBlock implements Waterloggable {

	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

	/**
	 *
	 * @param properties
	 */
	public WaterloggedFacingHalfBlock(Settings properties) {
		super(properties);
		this.setDefaultState(getDefaultState()
				.with(WATERLOGGED, Boolean.valueOf(false)));
	}
	
	/**
	 * 
	 */
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(WATERLOGGED);
	}
	
	/**
	 * This method returns the state of the block so that the correct entry in the
	 * blockstate.json file can be selected and the corresponding block model
	 * rendered.
	 */
	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		BlockPos blockPos = context.getBlockPos();
		FluidState fluidState = context.getWorld().getFluidState(blockPos);

		BlockState blockState = super.getPlacementState(context);
		blockState = blockState.with(WATERLOGGED, Boolean.valueOf(fluidState.getFluid() == Fluids.WATER));

		return blockState;
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess levelAccessor, BlockPos pos, BlockPos p_56930_) {
		if (state.get(WATERLOGGED)) {
			levelAccessor.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(levelAccessor));
		}
		return super.getStateForNeighborUpdate(state, direction, newState, levelAccessor, pos, p_56930_);
	}
	
	@Override
	public FluidState getFluidState(BlockState blockState) {
		return blockState.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(blockState);
	}
	
	@Override
	public boolean hasSidedTransparency(BlockState state) {
		return true;
	}
}
