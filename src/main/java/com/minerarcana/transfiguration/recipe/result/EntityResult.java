package com.minerarcana.transfiguration.recipe.result;

import com.google.common.base.Suppliers;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationRecipes;
import com.minerarcana.transfiguration.recipe.nbt.NBTCopier;
import com.minerarcana.transfiguration.recipe.resultinstance.AfterDoneResultInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeSpawnEggItem;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.function.Supplier;

public class EntityResult extends Result {
    private final EntityType<?> entityType;
    private final CompoundTag defaultTag;
    private final NBTCopier nbtCopier;
    private final ItemStack representation;

    public EntityResult(EntityType<?> entityType, CompoundTag defaultTag, NBTCopier nbtCopier) {
        this.entityType = entityType;
        this.defaultTag = defaultTag;
        this.nbtCopier = nbtCopier;
        SpawnEggItem item = ForgeSpawnEggItem.fromEntityType(entityType);
        this.representation = item != null ? new ItemStack(item) : ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public ResultInstance create() {
        return new AfterDoneResultInstance(1, this::handle);
    }

    public void handle(@Nonnull TransfigurationContainer<?> transfigurationContainer, double powerModifier) {
        Entity entity = entityType.create(transfigurationContainer.getLevel());
        if (entity != null) {
            if (!transfigurationContainer.getLevel().isClientSide()) {
                BlockPos blockPos = transfigurationContainer.getTargetedPos();
                entity.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                Supplier<CompoundTag> entityNBT = Suppliers.memoize(() -> entity.saveWithoutId(new CompoundTag()));
                if (this.getDefaultNBT() != null) {
                    entityNBT.get().merge(this.getDefaultNBT());
                }
                if (this.nbtCopier.hasOperations() && transfigurationContainer.getTargeted() instanceof Entity priorEntity) {
                    this.nbtCopier.copy(priorEntity.saveWithoutId(new CompoundTag()), entityNBT::get);
                }

                entity.load(entityNBT.get());

                transfigurationContainer.getLevel().addFreshEntity(entity);
            }
        }
    }

    @Override
    @Nonnull
    public ItemStack getRepresentation() {
        return this.representation;
    }

    @Override
    public Ingredient getView() {
        return Ingredient.of(this.getRepresentation());
    }

    @Nonnull
    @Override
    public ResultSerializer<?> getSerializer() {
        return TransfigurationRecipes.ENTITY_RESULT_SERIALIZER.get();
    }

    public EntityType<?> getEntityType() {
        return entityType;
    }

    public CompoundTag getDefaultNBT() {
        return this.defaultTag;
    }

    public static EntityResult create(EntityType<?> entityType, CompoundTag defaultTag) {
        return new EntityResult(entityType, defaultTag, new NBTCopier(Collections.emptyList()));
    }

    public static EntityResult create(EntityType<?> entityType, CompoundTag defaultTag, NBTCopier nbtCopier) {
        return new EntityResult(entityType, defaultTag, nbtCopier);
    }

    public static void summon(TransfigurationContainer<?> transfigurationContainer, EntityType<?> entityType) {
        Entity entity = entityType.create(transfigurationContainer.getLevel());
        if (entity != null) {
            if (!transfigurationContainer.getLevel().isClientSide()) {
                BlockPos blockPos = transfigurationContainer.getTargetedPos();
                entity.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                transfigurationContainer.getLevel().addFreshEntity(entity);
            }
        }
    }
}
