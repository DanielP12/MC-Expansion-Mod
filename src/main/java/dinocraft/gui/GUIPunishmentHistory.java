package dinocraft.gui;

import java.io.IOException;

import dinocraft.Dinocraft;
import dinocraft.network.PacketHandler;
import dinocraft.network.client.CPacketDisplayContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GUIPunishmentHistory extends GuiContainer
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Dinocraft.MODID, "textures/gui/container/generic_156.png");
	private final PunishmentHistory container;
	private String username;
	private boolean all = true;
	private boolean mutes;
	private boolean warnings;
	private boolean backup;
	
	public GUIPunishmentHistory(PunishmentHistory container)
	{
		super(container);
		this.container = container;
		this.allowUserInput = false;
		this.xSize = 256;
		this.ySize = 248;
		String[] tags = Minecraft.getMinecraft().player.getTags().toArray(new String[0]);
		String tag = tags[tags.length - 1];
		this.backup = tag.startsWith("viewing backup punishment history of ");
		this.username = tag.substring(tag.lastIndexOf(' ') + 1);
	}

	@Override
	public void initGui()
	{
		super.initGui();
		ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

		if (this.backup)
		{
			this.addButton(new GuiButton(0, resolution.getScaledWidth() / 2 - 118, this.guiTop + this.ySize - 10, 100, 20, "Close"));
			this.addButton(new GuiButton(1, resolution.getScaledWidth() / 2 + 18, this.guiTop + this.ySize - 10, 100, 20, "Restore"));
		}
		else
		{
			this.addButton(new GuiButton(0, resolution.getScaledWidth() / 2 - 118, this.guiTop + this.ySize - 10, 100, 20, "Close"));
			GuiButton button = new GuiButton(1, resolution.getScaledWidth() / 2 + 18, this.guiTop + this.ySize - 10, 100, 20, "Clear History");
			this.addButton(button);
			button.enabled = false;
		}

		GuiButton all = new GuiButton(2, resolution.getScaledWidth() / 2 - 180, this.guiTop + 9, 50, 20, "All");
		GuiButton mutes = new GuiButton(3, resolution.getScaledWidth() / 2 - 180, this.guiTop + 34, 50, 20, "Mutes");
		GuiButton warnings = new GuiButton(4, resolution.getScaledWidth() / 2 - 180, this.guiTop + 59, 50, 20, "Warnings");
		this.addButton(all);
		this.addButton(mutes);
		this.addButton(warnings);
		
		if (this.all)
		{
			all.enabled = false;
		}
		
		if (this.mutes)
		{
			mutes.enabled = false;
		}
		
		if (this.warnings)
		{
			warnings.enabled = false;
		}
	}

	public void enableAllButtonsExcept(int buttonId)
	{
		for (int i = 2; i < 5; i++)
		{
			this.buttonList.get(i).enabled = i == buttonId ? false : true;
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		switch (button.id)
		{
			case 0:
			{
				Minecraft.getMinecraft().displayGuiScreen((GuiScreen) null);
				break;
			}
			case 1:
			{
				Minecraft.getMinecraft().displayGuiScreen(new GUIClearHistory());
				break;
			}
			case 2:
			{
				PacketHandler.sendToServer(new CPacketDisplayContainer("all"));
				this.all = true;
				this.mutes = false;
				this.warnings = false;
				this.enableAllButtonsExcept(2);
				break;
			}
			case 3:
			{
				PacketHandler.sendToServer(new CPacketDisplayContainer("mutes"));
				this.all = false;
				this.mutes = true;
				this.warnings = false;
				this.enableAllButtonsExcept(3);
				break;
			}
			case 4:
			{
				PacketHandler.sendToServer(new CPacketDisplayContainer("warnings"));
				this.all = false;
				this.mutes = false;
				this.warnings = true;
				this.enableAllButtonsExcept(4);
				break;
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		
		if (!this.backup)
		{
			boolean empty = true;
			
			for (Slot slot : this.container.inventorySlots)
			{
				ItemStack stack = slot.getStack();
				
				if (!stack.isEmpty())
				{
					empty = false;
					break;
				}
			}
			
			this.buttonList.get(1).enabled = !empty;
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		if (this.backup)
		{
			this.fontRenderer.drawString(this.username + "'s Backup " + (this.mutes ? "Mute" : this.warnings ? "Warning" : this.all ? "Punishment" : "") + " History", 11, -2, 4210752);
		}
		else
		{
			this.fontRenderer.drawString(this.username + "'s " + (this.mutes ? "Mute" : this.warnings ? "Warning" : this.all ? "Mute and Warning" : "") + " History", 11, -2, 4210752);
		}
	}
	
	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
		Minecraft.getMinecraft().player.getTags().remove("viewing " + (this.backup ? "backup " : "") + "punishment history of " + this.username);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop - 8, 0, 0, this.xSize, this.ySize);
	}
}
