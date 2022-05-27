package de.c4t4lysm.catamines.commands.cmcommands;

import de.c4t4lysm.catamines.CataMines;
import de.c4t4lysm.catamines.commands.CommandInterface;
import de.c4t4lysm.catamines.schedulers.MineManager;
import de.c4t4lysm.catamines.utils.Utils;
import de.c4t4lysm.catamines.utils.mine.mines.CuboidCataMine;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.File;

public class DeleteCommand implements CommandInterface {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {

        if (!sender.hasPermission("catamines.delete")) {
            sender.sendMessage(CataMines.PREFIX + CataMines.getInstance().getLangString("Error-Messages.No-Permission"));
            return true;
        }

        if (args.length == 2) {
            if (!MineManager.getInstance().getMineListNames().contains(args[1])) {
                sender.sendMessage(CataMines.PREFIX + CataMines.getInstance().getLangString("Error-Messages.Mine.Not-Exist"));
                return true;
            }

            CuboidCataMine cuboidCataMineToDelete = MineManager.getInstance().getMine(args[1]);
            MineManager.getInstance().getMines().removeIf(mine -> mine.getName().equals(args[1]));
            File file = new File(CataMines.getInstance().getDataFolder() + "/mines/" + cuboidCataMineToDelete.getName() + ".yml");
            file.delete();

            sender.sendMessage(CataMines.PREFIX + CataMines.getInstance().getLangString("Commands.Delete").replaceAll("%mine%", args[1]));

            Utils.updateMenus();

        } else sender.sendMessage(CataMines.PREFIX + "/cm delete <mine>");

        return true;
    }

}
