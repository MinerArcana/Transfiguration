package com.minerarcana.transfiguration.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationAttributes;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class TransfiguringItem extends Item implements ITransfiguring {
    private final Supplier<TransfigurationType> type;

    public TransfiguringItem(Supplier<TransfigurationType> type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    @Nonnull
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        TransfigurationContainer<BlockState> blockTransfigurationContainer = TransfigurationContainer.block(
                context.getLevel(),
                context.getClickedPos(),
                context.getPlayer()
        );
        ItemStack held = context.getItemInHand();
        Optional<Player> player = Optional.ofNullable(context.getPlayer());
        boolean transfigured = BlockTransfigurationRecipe.tryTransfigure(this.type.get(),
                blockTransfigurationContainer,
                player.map(value -> value.getAttributeValue(TransfigurationAttributes.POWER_MODIFIER.get()))
                        .orElseGet(() -> this.getPowerModifier(held)),
                player.map(value -> value.getAttributeValue(TransfigurationAttributes.TIME_MODIFIER.get()))
                        .orElseGet(() -> this.getTimeModifier(held))
        );
        if (transfigured) {
            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                this.afterTransfiguration(context.getItemInHand(), context.getPlayer(), context.getHand());
            }
        }
        return transfigured ? InteractionResult.sidedSuccess(context.getLevel().isClientSide()) : InteractionResult.FAIL;
    }

    @Override
    @Nonnull
    public Component getName(@Nonnull ItemStack itemStack) {
        return Component.translatable(this.getOrCreateDescriptionId(), this.getType(itemStack).getDisplayName());
    }

    @Override
    @NotNull
    public TransfigurationType getType(ItemStack itemStack) {
        return type.get();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? ImmutableMultimap.of(
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
