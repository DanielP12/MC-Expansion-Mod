package dinocraft.network;

import dinocraft.Reference;
import dinocraft.network.reach.MessageExtendedReachAttack;
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
        INSTANCE.registerMessage(MessageBounce.class, MessageBounce.class, discriminant++, Side.CLIENT);
        INSTANCE.registerMessage(MessageCapabilitiesClient.class, MessageCapabilitiesClient.class, discriminant++, Side.CLIENT);
        INSTANCE.registerMessage(PacketFly.class, PacketFly.class, discriminant++, Side.CLIENT);
        INSTANCE.registerMessage(MessageReach.class, MessageReach.class, discriminant++, Side.CLIENT);
        INSTANCE.registerMessage(MessageClearChat.class, MessageClearChat.class, discriminant++, Side.CLIENT);

        INSTANCE.registerMessage(MessageEffect.class, MessageEffect.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(MessageSpawnCloudParticle.class, MessageSpawnCloudParticle.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(PacketCapabilities.class, PacketCapabilities.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(MessageDoubleJump.class, MessageDoubleJump.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(MessageSaveAll.class, MessageSaveAll.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(MessageExtendedReachAttack.Handler.class, MessageExtendedReachAttack.class, discriminant++, Side.SERVER);
        INSTANCE.registerMessage(MessageRegenerate.class, MessageRegenerate.class, discriminant++, Side.SERVER);
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