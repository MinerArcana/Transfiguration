package com.minerarcana.transfiguration.recipe.predicate;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationPredicates;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.Structure;

public record InStructurePredicate(
        ResourceKey<Structure> structure
) implements TransfigurationPredicate {
    public static final Codec<InStructurePredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(Registry.STRUCTURE_REGISTRY).fieldOf("structure").forGetter(InStructurePredicate::structure)
    ).apply(instance, InStructurePredicate::new));

    @Override
    public PredicateType getType() {
        return TransfigurationPredicates.IN_STRUCTURE.get();
    }

    @Override
    public boolean test(TransfigurationContainer<?> transfigurationContainer) {
        if (transfigurationContainer.getLevel() instanceof ServerLevel serverLevel) {
            return serverLevel.structureManager()
                    .getStructureWithPieceAt(transfigurationContainer.getTargetedPos(), this.structure())
                    .isValid();
        }
        return false;
    }
}
