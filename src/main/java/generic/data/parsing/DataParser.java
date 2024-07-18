package generic.data.parsing;

import javax.annotation.CheckForNull;

/**
 * Interface for parsing data.
 *
 * @param <D> the type of the parsed data
 */
public interface DataParser<D extends ParsedData> {

    /**
     * Parse the data. If the data cannot be parsed, null is returned.
     *
     * @param b the data to parse
     * @return the parsed data
     */
    @CheckForNull
    D parse(byte[] b);
}
