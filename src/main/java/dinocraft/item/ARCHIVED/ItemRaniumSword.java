package dinocraft.item.ARCHIVED;
//package dinocraft.item.archived;
//
//import dinocraft.capabilities.entity.DinocraftEntity;
//import dinocraft.util.VectorHelper;
//import dinocraft.util.server.DinocraftServer;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.ItemSword;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.EnumParticleTypes;
//import net.minecraft.util.math.RayTraceResult;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.World;
//
//public class ItemRaniumSword extends ItemSword
//{
//	public ItemRaniumSword(ToolMaterial material)
//	{
//		super(material);
//	}
//
//	@Override
//	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
//	{
//		if (!world.isRemote)
//		{
//			RayTraceResult result = DinocraftEntity.getBlockTraceNearest(player, 1024.0D);
//
//			if (result != null)
//			{
//				Vec3d vec1 = result.hitVec;
//				VectorHelper vec = VectorHelper.fromEntityCenter(player).subtract(VectorHelper.fromVector(vec1)).normalize();
//
//				for (int i = 0; i <= (int) vec1.distanceTo(new Vec3d(player.posX, player.posY + 0.5D, player.posZ)); i++)
//				{
//					DinocraftServer.spawnParticle(EnumParticleTypes.END_ROD, true, world, vec1.x + vec.x * i,
//							vec1.y + vec.y * i, vec1.z + vec.z * i,
//							0, 0, 0, 0);
//				}
//			}
//		}
//
//		return super.onItemRightClick(world, player, hand);
//	}
//
//	@Override
//	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
//	{
//		if (!target.world.isRemote)
//		{
//
//			/*
//			for (int i = 0; i < 100; i++)
//			{
//				int j = target.world.rand.nextInt(3);
//
//				if (j == 0)
//				{
//					DinocraftServer.spawnParticle(EnumParticleTypes.SPELL_MOB, true, target.world, target.posX + target.world.rand.nextFloat() * target.width * 2.0F - target.width,
//							target.posY + target.world.rand.nextFloat() * target.height, target.posZ + target.world.rand.nextFloat() * target.width * 2.0F - target.width,
//							200, 200, 50, 0);
//				}
//				else if (j == 1)
//				{
//					DinocraftServer.spawnParticle(EnumParticleTypes.SPELL_MOB, true, target.world, target.posX + target.world.rand.nextFloat() * target.width * 2.0F - target.width,
//							target.posY + target.world.rand.nextFloat() * target.height, target.posZ + target.world.rand.nextFloat() * target.width * 2.0F - target.width,
//							150, 80, 20, 0);
//				}
//				else
//				{
//					DinocraftServer.spawnParticle(EnumParticleTypes.SPELL_MOB, true, target.world, target.posX + target.world.rand.nextFloat() * target.width * 2.0F - target.width,
//							target.posY + target.world.rand.nextFloat() * target.height, target.posZ + target.world.rand.nextFloat() * target.width * 2.0F - target.width,
//							200, 120, 1, 0);
//				}
//			}
//
//			for (int i = 0; i < 10; i++)
//			{
//				DinocraftServer.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, true, target.world, target.posX + target.world.rand.nextFloat() * target.width * 2.0F - target.width,
//						target.posY + target.world.rand.nextFloat() * target.height, target.posZ + target.world.rand.nextFloat() * target.width * 2.0F - target.width,
//						target.world.rand.nextGaussian() * 0.02D, target.world.rand.nextGaussian() * 0.02D, target.world.rand.nextGaussian() * 0.02D, 0);
//			}
//
//			for (int i = 0; i < 10; i++)
//			{
//				DinocraftServer.spawnParticle(EnumParticleTypes.END_ROD, true, target.world, target.posX + target.world.rand.nextFloat() * target.width * 2.0F - target.width,
//						target.posY + target.world.rand.nextFloat() * target.height, target.posZ + target.world.rand.nextFloat() * target.width * 2.0F - target.width,
//						target.world.rand.nextGaussian() * 0.02D, target.world.rand.nextGaussian() * 0.02D, target.world.rand.nextGaussian() * 0.02D, 0);
//			}
//			 */
//		}
//
//		//		if (attacker instanceof EntityPlayer)
//		//		{
//		//			((EntityPlayer) attacker).getCooldownTracker().setCooldown(this, 400);
//		//		}
//		//
//		//		attacker.world.playSound(null, attacker.getPosition(), SoundEvents.BLOCK_NOTE_BELL, SoundCategory.NEUTRAL, 1.0F, attacker.world.rand.nextFloat());
//		return super.hitEntity(stack, target, attacker);
//	}
//}