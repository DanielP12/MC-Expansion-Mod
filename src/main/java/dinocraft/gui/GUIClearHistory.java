package dinocraft.gui;

import java.io.IOException;

import dinocraft.network.PacketHandler;
import dinocraft.network.client.CPacketRunCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class GUIClearHistory extends GuiScreen
{
	private String username;
	private boolean backup;

	public GUIClearHistory()
	{
		this.allowUserInput = false;
		String[] tags = Minecraft.getMinecraft().player.getTags().toArray(new String[0]);
		
		for (String tag : tags)
		{
			if (tag.contains("viewing punishment history of "))
			{
				this.backup = false;
				this.username = tag.substring(tag.lastIndexOf(' ') + 1);
				break;
			}
			else if (tag.contains("viewing backup punishment history of "))
			{
				this.backup = true;
				this.username = tag.substring(tag.lastIndexOf(' ') + 1);
				break;
			}
		}
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
		this.addButton(new GuiButton(0, resolution.getScaledWidth() / 2 + 10, resolution.getScaledHeight() / 2 + 10, 80, 20, "No"));
		this.addButton(new GuiButton(1, resolution.getScaledWidth() / 2 - 90, resolution.getScaledHeight() / 2 + 10, 80, 20, "Yes"));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		switch (button.id)
		{
			case 0:
			{
				PacketHandler.sendToServer(new CPacketRunCommand("/history " + this.username + (this.backup ? " backup view" : "")));
				break;
			}
			case 1:
			{
				PacketHandler.sendToServer(new CPacketRunCommand("/history " + this.username + (this.backup ? " backup restore" : " clear")));
				Minecraft.getMinecraft().displayGuiScreen((GuiScreen) null);
				break;
			}
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
		String str = null;
		
		if (this.backup)
		{
			str = "Are you sure you would like to restore the backup";
			String str2 = "punishment history of " + this.username + "?";
			this.fontRenderer.drawStringWithShadow(str, resolution.getScaledWidth() / 2 - this.fontRenderer.getStringWidth(str) / 2, resolution.getScaledHeight() / 2 - 20, 0xFFFFFF);
			this.fontRenderer.drawStringWithShadow(str2, resolution.getScaledWidth() / 2 - this.fontRenderer.getStringWidth(str2) / 2, resolution.getScaledHeight() / 2 - 20 + this.fontRenderer.FONT_HEIGHT + 2, 0xFFFFFF);
		}
		else
		{
			str = "Are you sure you would like to clear the punishment history of " + this.username + "?";
			this.fontRenderer.drawStringWithShadow(str, resolution.getScaledWidth() / 2 - this.fontRenderer.getStringWidth(str) / 2, resolution.getScaledHeight() / 2 - 20, 0xFFFFFF);
		}
	}
}
