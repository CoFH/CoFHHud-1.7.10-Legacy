package cofh.hud.itempickup;

import net.minecraft.entity.item.EntityItem;

public class PendingItem {

	public EntityItem theItem;
	public String theText;
	public int timeLeft;

	public PendingItem(EntityItem item) {

		theItem = item;
		theText = item.getEntityItem().stackSize + "x " + item.getEntityItem().getDisplayName();
		timeLeft = 60;
	}
}
