package com.minerarcana.transfiguration.mixin;

import com.minerarcana.transfiguration.entity.IAged;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemEntity.class)
public class ItemEntityAged implements IAged {
    @Shadow
    private int age;

    @Override
    public int getActualAge() {
        return age;
    }
}
