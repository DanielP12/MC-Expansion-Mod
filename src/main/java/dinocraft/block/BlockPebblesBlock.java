package dinocraft.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dinocraft.init.DinocraftItems;
import dinocraft.util.server.DinocraftServer;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPebblesBlock extends Block
{
	public BlockPebblesBlock()
	{
		super(Material.ROCK);
		this.setHardness(3.0F);
		this.setResistance(5.0F);
		this.setSoundType(SoundType.METAL);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	private int amount;
	
	private void spawnSpiderTrap(World world, BlockPos pos, IBlockState state)
	{
		float f1 = pos.getX() + world.rand.nextFloat() * 0.5F + 0.25F;
		float f2 = pos.getY() + world.rand.nextFloat() * 0.1F;
		float f3 = pos.getZ() + world.rand.nextFloat() * 0.5F + 0.25F;
		
		if (world.rand.nextBoolean())
		{
			EntityCaveSpider spider = new EntityCaveSpider(world);
			spider.setPosition(f1, f2, f3);
			world.spawnEntity(spider);
		}
		else
		{
			for (int i = 0; i < 2; ++i)
			{
				EntitySilverfish silverfish = new EntitySilverfish(world);
				silverfish.setPosition(f1, f2, f3);
				world.spawnEntity(silverfish);
			}
		}
		
		for (int i = 0; i < 20; ++i)
		{
			DinocraftServer.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, true, world, pos.getX() + world.rand.nextFloat(),
					pos.getY() + world.rand.nextFloat(), pos.getZ() + world.rand.nextFloat(),
					(float) world.rand.nextGaussian() * 0.02F, (float) world.rand.nextGaussian() * 0.02F, (float) world.rand.nextGaussian() * 0.02F, 0);
		}

		world.setBlockState(pos, Blocks.WEB.getDefaultState());
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		int i = rand.nextInt(2000);
		this.amount = 1;
		
		if (i < 1)
		{
			//			return DinocraftItems.TUSKERS_AMULET; /* 1/2000 */
		}
		else if (i < 3)
		{
			return DinocraftItems.TUSKERS_GEMSTONE; /* 2/2000 */
		}
		else if (i < 6)
		{
			return DinocraftItems.BLEVENT_INGOT; /* 3/2000 */
		}
		else if (i < 9)
		{
			return DinocraftItems.LEAF; /* 3/2000 */
		}
		else if (i < 13)
		{
			return DinocraftItems.DREMONITE_INGOT; /* 4/2000 */
		}
		else if (i < 18)
		{
			//			return DinocraftItems.RANIUM; /* 5/2000 */ //TODO: CHANGE
		}
		else if (i < 23)
		{
			return Items.DIAMOND; /* 5/2000 */
		}
		else if (i < 29)
		{
			return Items.GOLD_INGOT; /* 7/2000 */
		}
		else if (i < 37)
		{
			this.amount = rand.nextInt(2) + 1;
			return Items.LEATHER; /* 8/2000 */
		}
		else if (i < 47)
		{
			return Items.IRON_INGOT; /* 10/2000 */
		}
		else if (i < 57)
		{
			this.amount = rand.nextInt(4) + 1;
			return Items.GOLD_NUGGET; /* 10/2000 */
		}
		else if (i < 73)
		{
			this.amount = rand.nextInt(3) + 1;
			return Items.IRON_NUGGET; /* 16/2000 */
		}
		else if (i < 93)
		{
			this.amount = rand.nextInt(2) + 1;
			return Items.FLINT; /* 20/2000 */
		}
		else if (i < 117)
		{
			return Items.COAL; /* 24/2000 */
		}
		else if (i < 200)
		{
			this.amount = rand.nextInt(1) + 1;
			return Item.getItemFromBlock(Blocks.GRAVEL); /* 93/2000 */
		}
		else if (i < 350)
		{
			return Item.getItemFromBlock(Blocks.DIRT); /* 200/2000 */
		}
		else if (i < 550)
		{
			this.amount = rand.nextInt(2) + 1;
			return Items.CLAY_BALL; /* 200/2000 */
		}
		else if (i < 700)
		{
			return Items.BONE; /* 150/2000 */
		}
		else if (i < 750)
		{
			return Items.AIR; /* 50/2000 */
		}
		else if (i < 1000)
		{
			this.amount = rand.nextInt(3) + 1;
			return Item.getItemFromBlock(Blocks.COBBLESTONE); /* 250/2000 */
		}

		return DinocraftItems.PEBBLES; /* 1000/2000 */
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		Random rand = world instanceof World ? ((World) world).rand : new Random();
		int count = this.quantityDropped(state, fortune, rand);
		List<ItemStack> drops = new ArrayList<>();
		
		for (int i = 0; i < count; ++i)
		{
			Item item = this.getItemDropped(state, rand, fortune);

			if (item != null)
			{
				if (item == Items.AIR && world instanceof World)
				{
					this.spawnSpiderTrap((World) world, pos, state);
				}
				else
				{
					drops.add(new ItemStack(item, this.amount));
				}
			}
		}
		
		return drops;
	}
}