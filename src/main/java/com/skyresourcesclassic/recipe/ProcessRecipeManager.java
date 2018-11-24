package com.skyresourcesclassic.recipe;

import com.skyresourcesclassic.RandomHelper;
import com.skyresourcesclassic.SkyResourcesClassic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProcessRecipeManager {
    private String type;
    public static ProcessRecipeManager combustionRecipes = new ProcessRecipeManager("combustion") {
        public void drawJEIInfo(ProcessRecipe rec, Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX,
                                int mouseY) {
            String s = Integer.toString((int) rec.getIntParameter()) + " C";
            FontRenderer fontRendererObj = minecraft.fontRenderer;
            int stringWidth = fontRendererObj.getStringWidth(s);
            fontRendererObj.drawString(s, 118 - stringWidth, 8, java.awt.Color.gray.getRGB());
        }
    };
    public static ProcessRecipeManager infusionRecipes = new ProcessRecipeManager("infusion") {
        public void drawJEIInfo(ProcessRecipe rec, Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX,
                                int mouseY) {
            String s = "x" + Float.toString(rec.getIntParameter() / 2F);
            FontRenderer fontRendererObj = minecraft.fontRenderer;
            fontRendererObj.drawString(s, 80, 0, java.awt.Color.gray.getRGB());
        }
    };
    public static ProcessRecipeManager rockGrinderRecipes = new ProcessRecipeManager("rockgrinder") {
        public void drawJEIInfo(ProcessRecipe rec, Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX,
                                int mouseY) {
            String s = Float.toString(rec.getIntParameter() * 100) + "%";
            FontRenderer fontRendererObj = minecraft.fontRenderer;
            fontRendererObj.drawString(s, 70, 0, java.awt.Color.gray.getRGB());
        }
    };
    public static ProcessRecipeManager knifeRecipes = new ProcessRecipeManager("knife");
    public static ProcessRecipeManager crucibleRecipes = new ProcessRecipeManager("crucible");
    public static ProcessRecipeManager freezerRecipes = new ProcessRecipeManager("freezer") {
        public void drawJEIInfo(ProcessRecipe rec, Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX,
                                int mouseY) {
            String s = Float.toString(rec.getIntParameter()) + " ticks";
            FontRenderer fontRendererObj = minecraft.fontRenderer;
            fontRendererObj.drawString(s, 1, -5, java.awt.Color.gray.getRGB());
        }
    };
    public static ProcessRecipeManager waterExtractorInsertRecipes = new ProcessRecipeManager("waterextractor-insert") {
        public void drawJEIInfo(ProcessRecipe rec, Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX,
                                int mouseY) {
            FontRenderer fontRendererObj = minecraft.fontRenderer;
            fontRendererObj.drawString("Inserting", 65, 0, java.awt.Color.gray.getRGB());
        }
    };
    public static ProcessRecipeManager waterExtractorExtractRecipes = new ProcessRecipeManager("waterextractor-extract") {
        public void drawJEIInfo(ProcessRecipe rec, Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX,
                                int mouseY) {
            FontRenderer fontRendererObj = minecraft.fontRenderer;
            fontRendererObj.drawString("Extracting", 65, 0, java.awt.Color.gray.getRGB());
        }
    };
    public static ProcessRecipeManager cauldronCleanRecipes = new ProcessRecipeManager("cauldronclean") {
        public void drawJEIInfo(ProcessRecipe rec, Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX,
                                int mouseY) {
            String s = Float.toString(Math.round(rec.getIntParameter() * 10000F) / 100F) + "%";
            FontRenderer fontRendererObj = minecraft.fontRenderer;
            fontRendererObj.drawString(s, 70, 10, java.awt.Color.gray.getRGB());
        }
    };

    private static List<ProcessRecipeManager> managers;

    public ProcessRecipeManager(String recipeType) {
        recipes = new ArrayList();
        ctRemovedRecipes = new ArrayList();
        ctAddedRecipes = new ArrayList();
        type = recipeType;
        if (managers == null)
            managers = new ArrayList<>();
        managers.add(this);
    }

    public static ProcessRecipeManager getManagerFromType(String type) {
        for (ProcessRecipeManager m : managers) {
            if (m.type.equals(type))
                return m;
        }
        return null;
    }

    private List<ProcessRecipe> recipes;
    private List<ProcessRecipe> ctRemovedRecipes;
    private List<ProcessRecipe> ctAddedRecipes;

    public ProcessRecipe getRecipe(List<Object> input, float intVal, boolean forceEqual, boolean mergeStacks) {
        input = mergeStacks ? mergeStacks(input) : input;

        ProcessRecipe rec = new ProcessRecipe(input, intVal, type);

        for (ProcessRecipe recipe : recipes) {
            if (rec.isInputRecipeEqualTo(recipe, forceEqual)) {
                return recipe;
            }
        }

        return null;
    }

    public ProcessRecipe getRecipe(Object input, float intVal, boolean forceEqual, boolean mergeStacks) {
        List<Object> inputs = mergeStacks ? mergeStacks(Collections.singletonList(input))
                : Collections.singletonList(input);

        ProcessRecipe rec = new ProcessRecipe(inputs, intVal, type);

        for (ProcessRecipe recipe : recipes) {
            if (rec.isInputRecipeEqualTo(recipe, forceEqual)) {
                return recipe;
            }
        }

        return null;
    }

    public ProcessRecipe getMultiRecipe(List<Object> input, float intVal, boolean forceEqual, boolean mergeStacks) {
        input = mergeStacks ? mergeStacks(input) : input;

        ProcessRecipe rec = new ProcessRecipe(input, intVal, type);

        for (ProcessRecipe recipe : recipes) {
            if (rec.isInputMultiRecipeEqualTo(recipe)) {
                return recipe;
            }
        }

        return null;
    }

    private List<Object> mergeStacks(List<Object> input) {
        int checks = 0;
        boolean merged = true;
        while (merged && checks < 50) {
            merged = false;
            for (int i = 0; i < input.size(); i++) {
                if (!(input.get(i) instanceof ItemStack))
                    continue;
                ItemStack stack1 = (ItemStack) input.get(i);
                for (int i2 = i + 1; i2 < input.size(); i2++) {
                    if (!(input.get(i2) instanceof ItemStack))
                        continue;
                    ItemStack stack2 = (ItemStack) input.get(i2);

                    int moveAmt = RandomHelper.mergeStacks(stack2, stack1, false);
                    if (moveAmt > 0) {
                        stack1.grow(moveAmt);
                        stack2.shrink(moveAmt);
                        if (stack2.getCount() <= 0)
                            stack2 = ItemStack.EMPTY;
                        merged = true;
                        break;
                    }
                }
                if (merged)
                    break;
            }

            for (int i = input.size() - 1; i >= 0; i--) {
                if (!(input.get(i) instanceof ItemStack))
                    continue;
                ItemStack stack = (ItemStack) input.get(i);
                if (stack == ItemStack.EMPTY)
                    input.remove(i);
            }

            checks++;
        }

        return input;
    }

    public List<ProcessRecipe> getRecipes() {
        return recipes;
    }

    public void addRecipe(List<Object> output, float intVal, List<Object> input) {

        if (input == null) {
            SkyResourcesClassic.logger.error("Need inputs for recipe. DID NOT ADD RECIPE.");
            return;
        }

        if (output == null) {
            SkyResourcesClassic.logger.error("Need outputs for recipe. DID NOT ADD RECIPE.");
            return;
        }

        recipes.add(new ProcessRecipe(output, input, intVal, type));
    }

    public void addRecipe(Object output, float intVal, Object input) {
        if (input == null) {
            SkyResourcesClassic.logger.error("Need inputs for recipe. DID NOT ADD RECIPE.");
            return;
        }

        if (output == null) {
            SkyResourcesClassic.logger.error("Need outputs for recipe. DID NOT ADD RECIPE.");
            return;
        }

        recipes.add(
                new ProcessRecipe(Collections.singletonList(output), Collections.singletonList(input), intVal, type));
    }

    public void addRecipe(List<Object> output, float intVal, Object input) {
        if (input == null) {
            SkyResourcesClassic.logger.error("Need inputs for recipe. DID NOT ADD RECIPE.");
            return;
        }

        if (output == null) {
            SkyResourcesClassic.logger.error("Need outputs for recipe. DID NOT ADD RECIPE.");
            return;
        }

        recipes.add(new ProcessRecipe(output, Collections.singletonList(input), intVal, type));
    }

    public void addRecipe(Object output, float intVal, List<Object> input) {

        if (input == null) {
            SkyResourcesClassic.logger.error("Need inputs for recipe. DID NOT ADD RECIPE.");
            return;
        }

        if (output == null) {
            SkyResourcesClassic.logger.error("Need outputs for recipe. DID NOT ADD RECIPE.");
            return;
        }

        recipes.add(new ProcessRecipe(Collections.singletonList(output), input, intVal, type));
    }

    public void addRecipe(ProcessRecipe recipe) {

        if ((recipe.getInputs() == null || recipe.getInputs().size() == 0)
                && (recipe.getFluidInputs() == null || recipe.getFluidInputs().size() == 0)) {
            SkyResourcesClassic.logger.error("Need inputs for recipe. DID NOT ADD RECIPE.");
            return;
        }

        if ((recipe.getOutputs() == null || recipe.getOutputs().size() == 0)
                && (recipe.getFluidOutputs() == null || recipe.getFluidOutputs().size() == 0)) {
            SkyResourcesClassic.logger.error("Need outputs for recipe. DID NOT ADD RECIPE.");
            return;
        }

        recipes.add(recipe);
    }

    public void addCTRecipe(ProcessRecipe recipe) {
        if ((recipe.getInputs() == null || recipe.getInputs().size() == 0)
                && (recipe.getFluidInputs() == null || recipe.getFluidInputs().size() == 0)) {
            SkyResourcesClassic.logger.error("Need inputs for recipe. DID NOT ADD RECIPE.");
            return;
        }

        if ((recipe.getOutputs() == null || recipe.getOutputs().size() == 0)
                && (recipe.getFluidOutputs() == null || recipe.getFluidOutputs().size() == 0)) {
            SkyResourcesClassic.logger.error("Need outputs for recipe. DID NOT ADD RECIPE.");
            return;
        }

        ctAddedRecipes.add(recipe);
    }

    public void ctRecipes() {
        for (ProcessRecipe recipe : ctRemovedRecipes) {
            removeRecipe(recipe);
        }
        for (ProcessRecipe r : ctAddedRecipes) {
            addRecipe(r);
        }
    }

    public List<ProcessRecipe> removeRecipe(ProcessRecipe recipe) {
        if ((recipe.getOutputs() == null || recipe.getOutputs().size() == 0) && (recipe.getFluidOutputs() == null
                || recipe.getFluidOutputs().size() == 0)) {
            SkyResourcesClassic.logger.error("Need outputs for recipe. DID NOT REMOVE RECIPE.");
            return null;
        }

        if ((recipe.getInputs() == null || recipe.getInputs().size() == 0) && (recipe.getFluidInputs() == null
                || recipe.getFluidInputs().size() == 0)) {

            List<Integer> recipesToRemoveAt = new ArrayList<Integer>();
            List<ProcessRecipe> recipesToRemove = new ArrayList<ProcessRecipe>();
            for (int i = 0; i < recipes.size(); i++) {
                boolean valid = true;
                for (ItemStack iOut : recipes.get(i).getOutputs()) {
                    for (ItemStack rOut : recipe.getOutputs()) {
                        if (!iOut.isItemEqual(rOut))
                            valid = false;
                    }
                }
                for (FluidStack iOut : recipes.get(i).getFluidOutputs()) {
                    for (FluidStack rOut : recipe.getFluidOutputs()) {
                        if (!iOut.isFluidEqual(rOut))
                            valid = false;
                    }
                }
                if (valid)
                    recipesToRemoveAt.add(i);
            }
            for (int i = recipesToRemoveAt.size() - 1; i >= 0; i--) {
                recipesToRemove.add(recipes.get(recipesToRemoveAt.get(i)));
                recipes.remove((int) recipesToRemoveAt.get(i));
            }
            return recipesToRemove;
        }

        List<Integer> recipesToRemoveAt = new ArrayList<Integer>();
        List<ProcessRecipe> recipesToRemove = new ArrayList<ProcessRecipe>();
        for (int i = 0; i < recipes.size(); i++) {
            if (recipes.get(i).isInputRecipeEqualTo(recipe, false)) {
                boolean valid = true;
                for (ItemStack iOut : recipes.get(i).getOutputs()) {
                    for (ItemStack rOut : recipe.getOutputs()) {
                        if (!(iOut.isEmpty() && rOut.isEmpty()) && !iOut.isItemEqual(rOut))
                            valid = false;
                    }
                }
                for (FluidStack iOut : recipes.get(i).getFluidOutputs()) {
                    for (FluidStack rOut : recipe.getFluidOutputs()) {
                        if (!iOut.isFluidEqual(rOut))
                            valid = false;
                    }
                }
                if (valid)
                    recipesToRemoveAt.add(i);
            }
        }
        for (int i = recipesToRemoveAt.size() - 1; i >= 0; i--) {
            recipesToRemove.add(recipes.get(recipesToRemoveAt.get(i)));
            recipes.remove((int) recipesToRemoveAt.get(i));
        }
        return recipesToRemove;
    }

    public void removeCTRecipe(ProcessRecipe recipe) {
        ctRemovedRecipes.add(recipe);
    }

    public void drawJEIInfo(ProcessRecipe rec, Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX,
                            int mouseY) {

    }
}
