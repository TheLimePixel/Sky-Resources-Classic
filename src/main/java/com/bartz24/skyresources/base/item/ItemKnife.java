package com.bartz24.skyresources.base.item;

import com.bartz24.skyresources.References;
import com.bartz24.skyresources.registry.ModCreativeTabs;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemKnife extends Item
{
	private float damageVsEntity;

	public ItemKnife(Item.ToolMaterial material, String unlocalizedName,
			String registryName)
	{
		this.setMaxDamage((int) (material.getMaxUses() * 0.8F));
		this.damageVsEntity = 1.5F + material.getDamageVsEntity();
		this.setUnlocalizedName(References.ModID + "." + unlocalizedName);
		setRegistryName(registryName);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setCreativeTab(ModCreativeTabs.tabMain);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state)
	{
		Block block = state.getBlock();
		Material material = block.getMaterial(state);
		return material != Material.plants && material != Material.vine
				&& material != Material.coral && material != Material.leaves
				&& material != Material.gourd ? 1.0F : 3F;
	}

	public boolean canHarvestBlock(Block blockIn)
	{
		return false;
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(
			EntityEquipmentSlot equipmentSlot)
	{

		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(
				equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
		{
			multimap.put(
					SharedMonsterAttributes.ATTACK_DAMAGE
							.getAttributeUnlocalizedName(),
					new AttributeModifier(ATTACK_DAMAGE_MODIFIER,
							"Weapon modifier", this.damageVsEntity, 0));
			multimap.put(
					SharedMonsterAttributes.ATTACK_SPEED
							.getAttributeUnlocalizedName(),
					new AttributeModifier(ATTACK_SPEED_MODIFIER,
							"Weapon modifier", -1.4000000953674316D, 0));
		}

		return multimap;
	}
}
