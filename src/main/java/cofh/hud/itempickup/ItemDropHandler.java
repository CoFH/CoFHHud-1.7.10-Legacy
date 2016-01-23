package cofh.hud.itempickup;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class ItemDropHandler {

	public static ItemDropHandler instance = new ItemDropHandler();

	@SubscribeEvent
	public void HandleItemPickups(EntityItemPickupEvent event) {

		synchronized (ItemListRenderer.itemList) {

			ItemListRenderer.itemList.add(new PendingItem(event.item));
		}
	}
}
