package dinocraft.network;

import dinocraft.Reference;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
    
    private static int discriminant;

    /*
     * The integer is the ID of the message, the Side is the side this message will be handled (received) on!
     */
    public static void init() 
    {
        INSTANCE.registerMessage(PacketSpawnParticle.class, PacketSpawnParticle.class, discriminant++, Side.CLIENT);
        INSTANCE.registerMessage(MessageBoing.class, MessageBoing.class, discriminant++, Side.CLIENT);
        INSTANCE.registerMessage(MessageCapabilitiesClient.class, MessageCapabilitiesClient.class, discriminant++, Side.CLIENT);
        INSTANCE.registerMessage(MessagePing.class, MessagePing.class, discriminant++, Side.CLIENT);
        INSTANCE.registerMessage(CapFly.class, CapFly.class, discriminant++, Side.CLIENT);

        INSTANCE.registerMessage(MessageEffect.class, MessageEffect.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(MessageAbsorption.class, MessageAbsorption.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(MessageNoFall.class, MessageNoFall.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(MessageSpawnDeathParticle.class, MessageSpawnDeathParticle.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(MessageSpawnCloudParticle.class, MessageSpawnCloudParticle.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(MessageSpawnGoldParticle.class, MessageSpawnGoldParticle.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(PacketAchievements.class, PacketAchievements.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(PacketCapabilities.class, PacketCapabilities.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(MessageDoubleJump.class, MessageDoubleJump.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(MessageSaveAll.class, MessageSaveAll.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(MessageLCTNT.class, MessageLCTNT.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(MessageResis.class, MessageResis.class, discriminant++, Side.SERVER);
    }

    public static void sendToAll(IMessage message)
    {
        INSTANCE.sendToAll(message);
    }

    public static void sendTo(IMessage message, EntityPlayerMP player)
    {
        INSTANCE.sendTo(message, player);
    }

    public static void sendToAllAround(ParticleIntPacket message, World world, double distance)
    {
    	INSTANCE.sendToAllAround(message, message.getTargetPoint(world, distance));
    }

    public static void sendToAllAround(ParticleIntPacket message, World world)
    {
    	INSTANCE.sendToAllAround(message, message.getTargetPoint(world));
    }

    public static void sendToAllAround(ParticleDoublePacket message, World world)
    {
        INSTANCE.sendToAllAround(message, message.getTargetPoint(world));
    }

    public static void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point)
    {
        INSTANCE.sendToAllAround(message, point);
    }

    public static void sendToServer(IMessage message)
    {
        INSTANCE.sendToServer(message);
    }
}