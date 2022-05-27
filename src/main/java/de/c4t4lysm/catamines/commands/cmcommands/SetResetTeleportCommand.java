package de.c4t4lysm.catamines.commands.cmcommands;

import de.c4t4lysm.catamines.CataMines;
import de.c4t4lysm.catamines.commands.CommandInterface;
import de.c4t4lysm.catamines.schedulers.MineManager;
import de.c4t4lysm.catamines.utils.mine.mines.CuboidCataMine;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetResetTeleportCommand implements CommandInterface {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(CataMines.PREFIX + CataMines.getInstance().getLangString("Error-Messages.Only-Players"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("catamines.setresetteleport")) {
            player.sendMessage(CataMines.PREFIX + CataMines.getInstance().getLangString("Error-Messages.No-Permission"));
            return true;
        }

        if (args.length != 2) {
            player.sendMessage(CataMines.PREFIX + "§b/cm setresettp <mine>");
            return true;
        }

        if (!MineManager.getInstance().getMineListNames().contains(args[1])) {
            player.sendMessage(CataMines.PREFIX + CataMines.getInstance().getLangString("Error-Messages.Mine.Not-Exist"));
            return true;
        }

        CuboidCataMine cataMine = MineManager.getInstance().getMine(args[1]);
        cataMine.setTeleportResetLocation(player.getLocation());
        cataMine.setTeleportPlayersToResetLocation(true);
        cataMine.save();
        player.sendMessage(CataMines.PREFIX + CataMines.getInstance().getLangString("Commands.Set-Reset-Teleport").replaceAll("%mine%", args[1]));


        return true;
    }
}
