package generic.data.recording;

import generic.data.DataContainer;
import generic.data.DataHandler;
import generic.data.DataProvider;
import generic.data.parsing.ParsedData;

import javax.annotation.CheckForNull;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class that records data of a specific type. The data can be retrieved by the user in the order it was added.
 *
 * @param <D> The type of data to record. Actual recorded data is of this type or a subclass of this type.
 */
public class DataRecorder<D extends ParsedData> implements DataHandler<D>, DataProvider<D> {

    private final Class<D> recordedDataType;
    private final ConcurrentHashMap<Class<D>, DataContainer<D>> allRecordedData = new ConcurrentHashMap<>();
    private long lastReceivedMs;

    public DataRecorder(Class<D> recordedDataType) {
        this.recordedDataType = recordedDataType;
    }

    @Override
    public synchronized void handleData(D data) {
        lastReceivedMs = System.currentTimeMillis();

        if (!allRecordedData.containsKey(data.getClass())) {
            allRecordedData.put((Class<D>) data.getClass(), new DataContainer<>());
        }
        allRecordedData.get(data.getClass()).add(data);
    }

    @Override
    public Class<D> getDataType() {
        return recordedDataType;
    }

    @CheckForNull
    @Override
    public <T extends D> T getRecentlyAddedData(Class<T> clazz, int dataObjectIndex) {
        if (allRecordedData.containsKey(clazz)) {
            return (T) allRecordedData.get(clazz).getMostRecentData(dataObjectIndex);
        }
        return null;
    }

    public synchronized <T extends D> List<T> getAllData(Class<T> clazz) {
        if (allRecordedData.containsKey(clazz)) {
            return (List<T>) List.copyOf(allRecordedData.get(clazz).getAllData());
        }
        return List.of();
    }

    @Override
    public long dataLastUpdated() {
        return lastReceivedMs;
    }

    @Override
    public synchronized void clearData() {
        allRecordedData.clear();
    }
}
