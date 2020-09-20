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
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.common.util.NonNullPredicate;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TransfiguringEntity<T extends TransfigurationRecipe<U, V>, U extends NonNullPredicate<V>, V> extends Entity implements IRendersAsItem {
    private static final DataParameter<String> RECIPE_NAME = EntityDataManager.createKey(TransfiguringEntity.class,
            DataSerializers.STRING);
    private static final DataParameter<Direction> PLACED_ON = EntityDataManager.createKey(TransfiguringEntity.class,
            DataSerializers.DIRECTION);

    private final NonNullLazy<Integer> offset;

    private T recipe;
    private long startTime;
    private int modifiedTime;
    private double powerModifier;
    private int noRecipeTicks;
    private ResultHandler resultHandler;
    private ItemStack itemStack;
    private TransfigurationContainer<V> transfigurationContainer;

    public TransfiguringEntity(EntityType<?> entityType, World world) {
        super(entityType, world);
        this.offset = NonNullLazy.of(() -> this.getEntityWorld().getRandom().nextInt(20));
    }

    public TransfiguringEntity(EntityType<?> entityType, World world, Direction placedOn, T recipe, int modifiedTime,
                               double powerModifier) {
        this(entityType, world);
        this.setPlacedOn(placedOn);
        this.startTime = world.getGameTime();
        this.recipe = recipe;
        this.setRecipeName(recipe.getId().toString());
        this.modifiedTime = modifiedTime;
        this.powerModifier = powerModifier;
    }

    public void setPosition(BlockPos blockPos) {
        this.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        this.updateBoundingBox();
    }

    protected void updateBoundingBox() {
        if (this.getPlacedOn() != null) {
            Direction facing = this.getPlacedOn().getOpposite();
            double xPos = (double) this.getPosition().getX() + 0.5D - (double) facing.getXOffset() * 0.46875D;
            double yPos = (double) this.getPosition().getY() + 0.5D - (double) facing.getYOffset() * 0.46875D;
            double zPos = (double) this.getPosition().getZ() + 0.5D - (double) facing.getZOffset() * 0.46875D;
            this.setRawPosition(xPos, yPos, zPos);
            double x = 12;
            double y = 12;
            double z = 12;
            Direction.Axis direction$axis = this.getPlacedOn().getAxis();
            switch (direction$axis) {
                case X:
                    x = 1.0D;
                    break;
                case Y:
                    y = 1.0D;
                    break;
                case Z:
                    z = 1.0D;
            }

            x = x / 32.0D;
            y = y / 32.0D;
            z = z / 32.0D;
            this.setBoundingBox(new AxisAlignedBB(xPos - x, yPos - y, zPos - z, xPos + x, yPos + y, zPos + z));
        }
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
        this.getDataManager().register(PLACED_ON, Direction.DOWN);
    }

    @Override
    protected void readAdditional(@Nonnull CompoundNBT compound) {
        this.setPlacedOn(Direction.byName(compound.getString("placedOn")));
        this.setRecipeName(compound.getString("recipeName"));
        this.startTime = compound.getLong("startTime");
        this.modifiedTime = compound.getInt("modifiedTime");
        this.powerModifier = compound.getDouble("powerModifier");
    }

    @Override
    protected void writeAdditional(@Nonnull CompoundNBT compound) {
        compound.putString("placedOn", this.getPlacedOn().getString());
        compound.putString("recipeName", this.getRecipeName());
        compound.putLong("startTime", this.startTime);
        compound.putInt("modifiedTime", this.modifiedTime);
        compound.putDouble("powerModifier", this.powerModifier);
    }

    @Override
    @Nonnull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setPlacedOn(Direction direction) {
        this.getDataManager().set(PLACED_ON, direction);
        if (direction.getAxis().isHorizontal()) {
            this.rotationPitch = 0.0F;
            this.rotationYaw = (float)(direction.getHorizontalIndex() * 90);
        } else {
            this.rotationPitch = (float)(-90 * direction.getAxisDirection().getOffset());
            this.rotationYaw = 0.0F;
        }

        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        this.updateBoundingBox();
    }

    public Direction getPlacedOn() {
        return this.getDataManager().get(PLACED_ON);
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
}
