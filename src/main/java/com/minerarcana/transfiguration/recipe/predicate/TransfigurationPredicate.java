package com.minerarcana.transfiguration.recipe.predicate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationPredicates;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface TransfigurationPredicate extends Predicate<TransfigurationContainer<?>> {
    PredicateType getType();

    static TransfigurationPredicate[] fromJson(JsonObject jsonObject) {
        return from(
                jsonObject.has("predicates"),
                JsonOps.INSTANCE,
                () -> GsonHelper.getAsJsonArray(jsonObject, "predicates"),
                message -> {
                    throw new JsonParseException("Failed to Parse 'predicates' due to %s".formatted(message));
                }
        );
    }

    static TransfigurationPredicate[] fromBuffer(FriendlyByteBuf buf) {
        return from(
                buf.readBoolean(),
                NbtOps.INSTANCE,
                () -> Objects.requireNonNull(buf.readNbt()).get("tag"),
                message -> Transfiguration.LOGGER.error("Failed to Read Predicate from buffer: %s".formatted(message))
        );
    }

    static <T> TransfigurationPredicate[] from(boolean read, DynamicOps<T> ops, Supplier<T> provider, Consumer<String> onError) {
        if (read) {
            return TransfigurationPredicates.LIST_CODEC.get()
                    .decode(ops, provider.get())
                    .getOrThrow(false, onError)
                    .getFirst()
                    .toArray(TransfigurationPredicate[]::new);
        } else {
            return new TransfigurationPredicate[0];
        }
    }

    static JsonElement toJson(TransfigurationPredicate[] predicates) {
        return to(
                predicates,
                JsonOps.INSTANCE,
                message -> {
                }
        );
    }

    static void toBuffer(FriendlyByteBuf buf, TransfigurationPredicate[] predicates) {
        buf.writeBoolean(predicates.length > 0);
        if (predicates.length > 0) {
            CompoundTag tag = new CompoundTag();
            tag.put("tag", Objects.requireNonNull(to(
                    predicates,
                    NbtOps.INSTANCE,
                    message -> {

                    }
            )));
            buf.writeNbt(tag);
        }
    }

    static <T> T to(TransfigurationPredicate[] predicates, DynamicOps<T> ops, Consumer<String> onError) {
        if (predicates.length > 0) {
            return TransfigurationPredicates.LIST_CODEC.get()
                    .encode(Arrays.stream(predicates).toList(), ops, ops.empty())
                    .getOrThrow(false, onError);
        } else {
            return null;
        }
    }
}
