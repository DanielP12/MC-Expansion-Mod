package dinocraft.test;

import java.util.List;

import com.google.common.base.Predicates;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class MouseOver
{
	private static RayTraceResult getMouseOver(double distance)
	{
		Minecraft minecraft = Minecraft.getMinecraft();
		Entity entity = minecraft.getRenderViewEntity();
		RayTraceResult result = entity.rayTrace(distance, 0.0F);
		Vec3d pos = entity.getPositionEyes(0.0F);
		double calcdist = distance;

		if (result != null)
		{
			calcdist = result.hitVec.distanceTo(pos);
		}

		
		Vec3d lookvec = entity.getLook(0.0F);
		Vec3d var8 = pos.addVector(lookvec.x * distance, lookvec.y * distance, lookvec.z * distance);
		Entity pointedEntity = null;
		List<Entity> list = minecraft.world.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(lookvec.x * distance, lookvec.y * distance, lookvec.z * distance).grow(1.0F, 1.0F, 1.0F),
				Predicates.and(EntitySelectors.NOT_SPECTATING, entity1 -> entity1 != null && entity1.canBeCollidedWith()));
		double d = calcdist;

		for (Entity entity1 : list)
		{
			AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(entity1.getCollisionBorderSize());
			RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(pos, var8);
			
			if (axisalignedbb.contains(pos))
			{
				if (d >= 0.0D || d == 0.0D)
				{
					pointedEntity = entity1;
					d = 0.0D; }
			}
			else if (raytraceresult != null)
			{
				double d1 = pos.distanceTo(raytraceresult.hitVec);
				
				if (d1 < d || d == 0.0D)
				{
					pointedEntity = entity1;
					d = d1;
				}
			}
		}
		
		if (pointedEntity != null && (d < calcdist || result == null))
		{
			result = new RayTraceResult(pointedEntity);
		}
		
		return result;
	}
}
