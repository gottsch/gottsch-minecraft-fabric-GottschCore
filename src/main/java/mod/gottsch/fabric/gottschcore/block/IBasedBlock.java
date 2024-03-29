  /*
 * This file is part of  GottschCore.
 * Copyright (c) 2020 Mark Gottschling (gottsch)
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

  import net.minecraft.block.BlockState;
  import net.minecraft.state.property.EnumProperty;
  import net.minecraft.util.math.Direction;

  /**
 * 
 * @author Mark Gottschling on Jan 15, 2020
 *
 */
public interface IBasedBlock extends IGottschCoreBlock {
	public static final EnumProperty<Direction> BASE = EnumProperty.of("base", Direction.class);
	
	Direction getBase(BlockState state);
}
