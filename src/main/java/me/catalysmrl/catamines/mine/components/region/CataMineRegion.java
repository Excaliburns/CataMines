package me.catalysmrl.catamines.mine.components.region;

import me.catalysmrl.catamines.mine.components.composition.CataMineComposition;
import me.catalysmrl.catamines.mine.components.manager.choice.ChoiceManager;
import me.catalysmrl.catamines.mine.components.manager.choice.Identifiable;
import me.catalysmrl.catamines.mine.reward.Rewardable;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface CataMineRegion extends Rewardable, Identifiable, ConfigurationSerializable {

    String getName();

    void setName(String name);

    void fill();

    RegionType getType();

    ChoiceManager<CataMineComposition> getCompositionManager();

    long getVolume();

    enum RegionType {
        SELECTION,
        SCHEMATIC
    }
}