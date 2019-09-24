/*
package dinocraft.capabilities.player;

import net.minecraft.nbt.NBTTagCompound;

public class DinocraftPlayerActions extends DinocraftPlayerModule
{
	public DinocraftPlayerActions(DinocraftPlayer player)
	{
		super(player);
	}
	
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
	
	protected boolean longJump;
    
	public boolean canLongJumpAgain()
	{
		return this.longJump;
	}

	public void setCanLongJumpAgain(boolean flag) 
	{
		this.longJump = flag;
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
	
	protected boolean extraMaxHealth2;
	
	public boolean hasExtraMaxHealth2()
	{
		return this.extraMaxHealth2;
	}
	
	public void setHasExtraMaxHealth2(boolean flag)
	{
		this.extraMaxHealth2 = flag;
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
	
	public void write(NBTTagCompound tag)
	{
		NBTTagCompound root = new NBTTagCompound();
		tag.setTag("Abilities", root);

		tag.setBoolean("doubleJump", this.doubleJump);
		tag.setBoolean("hasDoubleJumped", this.hasDoubleJumped);
		tag.setBoolean("jumpCooldown", this.longJump);
		tag.setBoolean("extraMaxHealth", this.extraMaxHealth);
		tag.setBoolean("extraMaxHealth2", this.extraMaxHealth2);
		tag.setInteger("chlorophyteTick", this.chlorophyteTick);
		tag.setFloat("chlorophyteAbsorptionAmount", this.chlorophyteAbsorptionAmount);
	}

	public void read(NBTTagCompound tag)
	{
		NBTTagCompound root = tag.getCompoundTag("Abilities");

		this.doubleJump = root.getBoolean("doubleJump");
		this.hasDoubleJumped = root.getBoolean("hasDoubleJumped");
		this.longJump = root.getBoolean("jumpCooldown");
		this.extraMaxHealth = root.getBoolean("extraMaxHealth");
		this.extraMaxHealth2 = root.getBoolean("extraMaxHealth2");
		this.chlorophyteTick = root.getInteger("chlorophyteTick");
		this.chlorophyteAbsorptionAmount = root.getFloat("chlorophyteAbsorptionAmount");
	}
}
*/