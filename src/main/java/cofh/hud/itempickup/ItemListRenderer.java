package cofh.hud.itempickup;

import java.util.ArrayList;
import java.util.Collections;

public class ItemListRenderer {

	public static ArrayList<PendingItem> itemList = (ArrayList<PendingItem>) Collections.synchronizedList(new ArrayList<PendingItem>());

}
