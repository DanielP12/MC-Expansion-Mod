package dinocraft.capabilities.entity;

import net.minecraft.nbt.NBTTagCompound;

public class DinocraftEntityTicks extends DinocraftEntityModule
{
	public DinocraftEntityTicks(DinocraftEntity entity)
	{
		super(entity);
	}

	protected int regenerationTicks = 0;
	protected float regenerationLoopTicks = 0.0F;
	protected float healthToRegenerate = 0.0F;
	protected int regenerationCount = 0;
	protected int ticksInvulnerable = 0;
	protected int jesterizedTicks = 0;
	protected int mesmerizedTicks = 0;
	protected int electrifiedTicks = 0;
	protected int fallingCrystalsTicks = 0;
	protected int ticksStandingStill = 0;
	public int jesterSpeedingTicks = 0;
	
	public int getTicksStandingStill()
	{
		return this.ticksStandingStill;
	}
	
	public void incrementTicksStandingStill()
	{
		this.ticksStandingStill++;
	}
	
	public void resetTicksStandingStill()
	{
		this.ticksStandingStill = 0;
	}

	public void setStandingStill()
	{
		this.ticksStandingStill = 60;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		NBTTagCompound root = new NBTTagCompound();
		tag.setTag("Ticks", root);
		root.setInteger("RegenTicks", this.regenerationTicks);
		root.setFloat("RegenLoopTicks", this.regenerationLoopTicks);
		root.setInteger("RegenCount", this.regenerationCount);
		root.setFloat("RegenHealth", this.healthToRegenerate);
		root.setInteger("TicksInvulnerable", this.ticksInvulnerable);

		root.setInteger("TicksStandingStill", this.ticksStandingStill);
		root.setInteger("MesmerizedTicks", this.mesmerizedTicks);
		root.setInteger("ElectrifiedTicks", this.electrifiedTicks);
		root.setInteger("JesterizedTicks", this.jesterizedTicks);
		root.setInteger("FallingCrystalsTicks", this.fallingCrystalsTicks);
		
		root.setInteger("JesterSpeedingTicks", this.jesterSpeedingTicks);
	}
	
	@Override
	public void read(NBTTagCompound tag)
	{
		NBTTagCompound root = tag.getCompoundTag("Ticks");
		this.regenerationTicks = root.getInteger("RegenTicks");
		this.regenerationLoopTicks = root.getFloat("RegenLoopTicks");
		this.regenerationCount = root.getInteger("RegenCount");
		this.healthToRegenerate = root.getFloat("RegenHealth");
		this.ticksInvulnerable = root.getInteger("TicksInvulnerable");
		
		this.ticksStandingStill = root.getInteger("TicksStandingStill");
		this.mesmerizedTicks = root.getInteger("MesmerizedTicks");
		this.electrifiedTicks = root.getInteger("ElectrifiedTicks");
		this.jesterizedTicks = root.getInteger("JesterizedTicks");
		this.fallingCrystalsTicks = root.getInteger("FallingCrystalsTicks");
		
		this.jesterSpeedingTicks = root.getInteger("JesterSpeedingTicks");
	}
}