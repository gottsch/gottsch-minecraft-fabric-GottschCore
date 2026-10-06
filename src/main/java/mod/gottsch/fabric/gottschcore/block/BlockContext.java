/*
 * This file is part of  GottschCore.
 * Copyright (c) 2025 Mark Gottschling (gottsch)
 *
 * All rights reserved.
 *
 * GottschCore is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GottschCore is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GottschCore.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package mod.gottsch.fabric.gottschcore.block;


import mod.gottsch.fabric.gottschcore.spatial.Coords;
import mod.gottsch.fabric.gottschcore.spatial.ICoords;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.RegistryWorldView;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;

/**
 * 
 * @author Mark Gottschling on Oct 19, 2022
 *
 */
public class BlockContext {
	private final ICoords coords;
	private final BlockState state;
	
	/**
	 * 
	 * @param world
	 * @param coords
	 */
	public BlockContext(RegistryWorldView level, ICoords coords) {
		this.coords = coords;
		this.state = level.getBlockState(coords.toPos());
	}
	
	public BlockContext(RegistryWorldView level, BlockPos pos) {
		this(level, new Coords(pos));
	}
	
	public BlockContext(ICoords coords, BlockState state) {
		this.coords = coords;
		this.state = state;
	}
	
	public boolean hasState() {
		if (state == null)
			return false;
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public Block toBlock() {
		if (state != null) {
			return state.getBlock();
		}
		return null;
	}
	
	public static Block toBlock(final World level, final ICoords coords) {
		BlockState blockState = level.getBlockState(coords.toPos());
		if (blockState != null)
			return blockState.getBlock();
		return null;
	}
	
	public boolean equalsBlock(Block block) {
		if (state.getBlock() == block)
			return true;
		return false;
	}

	public boolean isAir() {
		return state.isAir();
	}

	/**
	 * Wrapper for Material.isReplaceable();
	 * 
	 * @return
	 */
	public boolean isReplaceable() {
		return state.isReplaceable();
	}

	/**
	 * Wrapper for Material.isSolid()
	 * 
	 * @return
	 */
	public boolean isSolid() {
		return state.isSolid();
	}
	
	public boolean isFluid() {
		return !state.getFluidState().isEmpty();
	}
	
	public boolean isBurning() {
		// NeoForge IBlockExtension#isBurning default: fire or lava
		return state.isOf(Blocks.FIRE) || state.isOf(Blocks.LAVA);
	}
	
	public boolean isLeaves() {
		return state.getBlock().getDefaultMapColor() == MapColor.DARK_GREEN &&
				state.isBurnable() &&
				state.getPistonBehavior() == PistonBehavior.DESTROY;
	}
	
	public ICoords getCoords() {
		return coords;
	}

	public BlockState getState() {
		return state;
	}
}
