package com.minerarcana.transfiguration.recipe.ingedient;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.minerarcana.transfiguration.recipe.json.SerializerJson;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Function;

public class IngredientListIngredientSerializer<T extends BasicIngredient> extends BasicIngredientSerializer<T> {
    private final Function<List<BasicIngredient>, T> creator;
    private final Function<T, List<BasicIngredient>> describe;

    public IngredientListIngredientSerializer(Function<List<BasicIngredient>, T> creator,
                                              Function<T, List<BasicIngredient>> describe) {
        this.creator = creator;
        this.describe = describe;
    }

    @Nonnull
    @Override
    public T parse(@Nonnull PacketBuffer buffer) {
        List<BasicIngredient> ingredients = Lists.newArrayList();
        int number = buffer.readInt();
        for (int x = 0; x < number; x++) {
            ingredients.add(BasicIngredient.fromBuffer(buffer));
        }
        return creator.apply(ingredients);
    }

    @Nonnull
    @Override
    public T parse(@Nonnull JsonObject json) throws JsonParseException {
        return creator.apply(SerializerJson.getBasicIngredients(json));
    }

    @Override
    public void write(@Nonnull PacketBuffer buffer, @Nonnull T object) {
        List<BasicIngredient> basicIngredients = describe.apply(object);
        buffer.writeInt(basicIngredients.size());
        for (BasicIngredient basicIngredient : basicIngredients) {
            BasicIngredient.toBuffer(buffer, basicIngredient);
        }
    }
}
