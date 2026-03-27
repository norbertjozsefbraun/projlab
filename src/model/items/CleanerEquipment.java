package model.items;
import model.map.Field;

/**
 * Interface representing cleaner equipment that can clean a field.
 */
public interface CleanerEquipment {
    void clean(Field f);
}