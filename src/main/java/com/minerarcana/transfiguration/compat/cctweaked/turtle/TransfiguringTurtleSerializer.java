package com.minerarcana.transfiguration.compat.cctweaked.turtle;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.recipe.json.RegistryJson;
import dan200.computercraft.api.turtle.TurtleUpgradeSerialiser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.NotNull;

public class TransfiguringTurtleSerializer extends TurtleUpgradeSerialiser.Base<TransfiguringTurtleUpgrade> {
    @NotNull
    @Override
    public TransfiguringTurtleUpgrade fromJson(@NotNull ResourceLocation id, @NotNull JsonObject object) {
        return new TransfiguringTurtleUpgrade(
                id,
                RegistryJson.getTransfigurationType(object),
                CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(object, "itemStack"), true)
        );
    }

    @NotNull
    @Override
    public TransfiguringTurtleUpgrade fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf buffer) {
        return new TransfiguringTurtleUpgrade(
                id,
                buffer.readRegistryId(),
                buffer.readItem()
        );
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull TransfiguringTurtleUpgrade upgrade) {
        buffer.writeRegistryId(upgrade.getTransfigurationType());
        buffer.writeItem(upgrade.getCraftingItem());
    }


}
