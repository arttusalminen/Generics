package generic.data;

import com.google.common.collect.ImmutableList;

import javax.annotation.CheckForNull;
import java.util.*;

/**
 * A wrapper for a collection of data that is saved in the order it was added.
 *
 * @param <T> the type of the data
 */
public class DataContainer<T> {
    private final Deque<T> allData = new ArrayDeque<>();

    public void add(T data) {
        allData.add(data);
    }

    public List<T> getAllData() {
        return ImmutableList.copyOf(allData);
    }

    @CheckForNull
    public T getMostRecentData(int countFromMostRecentBackwards) {
        if (allData.size() <= countFromMostRecentBackwards) {
            return null;
        }
        else {
            Iterator<T> iterator = allData.descendingIterator();
            for (int i = 0; i < countFromMostRecentBackwards; i++) {
                iterator.next();
            }
            return iterator.next();
        }
    }

    public void clear() {
        allData.clear();
    }
}
