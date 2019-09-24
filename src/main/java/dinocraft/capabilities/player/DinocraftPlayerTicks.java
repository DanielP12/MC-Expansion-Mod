/*
package dinocraft.capabilities.player;

import net.minecraft.nbt.NBTTagCompound;

public class DinocraftPlayerTicks extends DinocraftPlayerModule
{
	public DinocraftPlayerTicks(DinocraftPlayer player)
	{
		super(player);
	}
		
	protected int regenerationTicks = 0;
	protected double regenerationLoopTicks = 0.0D;
	protected float heartsRegenerate = 0.0F;
		
	protected int getRegenerationTicks()
	{
		return this.regenerationTicks;
	}
	
	protected double getRegenerationLoopTicks()
	{
		return this.regenerationLoopTicks;
	}
	
	protected float getRegenerationHearts()
	{
		return this.heartsRegenerate;
	}
	
	protected void setRegenerationTicks(int regenerationTicks)
	{
		this.regenerationTicks = regenerationTicks;
	}
	
	protected void setRegenerationLoopTicks(double regenerationLoopTicks)
	{
		this.regenerationLoopTicks = regenerationLoopTicks;
	}
	
	protected void setRegenerationHearts(float heartsRegenerate)
	{
		this.heartsRegenerate = heartsRegenerate;
	}
	
	protected int regenerationCount = 0;
	
	protected int getRegenerationCount()
	{
		return this.regenerationCount;
	}
	
	protected void setRegenerationCount(int count)
	{
		this.regenerationCount = count;
	}
	
	protected int degenerationTicks = 0;
	protected double degenerationLoopTicks = 0.0D;
	protected float heartsDegenerate = 0.0F;
		
	protected int getDegenerationTicks()
	{
		return this.degenerationTicks;
	}
	
	protected double getDegenerationLoopTicks()
	{
		return this.degenerationLoopTicks;
	}
	
	protected float getDegenerationHearts()
	{
		return this.heartsDegenerate;
	}
	
	protected void setDegenerationTicks(int degenerationTicks)
	{
		this.degenerationTicks = degenerationTicks;
	}
	
	protected void setDegenerationLoopTicks(double degenerationLoopTicks)
	{
		this.degenerationLoopTicks = degenerationLoopTicks;
	}
	
	protected void setDegenerationHearts(float heartsDegenerate)
	{
		this.heartsDegenerate = heartsDegenerate;
	}
	
	protected int degenerationCount = 0;
	
	protected int getDegenerationCount()
	{
		return this.degenerationCount;
	}
	
	protected void setDegenerationCount(int count)
	{
		this.degenerationCount = count;
	}
	
	protected int ticksInvulnerable;
	
	protected int getTicksInvulnerable()
	{
		return this.ticksInvulnerable;
	}
 
	protected void setTicksInvulnerable(int ticksInvulnerable)
	{
		this.ticksInvulnerable = ticksInvulnerable;
	}
	
	public void write(NBTTagCompound tag)
	{
		NBTTagCompound root = new NBTTagCompound();
		tag.setTag("Ticks", root);

		tag.setDouble("Regeneration Loop Ticks", this.regenerationLoopTicks);
		tag.setInteger("Regeneration Ticks", this.regenerationTicks);
		tag.setInteger("Regeneration Count", this.regenerationCount);
		tag.setFloat("Regeneration Hearts", this.heartsRegenerate);
		tag.setInteger("Ticks Invulnerable", this.ticksInvulnerable);
		tag.setDouble("Degeneration Loop Ticks", this.degenerationLoopTicks);
		tag.setInteger("Degeneration Ticks", this.degenerationTicks);
		tag.setInteger("Degeneration Count", this.degenerationCount);
		tag.setFloat("Degeneration Hearts", this.heartsDegenerate);
	}

	public void read(NBTTagCompound tag)
	{
		NBTTagCompound root = tag.getCompoundTag("Ticks");

		this.regenerationTicks = root.getInteger("Regeneration Ticks");
		this.regenerationLoopTicks = root.getInteger("Regeneration Loop Ticks");
		this.regenerationCount = root.getInteger("Regeneration Count");
		this.heartsRegenerate = root.getFloat("Regeneration Hearts");
		this.ticksInvulnerable = root.getInteger("Ticks Invulnerable");
		this.degenerationTicks = root.getInteger("Degeneration Ticks");
		this.degenerationLoopTicks = root.getDouble("Degeneration Loop Ticks");
		this.degenerationCount = root.getInteger("Degeneration Count");
		this.heartsDegenerate = root.getFloat("Degeneration Hearts");
	}
}
*/