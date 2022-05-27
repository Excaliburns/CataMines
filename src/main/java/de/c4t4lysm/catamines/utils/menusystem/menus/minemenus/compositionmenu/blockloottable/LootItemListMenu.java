package de.c4t4lysm.catamines.utils.menusystem.menus.minemenus.compositionmenu.blockloottable;

import de.c4t4lysm.catamines.CataMines;
import de.c4t4lysm.catamines.utils.ItemStackBuilder;
import de.c4t4lysm.catamines.utils.menusystem.Menu;
import de.c4t4lysm.catamines.utils.menusystem.PlayerMenuUtility;
import de.c4t4lysm.catamines.utils.menusystem.menus.minemenus.compositionmenu.CompositionBlockMenu;
import de.c4t4lysm.catamines.utils.mine.components.CataMineBlock;
import de.c4t4lysm.catamines.utils.mine.components.CataMineLootItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class LootItemListMenu extends Menu {

    private final CataMineBlock block;

    public LootItemListMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        playerMenuUtility.setMenu(this);
        this.block = playerMenuUtility.getBlock();
    }

    @Override
    public String getMenuName() {
        return ChatColor.AQUA + "Configure item drop chances";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        event.setCancelled(true);
        int rawSlot = event.getRawSlot();
        if (rawSlot >= 46 || Objects.equals(event.getClickedInventory(), event.getWhoClicked().getInventory()) || event.getCurrentItem() == null) {
            return;
        }

        if (rawSlot == 45) {
            new CompositionBlockMenu(playerMenuUtility).open();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.3F, 1F);
            return;
        }

        CataMineLootItem item = block.getLootTable().get(rawSlot);
        playerMenuUtility.setItem(item);
        new LootItemMenu(playerMenuUtility).open();
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.3F, 1F);
    }

    @Override
    public void setMenuItems() {
        for (int i = 0; i < block.getLootTable().size(); i++) {
            if (i >= 45) break;
            CataMineLootItem lootItem = block.getLootTable().get(i);
            ItemStack itemStack = lootItem.getItem().clone();
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> itemLore = CataMines.getInstance().getLangStringList("GUI.Loot-Table-List-Menu.Items.Drop-Item.Lore");
            itemLore.replaceAll(s -> s.replaceAll("%chance%", String.valueOf(lootItem.getChance())));
            itemMeta.setLore(itemLore);
            itemStack.setItemMeta(itemMeta);

            inventory.setItem(i, itemStack);
        }

        for (int i = 45; i < getSlots(); i++) {
            inventory.setItem(i, ItemStackBuilder.buildItem(Material.GRAY_STAINED_GLASS_PANE, " "));
        }

        inventory.setItem(45, ItemStackBuilder.buildItem(Material.ARROW, CataMines.getInstance().getLangString("GUI.Universal.Back-To-Block-Menu.Name"), CataMines.getInstance().getLangStringList("GUI.Universal.Back-To-Block-Menu.Lore")));
    }
}
