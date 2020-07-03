import java.io.File;
import java.util.TreeSet;

/**
 * Case insensitive class to handle the case insensitive queries.
 */
public class CaseInsensitiveIndex extends AbstractInvertedIndex {
    private static CaseInsensitiveIndex instance;

    private CaseInsensitiveIndex() {
    }

    /**
     * Singleton implementation of the class instance.
     *
     * @return Object of this class.
     */
    public static CaseInsensitiveIndex getInstance() {
        if (instance == null) {
            instance = new CaseInsensitiveIndex();
            System.out.println("New CaseInsensitive index is created");
        } else {
            System.out.println("You already have a CaseInsensitive index");
        }
        return instance;
    }

    @Override
    public void buildInvertedIndex(File[] listFiles) {
        for (File file : listFiles) {
            boolean textBegin = false;
            String docNo = file.getName();
            for (String line : Utils.readLines(file)) {
                if (line.equals("<TEXT>")) {
                    textBegin = true;
                    continue;
                }
                if (line.equals("</TEXT>")) {
                    textBegin = false;
                    continue;
                }
                if (textBegin) {
                    for (String word : Utils.splitBySpace(line)) {
                        word = word.toLowerCase(); // turns all the words in the document into lowercase.
                        TreeSet<String> docsSet;
                        if (invertedIndex.containsKey(word)) {
                            docsSet = invertedIndex.get(word);
                        } else {
                            docsSet = new TreeSet<>();
                        }
                        docsSet.add(docNo);
                        invertedIndex.put(word, docsSet);
                    }
                }
            }
        }
    }

    @Override
    public TreeSet<String> runQuery(String query) {
        String[] queryArray = Utils.splitBySpace(query);
        if (queryArray.length == 1) {
            return invertedIndex.get(query.toLowerCase());
        }
        String word1 = "";
        TreeSet<String> docs = new TreeSet<>();
        for (int i = 1; i < queryArray.length; i++) {
            if (i >= 2) {
                word1 = queryArray[i - 2].toLowerCase();
            }
            String word2 = queryArray[i - 1].toLowerCase();
            switch (queryArray[i]) {
                case "OR": {
                    TreeSet<String> union = new TreeSet<>();
                    if (invertedIndex.containsKey(word1)) {
                        union.addAll(invertedIndex.get(word1));
                    }
                    if (invertedIndex.containsKey(word2)) {
                        union.addAll(invertedIndex.get(word2));
                    }
                    if (i + 1 <= queryArray.length - 1) {
                        if (queryArray[i + 1].equals("NOT")) {
                            docs.removeAll(union);
                            i++;
                            continue;
                        }
                    }
                    docs.addAll(union);
                    break;
                }
                case "AND": {
                    TreeSet<String> intersections = new TreeSet<>();
                    if (invertedIndex.containsKey(word1) && invertedIndex.containsKey(word2)) {
                        intersections.addAll(invertedIndex.get(word1));
                        intersections.retainAll(invertedIndex.get(word2));
                    }
                    if (i + 1 <= queryArray.length - 1) {
                        if (queryArray[i + 1].equals("NOT")) {
                            docs.removeAll(intersections);
                            i++;
                            continue;
                        }
                    }
                    docs.addAll(intersections);
                    break;
                }
                case "NOT":
                    docs.removeAll(invertedIndex.get(word2));
                    break;
            }
        }
        return docs;
    }
}