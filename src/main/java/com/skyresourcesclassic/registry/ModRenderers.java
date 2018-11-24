package com.skyresourcesclassic.registry;

import com.skyresourcesclassic.References;
import com.skyresourcesclassic.alchemy.block.CondenserBlock.CondenserVariants;
import com.skyresourcesclassic.alchemy.block.CrystallizerBlock.CrystallizerVariants;
import com.skyresourcesclassic.alchemy.item.AlchemyItemComponent;
import com.skyresourcesclassic.alchemy.item.DirtyGemItem;
import com.skyresourcesclassic.alchemy.item.MetalCrystalItem;
import com.skyresourcesclassic.alchemy.render.CrucibleTESR;
import com.skyresourcesclassic.alchemy.tile.CrucibleTile;
import com.skyresourcesclassic.base.entity.EntityHeavyExplosiveSnowball;
import com.skyresourcesclassic.base.entity.EntityHeavySnowball;
import com.skyresourcesclassic.base.entity.RenderEntityItem;
import com.skyresourcesclassic.base.item.BaseItemComponent;
import com.skyresourcesclassic.base.item.ItemWaterExtractor;
import com.skyresourcesclassic.technology.block.CombustionHeaterBlock;
import com.skyresourcesclassic.technology.item.TechItemComponent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

import javax.annotation.Nonnull;

