package com.minerarcana.transfiguration.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationAttributes;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
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

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        return slot == EquipmentSlotType.MAINHAND ? ImmutableMultimap.of(
                TransfigurationAttributes.POWER_MODIFIER.get(), new AttributeModifier(
                        TransfigurationAttributes.POWER_UUID,
                        "Item Modifier",
                        this.getPowerModifier(stack),
                        AttributeModifier.Operation.ADDITION
                ),
                TransfigurationAttributes.TIME_MODIFIER.get(), new AttributeModifier(
                        TransfigurationAttributes.TIME_UUID,
                        "Item Modifier",
                        this.getTimeModifier(stack),
                        AttributeModifier.Operation.ADDITION
                )
        ) : super.getAttributeModifiers(slot, stack);
    }
}
