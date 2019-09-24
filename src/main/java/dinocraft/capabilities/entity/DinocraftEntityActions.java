package dinocraft.capabilities.entity;

import net.minecraft.nbt.NBTTagCompound;

public class DinocraftEntityActions extends DinocraftEntityModule
{
	public DinocraftEntityActions(DinocraftEntity entity)
	{
		super(entity);
	}
	
	/* Double Jump */
	protected boolean hasDoubleJumped;
	protected boolean doubleJump;
                
	public boolean hasDoubleJumped()
	{
		return this.hasDoubleJumped;
	}

	public void setHasDoubleJumped(boolean doubleJumped) 
	{
		this.hasDoubleJumped = doubleJumped;
	}

	public boolean canDoubleJumpAgain()
	{
		return this.doubleJump;
	}

	public void setCanDoubleJumpAgain(boolean flag) 
	{
		this.doubleJump = flag;
	}
	
	protected boolean extraMaxHealth;
	
	public boolean hasExtraMaxHealth()
	{
		return this.extraMaxHealth;
	}
	
	public void setHasExtraMaxHealth(boolean flag)
	{
		this.extraMaxHealth = flag;
	}
	
	protected int chlorophyteTick = 0;
	
	public int getChlorophyteTick() 
	{
		return chlorophyteTick;
	}
	
	public void setChlorophyteTick(int chlorophyteTick)
	{
		this.chlorophyteTick = chlorophyteTick;
	}
	
	protected float chlorophyteAbsorptionAmount = 0.0F;
	
	public float getChlorophyteAbsorptionAmount() 
	{
		return chlorophyteAbsorptionAmount;
	}
	
	public void setChlorophyteAbsorptionAmount(float chlorophyteAbsorptionAmount) 
	{
		this.chlorophyteAbsorptionAmount = chlorophyteAbsorptionAmount;
	}
	
	@Override
	public void write(NBTTagCompound tag)
	{
		NBTTagCompound root = new NBTTagCompound();
		tag.setTag("Abilities", root);

		tag.setBoolean("doubleJump", this.doubleJump);
		tag.setBoolean("hasDoubleJumped", this.hasDoubleJumped);
		tag.setBoolean("extraMaxHealth", this.extraMaxHealth);
		tag.setInteger("chlorophyteTick", this.chlorophyteTick);
		tag.setFloat("chlorophyteAbsorptionAmount", this.chlorophyteAbsorptionAmount);
	}

	@Override
	public void read(NBTTagCompound tag)
	{
		NBTTagCompound root = tag.getCompoundTag("Abilities");

		this.doubleJump = root.getBoolean("doubleJump");
		this.hasDoubleJumped = root.getBoolean("hasDoubleJumped");
		this.extraMaxHealth = root.getBoolean("extraMaxHealth");
		this.chlorophyteTick = root.getInteger("chlorophyteTick");
		this.chlorophyteAbsorptionAmount = root.getFloat("chlorophyteAbsorptionAmount");
	}
}