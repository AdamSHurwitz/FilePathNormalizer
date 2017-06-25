import java.util.HashMap;
import java.util.Map;

public class FilePathNormalizer {
    private static final String SINGLE_DOT = "SINGLE_DOT";
    private static final String DOUBLE_DOT = "DOUBLE_DOT";
    private String filePath = "";
    private int counter = 0;
    private int singleDotIndex;
    private int doubleDotIndex;
    private Map<Integer, String> patternMatcher = new HashMap<>();

    public FilePathNormalizer(String filePath) {
        this.filePath = filePath;
    }

    public String normalizePath() {
        while (parsing()) {
            findPatterns();
            if (getMinMatch() == -1) {
                counter = filePath.length() + 1;
            } else {
                transform(getMinMatch());
            }
        }
        return filePath;
    }

    private boolean parsing() {
        return counter < filePath.length() + 1;
    }

    private void findPatterns() {
        singleDotIndex = filePath.indexOf("./", counter);
        doubleDotIndex = filePath.indexOf("../", counter);
        patternMatcher.put(singleDotIndex, SINGLE_DOT);
        patternMatcher.put(doubleDotIndex, DOUBLE_DOT);
    }

    private void transform(int index) {
        switch (patternMatcher.get(index)) {
            case SINGLE_DOT:
                counter = index;
                filePath = filePath.substring(0, index) + filePath.substring(index + 2, filePath.length());
                normalizePath();
                break;
            case DOUBLE_DOT:
                if (index == 0) {
                    filePath = filePath.substring(2);
                } else {
                    int endAt = index - 2;
                    while (filePath.charAt(endAt) != '/') {
                        endAt--;
                    }
                    String beg = filePath.substring(0, endAt + 1);
                    String end = filePath.substring(index + 3);
                    filePath = beg + end;
                }
                normalizePath();
                break;
        }
    }

    private int getMinMatch() {
        if (singleDotIndex == -1 && doubleDotIndex == -1) {
            return -1;
        } else if (singleDotIndex == -1) {
            return doubleDotIndex;
        }
        if (doubleDotIndex == -1) {
            return singleDotIndex;
        } else {
            return Math.min(singleDotIndex, doubleDotIndex);
        }
    }
}
