package me.catalysmrl.catamines.mine.components.region;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionSelector;
import me.catalysmrl.catamines.mine.abstraction.region.AbstractCataMineRegion;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectionRegion extends AbstractCataMineRegion {

    private SelectionType selectionType;
    private Region region;
    private List<BlockVector3> vertices;

    /**
     * Creates a CataMineRegion wrapping a WorldEdit Region.
     * On creation of a mine the selection of the player will
     * be taken.
     *
     * @param region   a WorldEdit region
     * @param selector a Selection
     */
    public SelectionRegion(Region region, RegionSelector selector) {
        this.selectionType = SelectionType.getType(selector.getTypeName());
        this.region = region.clone();
        this.vertices = new ArrayList<>(selector.getVertices());
    }

    @Override
    public void fill() {

    }

    @Override
    public RegionType getType() {
        return RegionType.SELECTION;
    }

    @Override
    public @NotNull Map<String, Object> serialize() {
        return null;
    }

    /**
     * Used for deserialization of a ConfigurationSerializable class.
     * Returns an instance of a CataMineRegion constructed from a map
     * provided by {@link org.bukkit.configuration.serialization.ConfigurationSerialization}.
     *
     * @param serializedRegion the serialized object as a Map
     * @return a new CataMineRegion instance constructed from the map
     */
    public static SelectionRegion deserialize(Map<String, Object> serializedRegion) {
        return null;
    }

    public enum SelectionType {

        NONE("none"),
        CUBOID("cuboid"),
        CYLINDER("Cylinder"),
        ELLIPSOID("ellipsoid"),
        SPHERE("sphere"),
        POLYGONAL2D("2Dx1D polygon"),
        CONVEXPOLYHEDRAL("Convex Polyhedron");

        private final String typeName;

        SelectionType(String typeName) {
            this.typeName = typeName;
        }

        public static SelectionType getType(String typeName) {
            for (SelectionType type : values()) {
                if (type.typeName.equalsIgnoreCase(typeName)) {
                    return type;
                }
            }

            return NONE;
        }

    }
}