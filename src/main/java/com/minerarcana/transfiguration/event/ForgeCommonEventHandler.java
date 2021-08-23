package com.minerarcana.transfiguration.event;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.item.ITransfiguring;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Transfiguration.ID, bus = Bus.FORGE)
public class ForgeCommonEventHandler {

    @SubscribeEvent
    public static void handleEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack itemStack = event.getPlayer()
                .getHeldItem(event.getHand());
        if (itemStack.getItem() instanceof ITransfiguring) {
            if (((ITransfiguring) itemStack.getItem()).transfigureEntity(
                    event.getTarget(),
                    event.getPlayer(),
                    itemStack
            )) {
                event.setCancellationResult(ActionResultType.SUCCESS);
            }
        }
    }
}
