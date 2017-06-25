/**
 * Created by ahurwitz on 6/24/17.
 */
public class FilePathNormalizerTest {

    public FilePathNormalizerTest(){
    }

    public void runTests() {
        test("foo/./bar", "foo/bar");
        test("foo/./bar/foo/./bar", "foo/bar/foo/bar");
        test("foo/bar/../baz", "foo/baz");
        test("foo/bar/../baz/foo/bar/../baz", "foo/baz/foo/baz");
        test("../foo/bar", "/foo/bar");
        test("../foo/bar/foo/./bar/", "/foo/bar/foo/bar/");
        test("../foo/bar/foo/./bar/foo/bar/../baz", "/foo/bar/foo/bar/foo/baz");
    }

    public void test(String filePath, String expected){
        FilePathNormalizer filePathNormalizer = new FilePathNormalizer(filePath);
        System.out.println("Pass: " + filePathNormalizer.normalizePath().equals(expected)
                + " | Input: " + filePath
                + " | Expected: " + expected
                + " | Actual: " + filePathNormalizer.normalizePath());
    }
}
