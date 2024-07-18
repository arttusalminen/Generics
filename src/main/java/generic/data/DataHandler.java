package generic.data;

import generic.data.parsing.ParsedData;

/**
 * Interface for handling data of a specific type
 *
 * @param <D> The type of data to handle
 */
public interface DataHandler<D extends ParsedData> {

    /**
     * Handle the data
     *
     * @param data The data to handle
     */
    void handleData(D data);

    /**
     * Get the class of the data type
     *
     * @return The class of the data type
     */
    Class<D> getDataType();
}
