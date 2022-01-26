package com.minerarcana.transfiguration.event;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.item.ITransfiguring;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Transfiguration.ID, bus = Bus.FORGE)
public class ForgeCommonEventHandler {

    @SubscribeEvent
    public static void handleEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack itemStack = event.getPlayer()
                .getItemInHand(event.getHand());
        if (itemStack.getItem() instanceof ITransfiguring) {
            ITransfiguring transfiguring = (ITransfiguring) itemStack.getItem();
            boolean transfigured = transfiguring.transfigureEntity(
                    event.getTarget(),
                    event.getPlayer(),
                    itemStack
            );
            if (transfigured) {
                transfiguring.afterTransfiguration(itemStack, event.getPlayer(), event.getHand());
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }
}
