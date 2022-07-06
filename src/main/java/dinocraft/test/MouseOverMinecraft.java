package dinocraft.test;

import java.util.List;

import com.google.common.base.Predicates;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class MouseOverMinecraft
{
	private static RayTraceResult getMouseOverMinecraft(double distance)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		Entity entity = minecraft.getRenderViewEntity();
		RayTraceResult result = entity.rayTrace(distance, 0.0F);
		minecraft.pointedEntity = null;
		Vec3d vec3d = entity.getPositionEyes(0.0F);
		Vec3d vec3d1 = entity.getLook(0.0F);
		Vec3d vec3d2 = vec3d.addVector(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);
		Vec3d vec3d3 = null;
		Entity pointedEntity = null;
		List<Entity> list = minecraft.world.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance)
				.grow(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, entity1 -> entity1 != null && entity1.canBeCollidedWith()));
		double d0 = distance;

		for (Entity entity1 : list)
		{
			AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(entity1.getCollisionBorderSize());
			RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d2);
			
			if (axisalignedbb.contains(vec3d))
			{
				if (d0 >= 0.0D)
				{
					pointedEntity = entity1;
					vec3d3 = raytraceresult == null ? vec3d : raytraceresult.hitVec;
					d0 = 0.0D;
				}
			}
			else if (raytraceresult != null)
			{
				double d1 = vec3d.distanceTo(raytraceresult.hitVec);
				
				if (d1 < d0 || d0 == 0.0D)
				{
					if (entity1.getLowestRidingEntity() == entity.getLowestRidingEntity() && !entity1.canRiderInteract())
					{
						if (d0 == 0.0D)
						{
							pointedEntity = entity1;
							vec3d3 = raytraceresult.hitVec;
						}
					}
					else
					{
						pointedEntity = entity1;
						vec3d3 = raytraceresult.hitVec;
						d0 = d1;
					}
				}
			}
		}
		
		if (pointedEntity != null && vec3d.distanceTo(vec3d3) > distance)
		{
			pointedEntity = null;
			result = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, (EnumFacing)null, new BlockPos(vec3d3));
		}

		if (pointedEntity != null && (d0 < distance || minecraft.objectMouseOver == null))
		{
			result = new RayTraceResult(pointedEntity, vec3d3);
		}

		return result;
	}
}
