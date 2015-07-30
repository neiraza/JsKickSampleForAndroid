package to.gu.ri.jskicksampleforandroid;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by toguri on 15/07/30.
 */
public class WebViewSampleActivity extends Activity {

    WebView mWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_sample);

        mWeb = (WebView) findViewById(R.id.activity_webview_sample_web);
        mWeb.getSettings().setJavaScriptEnabled(true);

        mWeb.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Log.d("", ">>>>>>>>>>>>>>>>>loading url " + url);

                if (!TextUtils.isEmpty(url) && url.startsWith("pfx://")) {
                    int paramStartPoint = url.indexOf("?");
                    String params = paramStartPoint >= 0 ? url.substring(paramStartPoint + 1) : "";
                    Log.d("", ">>>>>>>>>>>>>>>>>loading pfx params " + params);
                    try {
                        JSONObject jsonObject = new JSONObject(params);
                        Log.d("", ">>>>>>>>>>>>>>>>>loading pfx params(JSON) " + jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(final WebView view, String url) {
                Log.d("", ">>>>>>>>>>>>>>>>>page finished url.... " + url);

                WebViewSampleActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.loadUrl("javascript:getJson()");
                    }
                });
            }
        });

//        String data = "<html><head><script>function getJson(){var xmlHttp = new XMLHttpRequest();xmlHttp.open(\"GET\", \"https://dl.dropboxusercontent.com/u/24338547/fuga.json\", false);xmlHttp.send(null);return xmlHttp.responseText;}</script></head><body></body></html>";
//        String data = "<html><head><script>function getJson(){document.location = \"pfx://fuga?wwwwwwwwww\";}</script></head><body></body></html>";
        String data = "<html><head><script>function getJson(){var xmlHttp = new XMLHttpRequest();xmlHttp.open(\"GET\", \"https://dl.dropboxusercontent.com/u/24338547/fuga.json\", false);xmlHttp.send(null);document.location = \"pfx://fuga?\" + xmlHttp.responseText;}</script></head><body></body></html>";
        mWeb.loadData(data, "text/html", "UTF-8");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
