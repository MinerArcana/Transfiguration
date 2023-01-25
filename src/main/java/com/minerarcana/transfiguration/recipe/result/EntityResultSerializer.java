package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import com.minerarcana.transfiguration.recipe.nbt.NBTCopier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

public class EntityResultSerializer extends ResultSerializer<EntityResult> {
    @Nonnull
    @Override
    public EntityResult parse(@Nonnull FriendlyByteBuf buffer) {
        return EntityResult.create(
                buffer.readRegistryId(),
                buffer.readNullable(FriendlyByteBuf::readNbt)
        );
    }

    @Nonnull
    @Override
    public EntityResult parse(@Nonnull JsonObject json) throws JsonParseException {
        return EntityResult.create(
                RegistryJson.getEntity(json),
                json.has("nbt") ? CraftingHelper.getNBT(json.get("nbt")) : null,
                NBTCopier.fromJson(json.get("copy"))
        );
    }

    @Override
    public void write(@Nonnull FriendlyByteBuf buffer, @Nonnull EntityResult object) {
        buffer.writeRegistryId(ForgeRegistries.ENTITY_TYPES, object.getEntityType());
        buffer.writeNullable(object.getDefaultNBT(), FriendlyByteBuf::writeNbt);
    }
}
