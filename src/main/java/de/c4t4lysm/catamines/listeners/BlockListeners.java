package de.c4t4lysm.catamines.listeners;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import de.c4t4lysm.catamines.schedulers.MineManager;
import de.c4t4lysm.catamines.utils.mine.mines.CuboidCataMine;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.util.BoundingBox;

public class BlockListeners implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDamageItem(final PlayerItemDamageEvent event) {
        if (event.isCancelled() || MineManager.getInstance().tasksStopped()) {
            return;
        }

        for (CuboidCataMine mine : MineManager.getInstance().getMines()) {
            if (mine.isStopped() || mine.getRegion() == null) {
                continue;
            }

            /*
             * A little weird, but because the event doesn't immediately let us get the source of the damage,
             * get an expanded region of the mine (based on default player reach) and check if the player is in it.
             */
            int playerReach = event.getPlayer().getGameMode().equals(GameMode.CREATIVE) ? 5 : 4;
            CuboidRegion expandedMineRegion = mine.getRegion().clone().getBoundingBox();
            BlockVector3 expandVector = BlockVector3.at(playerReach, playerReach, playerReach);
            expandedMineRegion.expand(expandVector, expandVector.multiply(-1));
            final BlockVector3 playerPos = BlockVector3.at(
                    event.getPlayer().getLocation().getX(),
                    event.getPlayer().getLocation().getY(),
                    event.getPlayer().getLocation().getZ()
            );

            System.out.println(expandedMineRegion.toString());
            System.out.println(playerPos.toString());

            if (event.getPlayer().getWorld().getName().equals(mine.getWorld())
                    && expandedMineRegion.contains(playerPos)) {
                mine.handlePlayerItemDamage(event);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        //TODO: Might want to revert to checking the gamemode
        if (event.isCancelled() || MineManager.getInstance().tasksStopped()) {
            return;
        }

        Location blockLocation = event.getBlock().getLocation();

        for (CuboidCataMine mine : MineManager.getInstance().getMines()) {
            if (mine.isStopped() || mine.getRegion() == null) {
                continue;
            }
            if (blockLocation.getWorld().getName().equals(mine.getWorld())
                    && mine.getRegion().contains(BlockVector3.at(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ()))) {
                mine.handleBlockBreak(event);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace(BlockPlaceEvent event) {
        if (event.isCancelled() || MineManager.getInstance().tasksStopped()) {
            return;
        }

        Location blockLocation = event.getBlock().getLocation();

        for (CuboidCataMine mine : MineManager.getInstance().getMines()) {
            if (mine.isStopped() || mine.getRegion() == null) {
                continue;
            }
            if (blockLocation.getWorld().getName().equals(mine.getWorld())
                    && mine.getRegion().contains(BlockVector3.at(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ()))) {
                mine.setBlockCount(mine.getBlockCount() + 1);
            }
        }
    }


}
