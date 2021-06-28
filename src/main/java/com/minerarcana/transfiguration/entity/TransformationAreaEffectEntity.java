package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.content.TransfigurationEntities;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.UUID;

public class TransformationAreaEffectEntity extends AreaEffectCloudEntity {
    private TransfiguringEntity<?, ?, ?> transfiguringEntity;
    private UUID transfiguringUniqueId;

    public TransformationAreaEffectEntity(EntityType<? extends AreaEffectCloudEntity> cloud, World world) {
        super(cloud, world);
    }

    public TransformationAreaEffectEntity(World world, double x, double y, double z) {
        this(TransfigurationEntities.TRANSFIGURING_AREA_EFFECT.get(), world);
        this.setPosition(x, y, z);
    }

    public void setTransfiguringEntity(@Nullable TransfiguringEntity<?, ?, ?> transfiguringEntity) {
        this.transfiguringEntity = transfiguringEntity;
        this.transfiguringUniqueId = transfiguringEntity == null ? null : transfiguringEntity.getUniqueID();
    }

    @Nullable
    public TransfiguringEntity<?, ?, ?> getTransfiguringEntity() {
        if (this.transfiguringEntity == null && this.transfiguringUniqueId != null && this.world instanceof ServerWorld) {
            Entity entity = ((ServerWorld) this.world).getEntityByUuid(this.transfiguringUniqueId);
            if (entity instanceof TransfiguringEntity) {
                this.transfiguringEntity = (TransfiguringEntity<?, ?, ?>) entity;
            }
        }

        return this.transfiguringEntity;
    }
}
