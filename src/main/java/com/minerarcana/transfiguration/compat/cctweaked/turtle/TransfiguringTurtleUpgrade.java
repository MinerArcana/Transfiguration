package com.minerarcana.transfiguration.compat.cctweaked.turtle;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.api.util.ResourceLocationHelper;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.entity.TransfiguringProjectileEntity;
import com.minerarcana.transfiguration.item.TransfiguringWandItem;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.json.ObjectJson;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dan200.computercraft.api.client.TransformedModel;
import dan200.computercraft.api.turtle.*;
import dan200.computercraft.api.upgrades.UpgradeDataProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class TransfiguringTurtleUpgrade extends AbstractTurtleUpgrade {
    private final TransfigurationType transfigurationType;

    public TransfiguringTurtleUpgrade(ResourceLocation id, TransfigurationType transfigurationTypeSupplier, ItemStack itemStack) {
        super(id, TurtleUpgradeType.TOOL, "upgrade.transfiguration.transfiguring.advective", itemStack);
        this.transfigurationType = transfigurationTypeSupplier;
    }

    @Nonnull
    @Override
    public TransformedModel getModel(@Nullable ITurtleAccess iTurtleAccess, @Nonnull TurtleSide turtleSide) {
        float xOffset = turtleSide == TurtleSide.LEFT ? -0.40625f : 0.40625f;
        Matrix4f transform = new Matrix4f(new float[]{
                0.0f, 0.0f, -1.0f, 1.0f + xOffset,
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, -1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 0.0f, 1.0f,
        });
        return TransformedModel.of(getCraftingItem(), new Transformation(transform));
    }

    @Nonnull
    @Override
    public TurtleCommandResult useTool(@Nonnull ITurtleAccess turtle, @Nonnull TurtleSide side, @Nonnull TurtleVerb verb, @Nonnull Direction direction) {
        return switch (verb) {
            case DIG -> dig(turtle, direction);
            case ATTACK -> attack(turtle, direction);
        };
    }

    @Nonnull
    private TurtleCommandResult dig(@Nonnull ITurtleAccess turtle, @Nonnull Direction direction) {
        TransfigurationContainer<BlockState> blockTransfigurationContainer = TransfigurationContainer.block(
                turtle.getLevel(), turtle.getPosition().relative(direction), null);

        return BlockTransfigurationRecipe.tryTransfigure(this.transfigurationType, blockTransfigurationContainer, 1.0, 1.0) ?
                TurtleCommandResult.success() : TurtleCommandResult.failure();
    }

    @Nonnull
    private TurtleCommandResult attack(@Nonnull ITurtleAccess turtle, @Nonnull Direction direction) {
        BlockPos position = turtle.getPosition();
        double x = position.getX() + 0.7D * (double) direction.getStepX();
        double y = position.getY() + 0.7D * (double) direction.getStepY();
        double z = position.getZ() + 0.7D * (double) direction.getStepZ();
        Level world = turtle.getLevel();
        if (!world.isClientSide()) {
            TransfiguringProjectileEntity transfiguringProjectileEntity = new TransfiguringProjectileEntity(world, x, y, z);
            transfiguringProjectileEntity.setItem(TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM.get()
                    .withTransfigurationType(transfigurationType));
            transfiguringProjectileEntity.shoot(direction.getStepX(), direction.getStepY() + 0.1F, direction.getStepZ(),
                    1.1F, 6.0F);
            world.addFreshEntity(transfiguringProjectileEntity);
        }

        return TurtleCommandResult.success();
    }

    public TransfigurationType getTransfigurationType() {
        return transfigurationType;
    }

    public static ItemStack getStackForType(TransfigurationType transfigurationType) {
        Item item = ForgeRegistries.ITEMS.getValue(ResourceLocationHelper.append(transfigurationType.getRegistryName(), "_", "wand"));
        if (item instanceof TransfiguringWandItem) {
            return new ItemStack(item);
        } else {
            return TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM
                    .map(projectileItem -> projectileItem.withTransfigurationType(transfigurationType))
                    .orElse(ItemStack.EMPTY);
        }
    }

    public static UpgradeDataProvider.Upgrade<TurtleUpgradeSerialiser<?>> getUpgradeData(RegistryEntry<TransfigurationType> transfigurationType) {
        return getUpgradeData(transfigurationType.get());
    }

    public static UpgradeDataProvider.Upgrade<TurtleUpgradeSerialiser<?>> getUpgradeData(TransfigurationType transfigurationType) {
        return new UpgradeDataProvider.Upgrade<>(
                transfigurationType.getRegistryName(),
                Turtles.TRANSFIGURING_TURTLE.get(),
                object -> {
                    object.addProperty("transfigurationType", Objects.requireNonNull(transfigurationType.getRegistryName()).toString());
                    object.add("itemStack", ObjectJson.writeItemStack(getStackForType(transfigurationType)));
                }
        );
    }
}
