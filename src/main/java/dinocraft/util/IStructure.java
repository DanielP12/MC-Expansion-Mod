package dinocraft.util;

import net.minecraft.util.Mirror;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraftforge.fml.common.FMLCommonHandler;

public interface IStructure
{
	WorldServer WORLD_SERVER = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0 /* overworld */);
	PlacementSettings SETTINGS = new PlacementSettings().setChunk(null).setIgnoreEntities(false).setIgnoreStructureBlock(false).setMirror(Mirror.NONE);
}
