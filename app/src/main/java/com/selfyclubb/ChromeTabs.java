package com.selfyclubb;

import android.Manifest;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChromeTabs extends AppCompatActivity {
    private static final int PICK_MEDIA_REQUEST = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 101;
    private ValueCallback<Uri[]> fileUploadCallback;
    private static final int FILE_CHOOSER_REQUEST_CODE = 100;
    private static final String BASE_URL = "https://selfyclub.com/welcome";
    private static final String GOOGLE_LOGIN_URL = "https://accounts.google.com/signin/oauth";
    private WebView webView;
    private static Boolean LOADING_STATUS = false;
    TextView errorText, uploadProgressText;
    LinearLayout uploadProgressLayout;
    FrameLayout loader;
    ProgressBar uploadProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vhrome_tabs);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        errorText = findViewById(R.id.errorText);
        loader = findViewById(R.id.loader);
        webView = findViewById(R.id.webview);
        uploadProgressLayout = findViewById(R.id.uploadProgressLayout);
        uploadProgressBar = findViewById(R.id.uploadProgressBar);
        uploadProgressText = findViewById(R.id.uploadProgressText);

        // Handle shared media if app is opened via sharing
        if (Intent.ACTION_SEND.equals(getIntent().getAction()) && getIntent().getType() != null) {
            handleSharedMedia(getIntent());
        } else {
            setupWebView();
            requestStoragePermission();
            requestPermissions();
//            pickMedia();
        }
    }

    public static boolean connectionCheck(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                if (networkCapabilities != null) {
                    if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true; // Connected to Wi-Fi or Mobile Data
                    }
                }
            }
        }
        Dialog loaddialog = new Dialog(context);
        loaddialog.setContentView(R.layout.dialog_connection_error);
        loaddialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loaddialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        loaddialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        loaddialog.getWindow().setGravity(Gravity.CENTER);
        loaddialog.setCancelable(false);
        loaddialog.setCanceledOnTouchOutside(false);
        loaddialog.show();
        Button retryBtn = loaddialog.findViewById(R.id.retryBtn);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaddialog.dismiss();
            }
        });
        return false;
    }

    private void openChromeTab(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();

        // Open URL in Chrome Tabs with cookies if available
        String cookies = CookieManager.getInstance().getCookie(BASE_URL);
        Log.d("Cookies", "Passing Cookies to Chrome: " + cookies);

        // Launch Chrome Custom Tab
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (Intent.ACTION_SEND.equals(intent.getAction()) && intent.getType() != null) {
            handleSharedMedia(intent);
        }
    }

    private void setupWebView() {
        if(connectionCheck(ChromeTabs.this)){
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setSupportMultipleWindows(true);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setAllowContentAccess(true);
            webSettings.setMediaPlaybackRequiresUserGesture(false); // Allow audio/video autoplay
            webSettings.setDatabaseEnabled(true);
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    loader.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    loader.setVisibility(View.GONE);
                    String cookies = CookieManager.getInstance().getCookie(BASE_URL);
                    String userId = extractUserId(cookies);
                    if (!url.equals(BASE_URL)) {
                        if (userId != null) {
                            openChromeTab(BASE_URL);
                        }
                    }
                }
            });

            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                    if (resultMsg == null || !(resultMsg.obj instanceof WebView.WebViewTransport)) {
                        return false;
                    }

                    WebView newWebView = new WebView(ChromeTabs.this);
                    newWebView.getSettings().setJavaScriptEnabled(true);
                    newWebView.getSettings().setDomStorageEnabled(true);
                    newWebView.getSettings().setSupportMultipleWindows(true);
                    newWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

                    newWebView.setWebViewClient(new WebViewClient());
                    newWebView.setWebChromeClient(new WebChromeClient());

                    Dialog dialog = new Dialog(ChromeTabs.this);
                    dialog.setContentView(newWebView);
                    dialog.setCancelable(true);
                    dialog.show();

                    WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                    transport.setWebView(newWebView);
                    resultMsg.sendToTarget();
                    return true;
                }

                // Handle file uploads (Camera, Gallery, Files)
                @Override
                public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                    if (fileUploadCallback != null) {
                        fileUploadCallback.onReceiveValue(null);
                    }
                    fileUploadCallback = filePathCallback;

                    Intent intent = fileChooserParams.createIntent();
                    try {
                        startActivityForResult(intent, FILE_CHOOSER_REQUEST_CODE);
                    } catch (ActivityNotFoundException e) {
                        fileUploadCallback = null;
                        return false;
                    }
                    return true;
                }

                // Grant permissions for microphone & camera access
                @Override
                public void onPermissionRequest(final PermissionRequest request) {
                    runOnUiThread(() -> request.grant(request.getResources())); // Auto grant all permissions
                }
            });
            webView.loadUrl(BASE_URL);
        }
    }

    private void openChromeAccountPicker() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://accounts.google.com/AccountChooser"));
            intent.setPackage("com.android.chrome");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Chrome not installed!", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickMedia() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
        startActivityForResult(intent, PICK_MEDIA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_MEDIA_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedUri = data.getData();
            if (selectedUri != null) {
                uploadFileToWoWonder(selectedUri);
//                getCookies(BASE_URL);
            }
        }
        if (requestCode == FILE_CHOOSER_REQUEST_CODE) {
            if (fileUploadCallback != null) {
                Uri[] result = (resultCode == RESULT_OK && data != null) ? new Uri[]{data.getData()} : null;
                fileUploadCallback.onReceiveValue(result);
                fileUploadCallback = null;
            }
        }
    }

    private void handleSharedMedia(Intent intent) {
        Uri fileUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (fileUri != null) {
            uploadFileToWoWonder(fileUri);
        }
    }

    private void uploadFileToWoWonder(Uri fileUri) {
        if(connectionCheck(ChromeTabs.this)){
            if(webView.getUrl().equals(BASE_URL)){
                Dialog dialog = new Dialog(ChromeTabs.this);
                dialog.setContentView(R.layout.dialog_error);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                TextView msg = dialog.findViewById(R.id.msgDialog);
                msg.setText("Please Login First!!!");
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                },4000);
                return;
            }
            runOnUiThread(() -> {
                uploadProgressLayout.setVisibility(View.VISIBLE);
                uploadProgressBar.setProgress(0);
                uploadProgressText.setText("Uploading 0%");
            });

            new Thread(() -> {
                try {
                    // Extract user_id from cookies
                    String cookies = CookieManager.getInstance().getCookie(BASE_URL);
                    String userId = extractUserId(cookies);
                    if (userId == null) {
                        runOnUiThread(() ->{
                            Dialog dialog = new Dialog(ChromeTabs.this);
                            dialog.setContentView(R.layout.dialog_error);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            dialog.getWindow().setGravity(Gravity.CENTER);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setCancelable(false);
                            TextView msg = dialog.findViewById(R.id.msgDialog);
                            msg.setText("Please Login First!!!");
                            dialog.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                }
                            },4000);
                        });
                    }

                    String mimeType = getContentResolver().getType(fileUri);
                    boolean isVideo = mimeType != null && mimeType.startsWith("video");
                    String uploadUrl = "https://selfyclub.com/api/new_post?access_token=" + userId;

                    RequestBody fileBody = getFileRequestBody(fileUri);
                    if (fileBody == null) {
                        runOnUiThread(() -> {
                            Dialog dialog = new Dialog(ChromeTabs.this);
                            dialog.setContentView(R.layout.dialog_error);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            dialog.getWindow().setGravity(Gravity.CENTER);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setCancelable(false);
                            TextView msg = dialog.findViewById(R.id.msgDialog);
                            msg.setText("Invalid Media File!!!");
                            dialog.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                }
                            },4000);
                        });
                        return;
                    }

                    // Wrap fileBody inside CountingRequestBody for progress tracking
                    CountingRequestBody countingBody = new CountingRequestBody(fileBody, (bytesWritten, contentLength) -> {
                        if (contentLength > 0) {
                            int progress = (int) ((100 * bytesWritten) / contentLength);
                            runOnUiThread(() -> {
                                uploadProgressBar.setProgress(progress);
                                uploadProgressText.setText("Uploading " + progress + "%");
                            });
                        }
                    });

                    OkHttpClient client = new OkHttpClient();

                    MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("server_key", "f9f727042fa90c6b6649d7f53ea9b1b4");

                    if (isVideo) {
                        requestBodyBuilder.addFormDataPart("postVideo", "video.mp4", countingBody);
                    } else {
                        requestBodyBuilder.addFormDataPart("postPhotos[]", "image.jpg", countingBody);
                    }

                    RequestBody requestBody = requestBodyBuilder.build();
                    Request request = new Request.Builder()
                            .url(uploadUrl)
                            .post(requestBody)
                            .build();

                    Response response = client.newCall(request).execute();
                    String responseBody = response.body().string();

                    runOnUiThread(() -> {
                        Dialog dialog = new Dialog(ChromeTabs.this);
                        dialog.setContentView(R.layout.dialog_success);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        dialog.getWindow().setGravity(Gravity.CENTER);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(false);
                        TextView msg = dialog.findViewById(R.id.msgDialog);
                        msg.setText("Uploaded Successfully!!!");
                        dialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                uploadProgressLayout.setVisibility(View.GONE);
                                openChromeTab(BASE_URL);
                                dialog.dismiss();
                            }
                        },4000);

                    });

                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        uploadProgressLayout.setVisibility(View.GONE);
                    });
                }
            }).start();
        }
    }

    private String extractUserId(String cookies) {
        if (cookies != null) {
            Log.d("Cookies", "Cookies: " + cookies);
            Pattern pattern = Pattern.compile("user_id=([^;]+)");
            Matcher matcher = pattern.matcher(cookies);

            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    private RequestBody getFileRequestBody(Uri fileUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            byte[] fileBytes = new byte[inputStream.available()];
            inputStream.read(fileBytes);
            inputStream.close();
            return RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_MEDIA_IMAGES},
                    REQUEST_STORAGE_PERMISSION);
        }
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                pickMedia(); // Restart media picking after permission is granted
//                webView.loadUrl(GOOGLE_LOGIN_URL);
            } else {
                Toast.makeText(this, "Storage permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack(); // Go to the previous page
        } else {
            super.onBackPressed(); // Exit the activity
        }
    }
}