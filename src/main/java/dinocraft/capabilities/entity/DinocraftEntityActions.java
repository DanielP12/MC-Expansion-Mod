package dinocraft.capabilities.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class DinocraftEntityActions extends DinocraftEntityModule
{
	public DinocraftEntityActions(DinocraftEntity entity)
	{
		super(entity);
	}

	private boolean hasJumpedInAir;
	private boolean canJumpInAir;
	private boolean canSneakJump;
	protected boolean dreadedFlying;
	protected boolean jesterSpeeding;
	protected boolean jesterized;
	protected EntityLivingBase jesterizingEntity;
	protected boolean mesmerized;
	protected boolean fallingCrystals;
	protected EntityLivingBase fallingCrystalsShooter;
	protected boolean electrified;

	public void setMesmerized(int time)
	{
		if (!(this.getEntity() instanceof EntityPlayer))
		{
			this.mesmerized = true;
			this.getDinocraftEntity().ticks.mesmerizedTicks = time * 20;
		}
	}
	
	public boolean isMesmerized()
	{
		return !(this.getEntity() instanceof EntityPlayer) ? this.mesmerized : false;
	}

	public void setDreadedFlying(boolean dreadedFlying)
	{
		this.dreadedFlying = dreadedFlying;
	}

	public boolean isDreadedFlying()
	{
		return this.dreadedFlying;
	}

	public void setJesterSpeeding(boolean jesterSpeeding)
	{
		this.jesterSpeeding = jesterSpeeding;
	}

	public boolean isJesterSpeeding()
	{
		return this.jesterSpeeding;
	}
	
	public void setJesterized(EntityLivingBase jesterizingEntity, int time)
	{
		this.jesterized = true;
		this.jesterizingEntity = jesterizingEntity;
		this.getDinocraftEntity().ticks.jesterizedTicks = time * 20;
	}
	
	public boolean isJesterized()
	{
		return this.jesterized;
	}

	public void setFallingCrystals(EntityLivingBase fallingCrystalsShooter, int time)
	{
		this.fallingCrystals = true;
		this.fallingCrystalsShooter = fallingCrystalsShooter;
		this.getDinocraftEntity().ticks.fallingCrystalsTicks = time * 20;
	}
	
	public boolean isFallingCrystals()
	{
		return this.fallingCrystals;
	}

	public void setElectrified(int time)
	{
		this.electrified = true;
		this.getDinocraftEntity().ticks.electrifiedTicks = time * 20;
	}
	
	public boolean isElectrified()
	{
		return this.electrified;
	}
	
	protected int shootingCount = 0;
	protected int boltCount;
	
	public void shootMagatiumBolts(int count)
	{
		this.boltCount = count;
		this.shootingCount = 0;
	}
	
	public boolean canSneakJump()
	{
		return this.canSneakJump;
	}
	
	public void setCanSneakJump(boolean canSneakJump)
	{
		this.canSneakJump = canSneakJump;
	}

	public boolean hasJumpedInAir()
	{
		return this.hasJumpedInAir;
	}
	
	public void setHasJumpedInAir(boolean jumpedInAir)
	{
		this.hasJumpedInAir = jumpedInAir;
	}
	
	public boolean canJumpInAir()
	{
		return this.canJumpInAir;
	}
	
	public void setCanJumpInAir(boolean jumpedInAir)
	{
		this.canJumpInAir = jumpedInAir;
	}

	protected boolean hasExtraMaxHealth;

	public boolean hasExtraMaxHealth()
	{
		return this.hasExtraMaxHealth;
	}

	public void setHasExtraMaxHealth(boolean hasExtraMaxHealth)
	{
		this.hasExtraMaxHealth = hasExtraMaxHealth;
	}

	@Override
	public void write(NBTTagCompound tag)
	{
		NBTTagCompound root = new NBTTagCompound();
		tag.setTag("Actions", root);

		root.setBoolean("CanJumpInAir", this.canJumpInAir);
		root.setBoolean("HasJumpedInAir", this.hasJumpedInAir);
		root.setBoolean("HasExtraMaxHealth", this.hasExtraMaxHealth);

		root.setBoolean("Mesmerized", this.mesmerized);
		root.setBoolean("Electrified", this.electrified);
		root.setBoolean("DreadedFlying", this.dreadedFlying);
	}
	
	@Override
	public void read(NBTTagCompound tag)
	{
		NBTTagCompound root = tag.getCompoundTag("Actions");

		this.canJumpInAir = root.getBoolean("CanJumpInAir");
		this.hasJumpedInAir = root.getBoolean("HasJumpedInAir");
		this.hasExtraMaxHealth = root.getBoolean("HasExtraMaxHealth");

		this.mesmerized = root.getBoolean("Mesmerized");
		this.electrified = root.getBoolean("Electrified");
		this.dreadedFlying = root.getBoolean("DreadedFlying");
	}
}