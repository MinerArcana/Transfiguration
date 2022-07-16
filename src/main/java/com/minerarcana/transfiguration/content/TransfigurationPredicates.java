package com.minerarcana.transfiguration.content;

import com.google.common.base.Suppliers;
import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.recipe.predicate.LevelPredicate;
import com.minerarcana.transfiguration.recipe.predicate.PositionPredicate;
import com.minerarcana.transfiguration.recipe.predicate.PredicateType;
import com.minerarcana.transfiguration.recipe.predicate.TransfigurationPredicate;
import com.mojang.serialization.Codec;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings({"UnstableApiUsage"})
public class TransfigurationPredicates {
    public static final Supplier<IForgeRegistry<PredicateType>> PREDICATE_TYPE_REGISTRY = Transfiguration.getRegistrate()
            .makeRegistry("predicate", PredicateType.class, RegistryBuilder::new);

    public static final Supplier<Codec<TransfigurationPredicate>> CODEC = Suppliers.memoize(
            () -> PREDICATE_TYPE_REGISTRY.get()
                    .getCodec()
                    .dispatch(TransfigurationPredicate::getType, PredicateType::getCodec)
    );

    public static final Supplier<Codec<List<TransfigurationPredicate>>> LIST_CODEC = Suppliers.memoize(
            () -> CODEC.get().listOf()
    );

    public static final RegistryEntry<PredicateType> LEVEL = Transfiguration.getRegistrate()
            .object("level")
            .simple(PredicateType.class, () -> PredicateType.of(LevelPredicate.CODEC));

    public static final RegistryEntry<PredicateType> POSITION = Transfiguration.getRegistrate()
            .object("position")
            .simple(PredicateType.class, () -> PredicateType.of(PositionPredicate.CODEC));


    public static void setup() {

    }
}
