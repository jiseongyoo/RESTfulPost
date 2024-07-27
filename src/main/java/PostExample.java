import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PostExample {
    public static final String TARGET_URL = "http://localhost:8080/example";
    private URL url;
    private String readLine;

    private HttpURLConnection urlConnection;
    private final int CONNECTION_TIMEOUT = 5000;
    private final int READ_TIMEOUT = 3000;

    public PostExample() throws IOException {
        url = new URL(TARGET_URL);

        urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
        urlConnection.setReadTimeout(READ_TIMEOUT);
        urlConnection.setRequestProperty("Content-Type", "application/json;");
        urlConnection.setDoOutput(true);
        urlConnection.setInstanceFollowRedirects(true);
    }

    public void post() {
        String sendData = "한글로 된 데이터";

        try (OutputStream outputStream = urlConnection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {

            bufferedWriter.write(sendData);
            bufferedWriter.flush(); // 실제로 POST가 되는 곳
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (InputStream inputStream = urlConnection.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            StringBuilder buffer = new StringBuilder();

            buffer.append("code : " + urlConnection.getResponseCode());
            buffer.append(", message : " + urlConnection.getResponseMessage() + "\n");

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                while((readLine = bufferedReader.readLine()) != null) {
                    buffer.append(readLine).append("\n");
                }
            }

            System.out.println("Response : " + buffer);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
