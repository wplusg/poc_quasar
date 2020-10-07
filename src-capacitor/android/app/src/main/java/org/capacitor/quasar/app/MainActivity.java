package org.capacitor.quasar.app;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.getcapacitor.BridgeActivity;
import com.getcapacitor.Plugin;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class MainActivity extends BridgeActivity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Initializes the Bridge
    this.init(savedInstanceState, new ArrayList<Class<? extends Plugin>>() {{
      // Additional plugins you've installed go here
      // Ex: add(TotallyAwesomePlugin.class);
    }});

    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, instanceIdResult -> {
      String token = instanceIdResult.getToken();
      AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

      builder.setTitle("FCM Token");
      builder.setMessage(token);
      builder.setPositiveButton("Copy to clipboard", (dialog, which) -> {
        ClipboardManager clipboard = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
          clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
          ClipData clip = ClipData.newPlainText("FCM Token", token);
          clipboard.setPrimaryClip(clip);

          Toast.makeText(MainActivity.this, "Token copied to clipboard", Toast.LENGTH_SHORT).show();
        }
      });
      builder.setNegativeButton("Close", null);

      builder.show();
    });
  }
}
