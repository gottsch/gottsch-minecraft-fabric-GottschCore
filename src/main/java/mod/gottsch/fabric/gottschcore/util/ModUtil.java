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
package mod.gottsch.fabric.gottschcore.util;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.item.Item;
import net.minecraft.world.biome.Biome;
import net.minecraft.block.Block;

import java.util.Optional;

/**
 * @author Mark Gottschling on May 13, 2025
 *
 */
public class ModUtil {
	/*
	MC 1.18.2: net/minecraft/server/MinecraftServer.storageSource
	Name: l => f_129744_ => storageSource
	Side: BOTH
	AT: public net.minecraft.server.MinecraftServer f_129744_ # storageSource
	Type: net/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess
	 */
	private static final String SAVE_FORMAT_LEVEL_SAVE_SRG_NAME = "f_129744_";
	
	/*
	MC 1.18.2: net/minecraft/world/item/Item.maxStackSize
	Name: d => f_41370_ => maxStackSize
	Side: BOTH
	AT: public net.minecraft.world.item.Item f_41370_ # maxStackSize
	Type: int
	*/
	public static final String MAX_STACK_SIZSE_SRG_NAME = "f_41370_";
	
	/*
	 MC 1.19.2: net/minecraft/world/item/Item.maxDamage
	Name: e => f_41371_ => maxDamage
	Side: BOTH
	AT: public net.minecraft.world.item.Item f_41371_ # maxDamage
	Type: int
	 */
	public static final String MAX_DAMAGE_SRG_NAME = "f_41371_";

	public static boolean hasDomain(String name) {

		return name != null && name.contains(":");
	}

	public static Optional<Identifier> asLocation(String name) {
		return hasDomain(name) ? Optional.of(Identifier.of(name)) : Optional.of(Identifier.ofVanilla(name));
	}

	public static Identifier asLocation( String defaultNamespace, String name) {
		return asLocation(name).orElse(Identifier.of(defaultNamespace, name));
	}

	/**
	 * wrapper for vanilla Identifier
	 * @param name
	 * @return
	 */
	public static Identifier mcLocation(String name) {
		return Identifier.ofVanilla(name);
	}

	public static Identifier getName(Block block) {
		// don't bother checking optional - if it is empty, then the block isn't registered and this shouldn't run anyway.
		Identifier name = Registries.BLOCK.getKey(block).get().getValue();
		return name;
	}

	public static Identifier getName(Item item) {
		// don't bother checking optional - if it is empty, then the block isn't registered and this shouldn't run anyway.
		Identifier name = Registries.ITEM.getKey(item).get().getValue();
		return name;
	}
	

	public static Identifier getName(RegistryEntry<Biome> biome) {
		return biome.getKey().get().getValue();	
	}

//	public static void setItemMaxStackSize(Item item, int size) {
//		ObfuscationReflectionHelper.setPrivateValue(Item.class, item, size, MAX_STACK_SIZSE_SRG_NAME);
//	}
//
//	public static void setItemDurability(Item item, int durability) {
//		ObfuscationReflectionHelper.setPrivateValue(Item.class, item, durability, MAX_DAMAGE_SRG_NAME);
//		setItemMaxStackSize(item, 1);
//	}
//
//	/**
//	 *
//	 * @param level
//	 * @return
//	 */
//	public static Optional<Path> getWorldSaveFolder(ServerLevel level) {
//		Object save = ObfuscationReflectionHelper.getPrivateValue(MinecraftServer.class, level.getServer(), SAVE_FORMAT_LEVEL_SAVE_SRG_NAME);
//		if (save instanceof LevelStorageSource.LevelStorageAccess) {
//			Path path = ((LevelStorageSource.LevelStorageAccess) save)
//					.getWorldDir().resolve(((LevelStorageSource.LevelStorageAccess) save).getLevelId())
//					.resolve("datapacks");
//			return Optional.of(path);
//		}
//		return Optional.empty();
//	}
//
//	/**
//	 *  Get all paths from a folder that inside the JAR file
//	 * @param jarPath
//	 * @param folder
//	 * @return
//	 * @throws URISyntaxException
//	 * @throws IOException
//	 */
//	public static  List<Path> getPathsFromResourceJAR(Path jarPath, String folder)
//			throws URISyntaxException, IOException {
//
//		List<Path> result;
//
//		/*
//		 * This block of code would be used if the jar file was unknown and it had to be discovered.
//		 *
//        // get path of the current running JAR
//        String jarPathOriginal = Treasure.class.getProtectionDomain()
//                .getCodeSource()
//                .getLocation()
//                .toURI()
//                .getPath();
//        Treasure.LOGGER.debug("JAR Path Original -> {}", jarPathOriginal);
//
//        // file walks JAR
//        URI uri = URI.create("jar:file:" + jarPath);
//		 */
//
//		try (FileSystem fs = FileSystems.newFileSystem(jarPath, Collections.emptyMap())) {
//			result = Files.walk(fs.getPath(folder))
//					.filter(Files::isRegularFile)
//					.collect(Collectors.toList());
//		}
//		return result;
//	}
//
//	/**
//	 *
//	 * @param fileName
//	 * @return
//	 */
//	public static InputStream getFileFromResourceAsStream(String fileName) {
//
//		// The class loader that loaded the class
//		ClassLoader classLoader = Treasure.class.getClassLoader();
//		InputStream inputStream = classLoader.getResourceAsStream(fileName);
//
//		// the stream holding the file content
//		if (inputStream == null) {
//			throw new IllegalArgumentException("file not found! " + fileName);
//		} else {
//			return inputStream;
//		}
//	}
//
//	/**
//	 *
//	 * @param path
//	 * @return
//	 * @throws IOException
//	 */
//	public static List<Path> getPathsFromFlatDatapacks(Path path) throws IOException {
//		List<Path> result;
//		try (Stream<Path> walk = Files.walk(path)) {
//			result = walk.filter(Files::isRegularFile)
//					.collect(Collectors.toList());
//		}
//		return result;
//	}
//

}
