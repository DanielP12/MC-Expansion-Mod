package dinocraft.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VectorHelper
{
	public static final VectorHelper ZERO = new VectorHelper(0.0D, 0.0D, 0.0D);
	public static final VectorHelper ONE = new VectorHelper(1.0D, 1.0D, 1.0D);
	public static final VectorHelper CENTER = new VectorHelper(0.5D, 0.5D, 0.5D);

	public final double x;
	public final double y;
	public final double z;

	public VectorHelper(double d, double d1, double d2)
	{
		this.x = d;
		this.y = d1;
		this.z = d2;
	}

	public VectorHelper(Vec3d vec)
	{
		this(vec.x, vec.y, vec.z);
	}

	public static VectorHelper fromBlockPos(BlockPos pos)
	{
		return new VectorHelper(pos.getX(), pos.getY(), pos.getZ());
	}

	public static VectorHelper fromEntity(Entity entity)
	{
		return new VectorHelper(entity.posX, entity.posY, entity.posZ);
	}

	public static VectorHelper fromEntityCenter(Entity entity)
	{
		return new VectorHelper(entity.posX, entity.posY - entity.getYOffset() + entity.height / 2.0F, entity.posZ);
	}

	public static VectorHelper fromTileEntity(TileEntity tileEntity)
	{
		return fromBlockPos(tileEntity.getPos());
	}

	public static VectorHelper fromTileEntityCenter(TileEntity tileEntity)
	{
		return fromTileEntity(tileEntity).add(0.5D);
	}

	public double dotProduct(VectorHelper vec)
	{
		double d = vec.x * this.x + vec.y * this.y + vec.z * this.z;
		return d > 1.0D && d < 1.00001D ? 1.0D : d < -1.0D && d > -1.00001D ? -1.0D : d;
	}

	public double dotProduct(double d, double d1, double d2)
	{
		return d * this.x + d1 * this.y + d2 * this.z;
	}

	public VectorHelper crossProduct(VectorHelper vec)
	{
		double d = this.y * vec.z - this.z * vec.y;
		double d1 = this.z * vec.x - this.x * vec.z;
		double d2 = this.x * vec.y - this.y * vec.x;
		return new VectorHelper(d, d1, d2);
	}

	public VectorHelper add(double d, double d1, double d2)
	{
		return new VectorHelper(this.x + d, this.y + d1, this.z + d2);
	}

	public VectorHelper add(VectorHelper vec)
	{
		return add(vec.x, vec.y, vec.z);
	}

	public VectorHelper add(double d) 
	{
		return add(d, d, d);
	}

	public VectorHelper subtract(VectorHelper vec) 
	{
		return new VectorHelper(this.x - vec.x, this.y - vec.y, this.z - vec.z);
	}

	public VectorHelper multiply(double d) 
	{
		return multiply(d, d, d);
	}

	public VectorHelper multiply(VectorHelper f)
	{
		return multiply(f.x, f.y, f.z);
	}

	public VectorHelper multiply(double fx, double fy, double fz) 
	{
		return new VectorHelper(this.x * fx, this.y * fy, this.z * fz);
	}

	public double mag()
	{
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}

	public double magSquared() 
	{
		return this.x * this.x + this.y * this.y + this.z * this.z;
	}

	public VectorHelper normalize() 
	{
		double d = mag();
		return d != 0 ? this.multiply(1.0D / d) : this;
	}

	@Override
	public String toString()
	{
		MathContext cont = new MathContext(4, RoundingMode.HALF_UP);
		return "VectorHelper(" + new BigDecimal(this.x, cont) + ", " + new BigDecimal(this.y, cont) + ", " + new BigDecimal(this.z, cont) + ")";
	}

	public VectorHelper perpendicular() 
	{
		return this.z == 0.0D ? this.zCrossProduct() : this.xCrossProduct();
	}

	public VectorHelper xCrossProduct() 
	{
		return new VectorHelper(0.0D, this.z, -this.y);
	}

	public VectorHelper zCrossProduct() 
	{
		return new VectorHelper(this.y, -this.x, 0.0D);
	}

	public VectorHelper yCrossProduct() 
	{
		return new VectorHelper(-this.z, 0.0D, this.x);
	}

	public Vec3d toVec3D() 
	{
		return new Vec3d(this.x, this.y, this.z);
	}

	public double angle(VectorHelper vec) 
	{
		return Math.acos(normalize().dotProduct(vec.normalize()));
	}

	public boolean isInside(AxisAlignedBB aabb) 
	{
		return this.x >= aabb.minX && this.y >= aabb.maxY && this.z >= aabb.minZ && this.x < aabb.maxX && this.y < aabb.maxY && this.z < aabb.maxZ;
	}

	public boolean isZero() 
	{
		return this.x == 0.0D && this.y == 0.0D && this.z == 0.0D;
	}

	public boolean isAxial()
	{
		return this.x == 0.0D ? this.y == 0.0D || this.z == 0.0D : this.y == 0.0D && this.z == 0.0D;
	}

	public Vector3f VectorHelperf() 
	{
		return new Vector3f((float) this.x, (float) this.y, (float) this.z);
	}

	public Vector4f vector4f() 
	{
		return new Vector4f((float) this.x, (float) this.y, (float) this.z, 1.0F);
	}

	@SideOnly(Side.CLIENT)
	public void glVertex() 
	{
		GL11.glVertex3d(this.x, this.y, this.z);
	}

	public VectorHelper negate() 
	{
		return new VectorHelper(-this.x, -this.y, -this.z);
	}

	public double scalarProject(VectorHelper b) 
	{
		double l = b.mag();
		return l == 0 ? 0 : dotProduct(b) / l;
	}

	public VectorHelper project(VectorHelper b) 
	{
		double l = b.magSquared();
		return l == 0.0D ? this.ZERO : b.multiply(this.dotProduct(b) / l);
	}

	public VectorHelper rotate(double angle, VectorHelper axis)
	{
		return Quat.aroundAxis(axis.normalize(), angle).rotate(this);
	}

	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof VectorHelper))
		{
			return false;
		}
		
		VectorHelper v = (VectorHelper) o;
		return this.x == v.x && this.y == v.y && this.z == v.z;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.x, this.y, this.z);
	}
}