/**
 * Calling getInstance that creates object of class CaseInsensitiveIndex.
 */
public class CaseInsensitiveFactory extends AbstractInvertedIndexFactory {
    /**
     * Creates the inverted index object using singleton implementation.
     * @return Object of caseInsensitiveIndex.
     */
    @Override
    public AbstractInvertedIndex createInvertedIndex() {
        return CaseInsensitiveIndex.getInstance();
    }
}
