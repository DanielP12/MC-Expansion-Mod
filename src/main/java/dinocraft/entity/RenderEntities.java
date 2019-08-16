package dinocraft.entity;

import dinocraft.Reference;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntities extends RenderEntity 
{
	public RenderEntities(RenderManager render)
	{
	    super(render);
	}

	public static final ResourceLocation VINE_BALL_TEXTURE = new ResourceLocation(Reference.MODID + ":textures/items/vine_ball.png");
	public static final ResourceLocation POISON_BALL_TEXTURE = new ResourceLocation(Reference.MODID + ":textures/items/poison_ball.png");
	public static final ResourceLocation RAY_BULLET_TEXTURE = new ResourceLocation(Reference.MODID + ":textures/items/ray_bullet.png");
	public static final ResourceLocation SEED_TEXTURE = new ResourceLocation(Reference.MODID + ":textures/items/seed.png");
	public static final ResourceLocation LEAFERANG_TEXTURE = new ResourceLocation(Reference.MODID + ":textures/items/leaferang.png");
}