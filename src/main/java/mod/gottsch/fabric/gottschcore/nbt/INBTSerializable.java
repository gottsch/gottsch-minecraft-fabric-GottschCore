package mod.gottsch.fabric.gottschcore.nbt;

import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryWrapper;

/**
 * Fabric stand-in for NeoForge's INBTSerializable, with the same method names so payload classes
 * written for NeoForge GottschCore port with an import change. Used by CoordsIntervalTree.NBTSerializer.
 *
 * @author Mark Gottschling on Oct 6, 2026
 */
public interface INBTSerializable<T extends NbtElement> {

    T serializeNBT(RegistryWrapper.WrapperLookup provider);

    void deserializeNBT(RegistryWrapper.WrapperLookup provider, T nbt);
}
