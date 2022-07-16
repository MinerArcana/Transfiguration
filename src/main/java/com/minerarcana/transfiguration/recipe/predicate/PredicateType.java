package com.minerarcana.transfiguration.recipe.predicate;

import com.mojang.serialization.Codec;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class PredicateType extends ForgeRegistryEntry<PredicateType> {
    private final Codec<? extends TransfigurationPredicate> codec;

    public PredicateType(Codec<? extends TransfigurationPredicate> codec) {
        this.codec = codec;
    }

    public Codec<? extends TransfigurationPredicate> getCodec() {
        return this.codec;
    }

    public static PredicateType of(Codec<? extends TransfigurationPredicate> codec) {
        return new PredicateType(codec);
    }
}
