package mt.naveentesting;

import android.annotation.SuppressLint;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {


    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.newLayout)
    RelativeLayout newLayout;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        System.out.println("Web view loading");

        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportMultipleWindows(true);
        //  webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.loadUrl("https://ievolve.ultimatix.net/ScormEngineInterface/defaultui/launch.jsp?registration=ContentId|3804!UserId|891403&cc=en_US&configuration=");

        //  webView.loadUrl("https://ievolveuat.ultimatix.net/ScormEngineInterface/defaultui/launch.jsp?registration=ContentId|3433!UserId|107019&cc=en_US&configuration=");
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, android.os.Message resultMsg) {

                System.out.println("Creating windows");

                WebView newView = new WebView(WebViewActivity.this);
                newView.setWebViewClient(new MyBrowser());

                newView.getSettings().setJavaScriptEnabled(true);
                newView.getSettings().setDomStorageEnabled(true);
                newView.getSettings().setSupportMultipleWindows(true);
                newView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                newView.getSettings().setAllowFileAccess(true);
                // Create dynamically a new view
                // newView.setLayoutParams(new RelativeLayout.LayoutParams(500,500));

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                newView.setLayoutParams(params);
                newLayout.addView(newView);
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newView);
                resultMsg.sendToTarget();


                newView.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, android.os.Message resultMsg) {

                        System.out.println("Creating windows1111");

                        WebView newView1 = new WebView(WebViewActivity.this);
                        newView1.setWebViewClient(new MyBrowser());

                        newView1.getSettings().setJavaScriptEnabled(true);
                        newView1.getSettings().setDomStorageEnabled(true);
                        newView1.getSettings().setSupportMultipleWindows(true);

                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                        newView1.setLayoutParams(params);
                        newLayout.addView(newView1);
                        WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                        transport.setWebView(newView1);
                        resultMsg.sendToTarget();
                        return true;
                    }
                });


                return true;
            }
        });

	   /* 	  @Override
            public boolean onJsAlert(WebView view, String url, String message,
	    			JsResult result) {
	    		// TODO Auto-generated method stub
	    		return super.onJsAlert(view, url, message, result);
	    	}
	    	});*/


        //  wv1.loadUrl("https://ievolveuat.ultimatix.net/ScormEngineInterface/defaultui/launch.jsp?registration=ContentId|3433!UserId|107019&cc=en_US&configuration=");
        //  wv1.loadUrl("https://contentuat.ultimatix.net/ScormEngineInterface/defaultui/deliver.jsp?preventRightClick=false&cc=&configuration=&ieCompatibilityMode=none&forceReview=false&tracking=true&package=ContentId%7C3433%21VersionId%7C0&registration=ContentId%7C3433%21InstanceId%7C0%21UserId%7C107019");
//	   }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            System.out.println("Loading URL " + url);
            view.loadUrl(url);
            return true;
        }


        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            // TODO Auto-generated method stub
            System.out.println("ERROR " + description);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

	   /*   @Override
          public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                   System.out.println();
              }*/

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                       SslError error) {
            // TODO Auto-generated method stub
            System.out.println("SSL ERROR " + error);
            //super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