public class ModRenderers {
    public static void preInit() {

        for (int i = 0; i < ModFluids.crystalFluidInfos().length; i++) {
            mapFluidState(ModFluids.crystalFluids.get(i));
        }

        for (int i = 0; i < AlchemyItemComponent.getNames().size(); i++) {
            registerItemRenderer(ModItems.alchemyComponent, i);
        }

        for (int i = 0; i < MetalCrystalItem.getNames().size(); i++) {
            registerItemRenderer(ModItems.metalCrystal, i, true);
        }

        for (int i = 0; i < DirtyGemItem.getNames().size(); i++) {
            registerItemRenderer(ModItems.dirtyGem, i, true);
        }
        for (int i = 0; i < BaseItemComponent.getNames().size(); i++) {
            registerItemRenderer(ModItems.baseComponent, i);
        }
        for (int i = 0; i < TechItemComponent.getNames().size(); i++) {
            registerItemRenderer(ModItems.techComponent, i);
        }
        registerItemRenderer(ModItems.cactusFruit);
        registerItemRenderer(ModItems.fleshySnowNugget);
        registerItemRenderer(ModItems.heavySnowball);
        registerItemRenderer(ModItems.heavyExpSnowball);
        registerItemRenderer(ModItems.cactusKnife);
        registerItemRenderer(ModItems.stoneKnife);
        registerItemRenderer(ModItems.ironKnife);
        registerItemRenderer(ModItems.diamondKnife);
        registerItemRenderer(ModItems.stoneGrinder);
        registerItemRenderer(ModItems.ironGrinder);
        registerItemRenderer(ModItems.diamondGrinder);
        registerItemRenderer(ModItems.alchemicalInfusionStone);
        registerItemRenderer(ModItems.healthGem);
        registerItemRenderer(ModItems.survivalistFishingRod);
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.cactusFruitNeedle));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.compressedCoalBlock));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.coalInfusedBlock));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.sandyNetherrack));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.darkMatterBlock));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.heavySnow));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.heavySnow2));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.dryCactus));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.miniFreezer));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.ironFreezer));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.crucible));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.fluidDropper));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.blazePowderBlock));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.dirtFurnace));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.poweredHeater));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.darkMatterWarper));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.endPortalCore));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.lifeInfuser));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.lifeInjector));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.crucibleInserter));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.rockCrusher));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.rockCleaner));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.combustionCollector));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.quickDropper));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.aqueousConcentrator));
        registerItemRenderer(Item.getItemFromBlock(ModBlocks.aqueousDeconcentrator));

        registerItemRenderer(ModItems.sandstoneInfusionStone);
        registerItemRenderer(ModItems.redSandstoneInfusionStone);

        ModelBakery.registerItemVariants(ModItems.waterExtractor,
                new ModelResourceLocation("skyresourcesclassic:water_extractor.empty", "inventory"),
                new ModelResourceLocation("skyresourcesclassic:water_extractor.full1", "inventory"),
                new ModelResourceLocation("skyresourcesclassic:water_extractor.full2", "inventory"),

                new ModelResourceLocation("skyresourcesclassic:water_extractor.full3", "inventory"),
                new ModelResourceLocation("skyresourcesclassic:water_extractor.full4", "inventory"),
                new ModelResourceLocation("skyresourcesclassic:water_extractor.full5", "inventory"),
                new ModelResourceLocation("skyresourcesclassic:water_extractor.full6", "inventory"));

        registerVariantsDefaulted(ModBlocks.combustionHeater, CombustionHeaterBlock.CombustionHeaterVariants.class, "variant");
        registerVariantsDefaulted(ModBlocks.alchemicalCondenser, CondenserVariants.class, "variant");
        registerVariantsDefaulted(ModBlocks.crystallizer, CrystallizerVariants.class, "variant");

        ModelLoader.setCustomMeshDefinition(ModItems.waterExtractor, new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                NBTTagCompound tagCompound = stack.getTagCompound();
                int amount = 0;
                if (tagCompound != null) {
                    amount = tagCompound.getInteger("amount");
                }

                int level = (int) (amount * 6F / ((ItemWaterExtractor) stack.getItem()).getMaxAmount());
                if (level < 0)
                    level = 0;
                else if (level > 6)
                    level = 6;

                return new ModelResourceLocation(
                        stack.getItem().getRegistryName() + "." + ItemWaterExtractor.extractorIcons[level],
                        "inventory");
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityHeavySnowball.class,
                new IRenderFactory<EntityHeavySnowball>() {
                    @Override
                    public RenderEntityItem createRenderFor(RenderManager manager) {
                        return new RenderEntityItem(manager, new ItemStack(ModItems.heavySnowball));
                    }
                });

        RenderingRegistry.registerEntityRenderingHandler(EntityHeavyExplosiveSnowball.class,
                new IRenderFactory<EntityHeavyExplosiveSnowball>() {
                    @Override
                    public RenderEntityItem createRenderFor(RenderManager manager) {
                        return new RenderEntityItem(manager, new ItemStack(ModItems.heavyExpSnowball));
                    }
                });
    }

    public static void init() {
        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {

            @Override
            public int colorMultiplier(ItemStack stack, int tintIndex) {
                if (stack.getMetadata() < 0 || stack.getMetadata() >= MetalCrystalItem.getNames().size())
                    return -1;

                return ModFluids.getFluidInfo(stack.getMetadata()).color;
            }

        }, ModItems.metalCrystal);

        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor() {

            @Override
            public int colorMultiplier(ItemStack stack, int tintIndex) {
                if (stack.getMetadata() < 0 || stack.getMetadata() >= ModItems.gemList.size())
                    return -1;

                return ModItems.gemList.get(stack.getMetadata()).color;
            }

        }, ModItems.dirtyGem);

        ClientRegistry.bindTileEntitySpecialRenderer(CrucibleTile.class, new CrucibleTESR());
    }

    public static void registerItemRenderer(Item item, int meta, ResourceLocation name) {
        ModelBakery.registerItemVariants(item, name);
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(name, "inventory"));
    }

    public static void registerItemRenderer(Item item, int meta) {
        registerItemRenderer(item, meta, new ResourceLocation(item.getRegistryName().toString() + meta));
    }

    public static void registerItemRenderer(Item item, int meta, boolean global) {
        if (!global)
            registerItemRenderer(item, meta);
        else
            registerItemRenderer(item, meta, item.getRegistryName());
    }

    public static void registerItemRenderer(Item item) {
        registerItemRenderer(item, item.getRegistryName());
    }

    public static void registerItemRenderer(Item item, ResourceLocation name) {
        registerItemRenderer(item, 0, name);
    }

    public static void registerBlockRenderer(Block block, IStateMapper mapper, int... metadata) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getBlockModelShapes()
                .registerBlockWithStateMapper(block, mapper);
    }

    private static <T extends Enum<T> & IStringSerializable> void registerVariantsDefaulted(Block b, Class<T> enumclazz,
                                                                                            String variantHeader) {
        Item item = Item.getItemFromBlock(b);
        for (T e : enumclazz.getEnumConstants()) {
            String variantName = variantHeader + "=" + e.getName().toLowerCase();
            ModelLoader.setCustomModelResourceLocation(item, e.ordinal(),
                    new ModelResourceLocation(b.getRegistryName(), variantName));
        }
    }

    public static void mapFluidState(Fluid fluid) {
        Block block = fluid.getBlock();
        Item item = Item.getItemFromBlock(block);
        FluidStateMapper mapper = new FluidStateMapper(fluid);
        if (item != null) {
            ModelLoader.registerItemVariants(item);
            ModelLoader.setCustomMeshDefinition(item, mapper);
        }
        ModelLoader.setCustomStateMapper(block, mapper);
    }

    static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {
        public final ModelResourceLocation location;

        public FluidStateMapper(Fluid fluid) {
            this.location = new ModelResourceLocation(References.ModID + ":fluid_block", fluid.getName());
        }

        @Nonnull
        @Override
        protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
            return location;
        }

        @Nonnull
        @Override
        public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
            return location;
        }
    }
}
