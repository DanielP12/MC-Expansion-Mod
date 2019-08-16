package dinocraft.item;

import dinocraft.Reference;
import dinocraft.capabilities.player.DinocraftPlayer;
import dinocraft.capabilities.player.DinocraftPlayerActions;
import dinocraft.init.DinocraftArmour;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemTuskerersOldRags extends ItemArmor
{
	public ItemTuskerersOldRags(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String unlocalizedName)
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent event)
	{
        if (event.getEntity() instanceof EntityPlayer)
        {
            EntityPlayer playerIn = (EntityPlayer)event.getEntity();
            DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
            ItemStack leggings = playerIn.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
            
            if (!player.getActions().hasExtraMaxHealth() && leggings != null && leggings.getItem() == DinocraftArmour.TUSKERERS_OLD_RAGS && !playerIn.world.isRemote)
            {
                player.addMaxHealth(-2.0F);
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerTick(final TickEvent.PlayerTickEvent event) 
    {
        if (event.phase != TickEvent.Phase.END) return;
        
        EntityPlayer playerIn = event.player;
        DinocraftPlayer player = DinocraftPlayer.getPlayer(playerIn);
        DinocraftPlayerActions actions = player.getActions();
        ItemStack leggings = playerIn.getItemStackFromSlot(EntityEquipmentSlot.LEGS);
        
        if (leggings != null && leggings.getItem() == DinocraftArmour.TUSKERERS_OLD_RAGS && !playerIn.world.isRemote) 
        {
            if (actions.hasExtraMaxHealth()) return;
            
            player.addMaxHealth(2.0F);
            actions.setHasExtraMaxHealth(true);
        }
        else if (actions.hasExtraMaxHealth())
        {
            player.addMaxHealth(-2.0F);
            actions.setHasExtraMaxHealth(false);
        }
    }
    
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onItemTooltip(ItemTooltipEvent event)
	{
		if (event.getItemStack().getItem() == this) event.getToolTip().add(TextFormatting.BLUE + " +2 Max Health");
	}
}