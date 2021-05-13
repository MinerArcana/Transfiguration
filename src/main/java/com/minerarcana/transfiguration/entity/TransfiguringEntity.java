package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.recipe.TransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.resulthandler.ResultHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.common.util.NonNullPredicate;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public abstract class TransfiguringEntity<T extends TransfigurationRecipe<U, V>, U extends NonNullPredicate<V>, V>
        extends Entity implements IRendersAsItem, IEntityAdditionalSpawnData {
    private static final DataParameter<String> RECIPE_NAME = EntityDataManager.createKey(
            TransfiguringEntity.class,
            DataSerializers.STRING
    );

    private final NonNullLazy<Integer> offset;

    private T recipe;
    private long startTime;
    private int modifiedTime;
    private double powerModifier;
    private int noRecipeTicks;
    private ResultHandler resultHandler;
    private ItemStack itemStack;
    private TransfigurationPlacement placement;
    private TransfigurationContainer<V> transfigurationContainer;
    private UUID caster;

    public TransfiguringEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
        this.offset = NonNullLazy.of(() -> this.getEntityWorld().getRandom().nextInt(20));
    }

    public TransfiguringEntity(EntityType<? extends Entity> entityType, World world, BlockPos blockPos,
                               TransfigurationPlacement placedOn, T recipe, int modifiedTime, double powerModifier) {
        super(entityType, world);
        this.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        this.setPlacement(placedOn);
        this.startTime = world.getGameTime();
        this.recipe = recipe;
        this.setRecipeName(recipe.getId().toString());
        this.modifiedTime = modifiedTime;
        this.powerModifier = powerModifier;
        this.offset = NonNullLazy.of(() -> this.getEntityWorld().getRandom().nextInt(20));
    }

    protected void setPlacement(TransfigurationPlacement placedOn) {
        this.placement = placedOn;
        this.setBoundingBox(this.placement.getBoundingBox(this.getPosition()));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getEntityWorld().isRemote()) {
            if (recipe == null) {
                recipe = this.getRecipe();
                noRecipeTicks++;
                if (noRecipeTicks > 5) {
                    this.remove();
                }
            } else {
                ResultHandler resultHandler = this.getResultHandler(recipe);
                if (this.getEntityWorld().getGameTime() - startTime > modifiedTime) {
                    this.getTransfigurationContainer().removeTarget();
                    if (resultHandler.process(this.getTransfigurationContainer(), this.powerModifier)) {
                        this.remove();
                    }
                } else {
                    resultHandler.preprocess(this.getTransfigurationContainer(), this.powerModifier);
                }
            }
        }
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(RECIPE_NAME, "");
    }

    @Override
    public void readAdditional(@Nonnull CompoundNBT compound) {
        this.setRecipeName(compound.getString("recipeName"));
        this.startTime = compound.getLong("startTime");
        this.modifiedTime = compound.getInt("modifiedTime");
        this.powerModifier = compound.getDouble("powerModifier");
        this.placement = TransfigurationPlacement.fromString(compound.getString("placement"));
    }

    @Override
    public void writeAdditional(@Nonnull CompoundNBT compound) {
        compound.putString("recipeName", this.getRecipeName());
        compound.putLong("startTime", this.startTime);
        compound.putInt("modifiedTime", this.modifiedTime);
        compound.putDouble("powerModifier", this.powerModifier);
        compound.putString("placement", this.placement.name());
    }

    @Override
    @Nonnull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setRecipeName(String recipeName) {
        this.getDataManager().set(RECIPE_NAME, recipeName);
    }

    public String getRecipeName() {
        return this.getDataManager().get(RECIPE_NAME);
    }

    public int getOffset() {
        return this.offset.get();
    }

    @Nullable
    public LivingEntity getCaster() {
        return null;
    }

    @Nonnull
    public ResultHandler getResultHandler(@Nonnull T recipe) {
        if (this.resultHandler == null) {
            this.resultHandler = recipe.createResultHandler();
        }
        return this.resultHandler;
    }

    @Nonnull
    public abstract TransfigurationContainer<V> createTransfigurationContainer();

    public TransfigurationContainer<V> getTransfigurationContainer() {
        if (this.transfigurationContainer == null) {
            this.transfigurationContainer = this.createTransfigurationContainer();
        }
        return this.transfigurationContainer;
    }

    @Nullable
    public abstract T getRecipe();

    @Override
    @Nonnull
    public ItemStack getItem() {
        if (this.itemStack == null && this.getRecipe() != null) {
            this.itemStack = TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM
                    .map(transfiguringProjectileItem -> transfiguringProjectileItem.withTransfigurationType(
                            this.getRecipe().getTransfigurationType())
                    )
                    .orElse(null);
        }
        if (this.itemStack == null) {
            return ItemStack.EMPTY;
        } else {
            return this.itemStack;
        }
    }

    @Override
    public void writeSpawnData(PacketBuffer packetBuffer) {
        packetBuffer.writeEnumValue(placement);
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        this.setPlacement(additionalData.readEnumValue(TransfigurationPlacement.class));
    }

    public TransfigurationPlacement getPlacement() {
        return this.placement;
    }
}
