package dinocraft.network;

import dinocraft.Dinocraft;
import dinocraft.network.client.CPacketChangeCapability;
import dinocraft.network.client.CPacketDisplayContainer;
import dinocraft.network.client.CPacketDoubleJump;
import dinocraft.network.client.CPacketDreadedFlight;
import dinocraft.network.client.CPacketEntityEffect;
import dinocraft.network.client.CPacketExecuteCommand;
import dinocraft.network.client.CPacketFallingCrystalEntity;
import dinocraft.network.client.CPacketJestersDash;
import dinocraft.network.client.CPacketPlaySound;
import dinocraft.network.client.CPacketReachAttack;
import dinocraft.network.client.CPacketRegenerate;
import dinocraft.network.client.CPacketRunCommand;
import dinocraft.network.client.CPacketSpawnParticle;
import dinocraft.network.client.CPacketStandingStill;
import dinocraft.network.client.CPacketStrikeEffect;
import dinocraft.network.client.CPacketThunderJump;
import dinocraft.network.server.SPacketAllowFlying;
import dinocraft.network.server.SPacketChangeCapability;
import dinocraft.network.server.SPacketChatMsgAsPlayer;
import dinocraft.network.server.SPacketClearChat;
import dinocraft.network.server.SPacketCrashPlayer;
import dinocraft.network.server.SPacketDreadedParticles;
import dinocraft.network.server.SPacketElectricParticles;
import dinocraft.network.server.SPacketElectricParticlesConnection;
import dinocraft.network.server.SPacketFlySpeed;
import dinocraft.network.server.SPacketItemPickupEffect;
import dinocraft.network.server.SPacketJesterKnockback;
import dinocraft.network.server.SPacketJesterParticles;
import dinocraft.network.server.SPacketMesmerizedParticles;
import dinocraft.network.server.SPacketRecoilPlayer;
import dinocraft.network.server.SPacketSpawnParticle;
import dinocraft.network.server.SPacketSpawnParticles;
import dinocraft.network.server.SPacketStrikeEntity;
import dinocraft.network.server.SPacketTag;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Dinocraft.MODID);
	
	public static void init()
	{
		int discriminator = 0;
		INSTANCE.registerMessage(SPacketSpawnParticle.class, SPacketSpawnParticle.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketSpawnParticles.class, SPacketSpawnParticles.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketJesterParticles.class, SPacketJesterParticles.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketElectricParticles.class, SPacketElectricParticles.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketElectricParticlesConnection.class, SPacketElectricParticlesConnection.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketDreadedParticles.class, SPacketDreadedParticles.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketMesmerizedParticles.class, SPacketMesmerizedParticles.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketChangeCapability.class, SPacketChangeCapability.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketAllowFlying.class, SPacketAllowFlying.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketClearChat.class, SPacketClearChat.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketCrashPlayer.class, SPacketCrashPlayer.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketRecoilPlayer.class, SPacketRecoilPlayer.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketItemPickupEffect.class, SPacketItemPickupEffect.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketStrikeEntity.class, SPacketStrikeEntity.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketChatMsgAsPlayer.class, SPacketChatMsgAsPlayer.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketFlySpeed.class, SPacketFlySpeed.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketTag.class, SPacketTag.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(SPacketJesterKnockback.class, SPacketJesterKnockback.class, discriminator++, Side.CLIENT);

		INSTANCE.registerMessage(CPacketEntityEffect.class, CPacketEntityEffect.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketSpawnParticle.class, CPacketSpawnParticle.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketPlaySound.class, CPacketPlaySound.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketChangeCapability.class, CPacketChangeCapability.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketDoubleJump.class, CPacketDoubleJump.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketThunderJump.class, CPacketThunderJump.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketJestersDash.class, CPacketJestersDash.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketDreadedFlight.class, CPacketDreadedFlight.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketFallingCrystalEntity.class, CPacketFallingCrystalEntity.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketRegenerate.class, CPacketRegenerate.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketStrikeEffect.class, CPacketStrikeEffect.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketReachAttack.class, CPacketReachAttack.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketExecuteCommand.class, CPacketExecuteCommand.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketRunCommand.class, CPacketRunCommand.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketDisplayContainer.class, CPacketDisplayContainer.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(CPacketStandingStill.class, CPacketStandingStill.class, discriminator++, Side.SERVER);
	}

	public static void sendToAll(IMessage message)
	{
		INSTANCE.sendToAll(message);
	}
	
	public static void sendTo(IMessage message, EntityPlayerMP player)
	{
		INSTANCE.sendTo(message, player);
	}
	
	public static void sendToAllAround(ParticleFloatPacket message, World world, double range)
	{
		INSTANCE.sendToAllAround(message, message.getTargetPoint(world, range));
	}

	public static void sendToAllAround(ParticlePositionPacket message, World world, double range)
	{
		INSTANCE.sendToAllAround(message, message.getTargetPoint(world, range));
	}
	
	public static void sendToAllAround(IMessage message, TargetPoint point)
	{
		INSTANCE.sendToAllAround(message, point);
	}

	public static void sendToServer(IMessage message)
	{
		INSTANCE.sendToServer(message);
	}
}