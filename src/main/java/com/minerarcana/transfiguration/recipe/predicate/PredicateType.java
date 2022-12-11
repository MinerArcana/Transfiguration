package com.minerarcana.transfiguration.recipe.predicate;

import com.mojang.serialization.Codec;

public record PredicateType(Codec<? extends TransfigurationPredicate> codec) {

    public static PredicateType of(Codec<? extends TransfigurationPredicate> codec) {
        return new PredicateType(codec);
    }
}
