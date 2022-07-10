package com.minerarcana.transfiguration.recipe.result;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.SerializerJson;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WeightedResultSerializer extends ResultSerializer<WeightedResult> {
    @NotNull
    @Override
    public WeightedResult parse(@NotNull FriendlyByteBuf buffer) {
        return new WeightedResult(
                buffer.readList(buf -> Pair.of(
                        Result.fromBuffer(buf),
                        buf.readInt()
                ))
        );
    }

    @NotNull
    @Override
    public WeightedResult parse(@NotNull JsonObject json) throws JsonParseException {
        JsonArray entries = GsonHelper.getAsJsonArray(json, "entries");
        List<Pair<Result, Integer>> parsedEntries = Lists.newArrayList();
        for (int x = 0; x < entries.size(); x++) {
            JsonObject entryObject = GsonHelper.convertToJsonObject(entries.get(x), "entries[%d]".formatted(x));
            int weight = GsonHelper.getAsInt(entryObject, "weight", 1);
            if (weight <= 0) {
                throw new JsonParseException("entries[%d].weight must be greater than 0".formatted(x));
            } else {
                try {
                    Result result = SerializerJson.getResult(entryObject);
                    parsedEntries.add(Pair.of(result, weight));
                } catch (JsonParseException e) {
                    throw new JsonParseException("entries[%d].result failed with error %s".formatted(x, e.getMessage()));
                }
            }
        }
        if (parsedEntries.isEmpty()) {
            throw new JsonParseException("entries cannot be an empty array");
        }
        return new WeightedResult(parsedEntries);
    }

    @Override
    public void write(@NotNull FriendlyByteBuf buffer, @NotNull WeightedResult object) {
        buffer.writeCollection(object.getWeightedResults().unwrap(), ((friendlyByteBuf, resultWrapper) -> {
            friendlyByteBuf.writeInt(resultWrapper.getWeight().asInt());
            Result.toBuffer(friendlyByteBuf, resultWrapper.getData());
        }));
    }
}
