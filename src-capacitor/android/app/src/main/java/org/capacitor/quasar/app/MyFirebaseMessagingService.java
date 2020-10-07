package org.capacitor.quasar.app;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

import androidx.annotation.NonNull;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
  @Override
  public void onNewToken(@NonNull String s) {
    Log.d("TAG", "The token refreshed: " + s);
  }
}
