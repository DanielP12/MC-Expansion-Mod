//package dinocraft.block;
//
//import java.util.Random;
//
//import javax.annotation.Nullable;
//
//import dinocraft.init.DinocraftItems;
//import net.minecraft.block.Block;
//import net.minecraft.block.material.Material;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.entity.Entity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.world.IBlockAccess;
//import net.minecraft.world.World;
//
//public class BlockFlareOre extends Block
//{
//	public BlockFlareOre()
//	{
//		super(Material.ROCK);
//		this.setHardness(4.0F);
//		this.setResistance(15.0F);
//	}
//
//	@Override
//	@Nullable
//	public Item getItemDropped(IBlockState state, Random rand, int fortune)
//	{
//		return DinocraftItems.FLARE_MAGMA;
//	}
//
//	@Override
//	public int quantityDropped(IBlockState state, int fortune, Random random)
//	{
//		return random.nextInt(4) + 2;
//	}
//
//	@Override
//	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
//	{
//		entity.setFire(5);
//		super.onEntityCollidedWithBlock(world, pos, state, entity);
//	}
//
//	@Override
//	public void onEntityWalk(World world, BlockPos pos, Entity entity)
//	{
//		entity.setFire(5);
//		super.onEntityWalk(world, pos, entity);
//	}
//
//	@Override
//	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
//	{
//		Random rand = world instanceof World ? ((World) world).rand : new Random();
//
//		if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
//		{
//			return MathHelper.getInt(rand, 3, 7);
//		}
//
//		return 0;
//	}
//
//	@Override
//	public ItemStack getItem(World world, BlockPos pos, IBlockState state)
//	{
//		return new ItemStack(this);
//	}
//}