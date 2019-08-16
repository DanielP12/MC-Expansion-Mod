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
	public static final VectorHelper ZERO = new VectorHelper(0, 0, 0);
	public static final VectorHelper ONE = new VectorHelper(1, 1, 1);
	public static final VectorHelper CENTER = new VectorHelper(0.5, 0.5, 0.5);

	public final double x;
	public final double y;
	public final double z;

	public VectorHelper(double d, double d1, double d2) {
		x = d;
		y = d1;
		z = d2;
	}

	public VectorHelper(Vec3d vec) {
		this(vec.x, vec.y, vec.z);
	}

	public static VectorHelper fromBlockPos(BlockPos pos) {
		return new VectorHelper(pos.getX(), pos.getY(), pos.getZ());
	}

	public static VectorHelper fromEntity(Entity e) {
		return new VectorHelper(e.posX, e.posY, e.posZ);
	}

	public static VectorHelper fromEntityCenter(Entity e) {
		return new VectorHelper(e.posX, e.posY - e.getYOffset() + e.height / 2, e.posZ);
	}

	public static VectorHelper fromTileEntity(TileEntity e) {
		return fromBlockPos(e.getPos());
	}

	public static VectorHelper fromTileEntityCenter(TileEntity e) {
		return fromTileEntity(e).add(0.5);
	}

	public double dotProduct(VectorHelper vec) {
		double d = vec.x * x + vec.y * y + vec.z * z;

		if(d > 1 && d < 1.00001)
			d = 1;
		else if(d < -1 && d > -1.00001)
			d = -1;
		return d;
	}

	public double dotProduct(double d, double d1, double d2) {
		return d * x + d1 * y + d2 * z;
	}

	public VectorHelper crossProduct(VectorHelper vec) {
		double d = y * vec.z - z * vec.y;
		double d1 = z * vec.x - x * vec.z;
		double d2 = x * vec.y - y * vec.x;
		return new VectorHelper(d, d1, d2);
	}

	public VectorHelper add(double d, double d1, double d2) {
		return new VectorHelper(x + d, y + d1, z + d2);
	}

	public VectorHelper add(VectorHelper vec) {
		return add(vec.x, vec.y, vec.z);
	}

	public VectorHelper add(double d) {
		return add(d, d, d);
	}

	public VectorHelper subtract(VectorHelper vec) {
		return new VectorHelper(x - vec.x, y - vec.y, z - vec.z);
	}

	public VectorHelper multiply(double d) {
		return multiply(d, d, d);
	}

	public VectorHelper multiply(VectorHelper f) {
		return multiply(f.x, f.y, f.z);
	}

	public VectorHelper multiply(double fx, double fy, double fz) {
		return new VectorHelper(x * fx, y * fy, z * fz);
	}

	public double mag() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public double magSquared() {
		return x * x + y * y + z * z;
	}

	public VectorHelper normalize() {
		double d = mag();
		if(d != 0)
			return multiply(1 / d);

		return this;
	}

	@Override
	public String toString() {
		MathContext cont = new MathContext(4, RoundingMode.HALF_UP);
		return "VectorHelper(" + new BigDecimal(x, cont) + ", " +new BigDecimal(y, cont) + ", " + new BigDecimal(z, cont) + ")";
	}

	public VectorHelper perpendicular() {
		if(z == 0)
			return zCrossProduct();
		return xCrossProduct();
	}

	public VectorHelper xCrossProduct() {
		double d = z;
		double d1 = -y;
		return new VectorHelper(0, d, d1);
	}

	public VectorHelper zCrossProduct() {
		double d = y;
		double d1 = -x;
		return new VectorHelper(d, d1, 0);
	}

	public VectorHelper yCrossProduct() {
		double d = -z;
		double d1 = x;
		return new VectorHelper(d, 0, d1);
	}

	public Vec3d toVec3D() {
		return new Vec3d(x, y, z);
	}

	public double angle(VectorHelper vec) {
		return Math.acos(normalize().dotProduct(vec.normalize()));
	}

	public boolean isInside(AxisAlignedBB aabb) {
		return x >= aabb.minX && y >= aabb.maxY && z >= aabb.minZ && x < aabb.maxX && y < aabb.maxY && z < aabb.maxZ;
	}

	public boolean isZero() {
		return x == 0 && y == 0 && z == 0;
	}

	public boolean isAxial() {
		return x == 0 ? y == 0 || z == 0 : y == 0 && z == 0;
	}

	public Vector3f VectorHelperf() {
		return new Vector3f((float)x, (float)y, (float)z);
	}

	public Vector4f vector4f() {
		return new Vector4f((float)x, (float)y, (float)z, 1);
	}

	@SideOnly(Side.CLIENT)
	public void glVertex() {
		GL11.glVertex3d(x, y, z);
	}

	public VectorHelper negate() {
		return new VectorHelper(-x, -y, -z);
	}

	public double scalarProject(VectorHelper b) {
		double l = b.mag();
		return l == 0 ? 0 : dotProduct(b)/l;
	}

	public VectorHelper project(VectorHelper b) {
		double l = b.magSquared();
		if(l == 0) {
			return ZERO;
		}

		double m = dotProduct(b)/l;
		return b.multiply(m);
	}

	public VectorHelper rotate(double angle, VectorHelper axis) {
		return Quat.aroundAxis(axis.normalize(), angle).rotate(this);
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof VectorHelper))
			return false;

		VectorHelper v = (VectorHelper)o;
		return x == v.x && y == v.y && z == v.z;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}
}
