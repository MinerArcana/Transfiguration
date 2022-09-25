package com.minerarcana.transfiguration.recipe.predicate;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationPredicates;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;

public record FluidStatePredicate(
        Fluid fluid
) implements TransfigurationPredicate {
    public static final Codec<FluidStatePredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ForgeRegistries.FLUIDS.getCodec()
                    .optionalFieldOf("fluid", Fluids.EMPTY)
                    .forGetter(FluidStatePredicate::fluid)
    ).apply(instance, FluidStatePredicate::new));

    @Override
    public PredicateType getType() {
        return TransfigurationPredicates.FLUIDSTATE.get();
    }

    @Override
    public boolean test(TransfigurationContainer<?> transfigurationContainer) {
        return transfigurationContainer.getFluidState().is(this.fluid());
    }
}
