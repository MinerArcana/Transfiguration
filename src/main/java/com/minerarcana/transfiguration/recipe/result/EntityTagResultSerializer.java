package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import com.minerarcana.transfiguration.recipe.json.TagJson;
import net.minecraft.entity.EntityType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class EntityTagResultSerializer extends ResultSerializer<EntityTagResult> {
    @Nonnull
    @Override
    public EntityTagResult parse(@Nonnull PacketBuffer buffer) {
        return new EntityTagResult(EntityTypeTags.func_232896_a_(buffer.readResourceLocation().toString()));
    }

    @Nonnull
    @Override
    public EntityTagResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return new EntityTagResult(TagJson.getEntityTypeTag(json));
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull EntityTagResult object) {
        buffer.writeResourceLocation(object.getTag().getName());
    }
}
