import java.io.File;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Consists some methods for the inverted index classes and stored the HashMap that
 * contains the docs set.
 */
public abstract class AbstractInvertedIndex {
    public HashMap<String, TreeSet<String>> invertedIndex = new HashMap<>();

    /**
     * Builds the inverted index hashmap that maps a word to the documents that contains it.
     *
     * @param listFiles A list of files to read.
     */
    public abstract void buildInvertedIndex(File[] listFiles);

    /**
     * Runs the selected query and find the relevant documents.
     *
     * @param query A string to look for.
     * @return A set of relevant documents that answers the query.
     */
    public abstract TreeSet<String> runQuery(String query);
}
