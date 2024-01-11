package Powered_by.springboot.APISport;
import okhttp3.*;
import java.io.IOException;

public class APIClient {

    private final OkHttpClient client;

    public APIClient() {
        this.client = new OkHttpClient();
    }

    public String getData(String endpoint) throws Exception {
        String apiUrl = "https://v2.nba.api-sports.io/" + endpoint;
 // e192b1c9e1b945372893787b520ccade dani
        //745e9d0949114fdba6fc415b1b3d66b2 eddy
        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("x-apisports-key", "e192b1c9e1b945372893787b520ccade")
                .addHeader("x-rapidapi-host", "v2.nba.api-sports.io")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to retrieve data: " + response.code());
            }

            return response.body().string();
        }
    }
}


