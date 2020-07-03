/**
 * Calling getInstance that creates object of class CaseSensitiveIndex.
 */
public class CaseSensitiveFactory extends AbstractInvertedIndexFactory {
    /**
     * Creates the inverted index object using singleton implementation.
     * @return Object of caseSensitiveIndex.
     */
    @Override
    public AbstractInvertedIndex createInvertedIndex() {
        return CaseSensitiveIndex.getInstance();
    }
}
