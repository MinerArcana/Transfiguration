package com.minerarcana.transfiguration.transfiguring;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import com.minerarcana.transfiguration.util.ResourceLocationHelper;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.NonNullLazy;

public class TransfigurationType {
    private final ResourceLocation id;
    private final ResourceLocation blockRecipeId;
    private final NonNullLazy<IRecipeType<BlockTransfigurationRecipe>> blockRecipeType;

    public TransfigurationType(String id) {
        this(Transfiguration.rl(id));
    }

    public TransfigurationType(ResourceLocation id) {
        this.id = id;
        this.blockRecipeId = ResourceLocationHelper.append(id, "block");
        this.blockRecipeType = NonNullLazy.of(() -> IRecipeType.register(blockRecipeId.toString()));
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public ResourceLocation getBlockRecipeId() {
        return this.blockRecipeId;
    }

    public IRecipeType<BlockTransfigurationRecipe> getBlockRecipeType() {
        return this.blockRecipeType.get();
    }
}
