package generic.data;

import generic.data.parsing.ParsedData;

import javax.annotation.CheckForNull;
import java.util.List;

/**
 * A provider of data objects. Data objects are added to the provider and can be retrieved by the user.
 * Data objects can be retrieved in the order they were added.
 *
 * @param <D> The type of data object that this provider provides. Actual data objects in the container are of this type or a subclass of this type.
 */
public interface DataProvider<D extends ParsedData> {

    /**
     * Get the most recently added data object of the specified class.
     * @param clazz The class of the data object to get.
     * @param dataObjectIndex The index of the data object to get. 0 is the most recently added data object.
     *                        1 is the second most recently added data object, and so on.
     */
    @CheckForNull
    <T extends D> T getRecentlyAddedData(Class<T> clazz, int dataObjectIndex);

    /**
     * Get all data objects of the specified class. The data objects are returned in the order they were added.
     * @param clazz The class of the data objects to get.
     */
    <T extends D> List<T> getAllData(Class<T> clazz);

    /**
     * Get the time in milliseconds since the last data object was added.
     */
    long dataLastUpdated();

    /**
     * Clear all data objects.
     */
    void clearData();

}
