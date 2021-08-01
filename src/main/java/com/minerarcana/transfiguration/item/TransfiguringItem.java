package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public abstract class TransfiguringItem extends Item implements ITransfiguring {
    private final Supplier<TransfigurationType> type;

    public TransfiguringItem(Supplier<TransfigurationType> type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    @Nonnull
    public ActionResultType onItemUse(@Nonnull ItemUseContext context) {
        TransfigurationContainer<BlockState> blockTransfigurationContainer = TransfigurationContainer.block(
                context.getWorld(), context.getPos(), context.getPlayer());
        ItemStack held = context.getItem();
        boolean transfigured = BlockTransfigurationRecipe.tryTransfigure(this.type.get(), blockTransfigurationContainer,
                this.getPowerModifier(held), this.getTimeModifier(held));
        if (transfigured) {
            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                this.afterTransfiguration(context.getItem(), context.getPlayer(), context.getHand());
            }
        }
        return transfigured ? ActionResultType.func_233537_a_(context.getWorld().isRemote()) : ActionResultType.FAIL;
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName(@Nonnull ItemStack itemStack) {
        return new TranslationTextComponent(this.getDefaultTranslationKey(), this.getType(itemStack).getDisplayName());
    }

    @Override
    public TransfigurationType getType(ItemStack itemStack) {
        return type.get();
    }
}
