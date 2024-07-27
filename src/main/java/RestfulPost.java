import java.io.*;

public class RestfulPost {
    public static void main(String[] args) throws IOException, InterruptedException {
        PostExample postExample = new PostExample();
        for (int repeat = 0; repeat < 10; repeat++) {
            postExample.post("한글로 된 데이터");
            Thread.sleep(1000);
        }
    }
}
