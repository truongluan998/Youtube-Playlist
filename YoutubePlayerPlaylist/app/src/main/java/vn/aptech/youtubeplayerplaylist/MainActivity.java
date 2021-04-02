package vn.aptech.youtubeplayerplaylist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String API_KEY = "AIzaSyAhzhBqBtuhmrSh6r1mzITJBP1sUN56UCo";
    String ID_PLAY_LIST = "PL5PoPP0-BugOxLmSY0wyBm6JtxOihediy";
    String URL_GET_JSON = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" +ID_PLAY_LIST+ "&key=" +API_KEY+ "&maxResult=50";
    ListView lvVideo;
    ArrayList<VideoYoutube> videoArr;
    VideoApdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetJSONYoutube(URL_GET_JSON);

        lvVideo = findViewById(R.id.lv_video);
        videoArr = new ArrayList<>();
        videoAdapter = new VideoApdapter(this, R.layout.row_video_youtube, videoArr);

        lvVideo.setAdapter(videoAdapter);

        lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, PlayVideoActivity.class);
                intent.putExtra("idVideoYoutube", videoArr.get(i).getIdVideo());
                startActivity(intent);

            }
        });


    }

    private void GetJSONYoutube(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArrayItems = response.getJSONArray("items");

                            String title = "";
                            String url = "";
                            String idVideo = "";

                            for (int i = 0; i < jsonArrayItems.length(); i++) {
                                JSONObject jsonObjectItem = jsonArrayItems.getJSONObject(i);
                                JSONObject jsonObjectSnippet = jsonObjectItem.getJSONObject("snippet");
                                title = jsonObjectSnippet.getString("title");

                                JSONObject jsonObjectThumbnails = jsonObjectSnippet.getJSONObject("thumbnails");

                                JSONObject jsonObjectMedium = jsonObjectThumbnails.getJSONObject("medium");

                                url = jsonObjectMedium.getString("url");

                                JSONObject jsonObjectResourceID = jsonObjectSnippet.getJSONObject("resourceId");

                                idVideo = jsonObjectResourceID.getString("videoId");

                                videoArr.add(new VideoYoutube(title, url, idVideo));

                            }
                            videoAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonObjectRequest);

    }

}