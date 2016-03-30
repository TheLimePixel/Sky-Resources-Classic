package com.bartz24.skyresources.registry;

import com.bartz24.skyresources.technology.gui.GuiCombustionHeater;
import com.bartz24.skyresources.technology.gui.container.ContainerCombustionHeater;
import com.bartz24.skyresources.technology.tile.CombustionHeaterTile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class ModGuiHandler implements IGuiHandler
{
	public static final int CombustionHeaterGUI = 0;

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z)
	{
		if (id == CombustionHeaterGUI)
			return new ContainerCombustionHeater(player.inventory,
					(CombustionHeaterTile) world
							.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world,
			int x, int y, int z)
	{
		if (id == CombustionHeaterGUI)
			return new GuiCombustionHeater(player.inventory,
					(CombustionHeaterTile) world
							.getTileEntity(new BlockPos(x, y, z)));

		return null;
	}
}
