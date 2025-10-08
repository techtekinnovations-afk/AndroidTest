package com.hacktober.luck;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button b1;
    public FirebaseFirestore db;
    SharedPreferences sharedPref;
    TextView tv;

    public void storeFlag(String data) {
        SharedPreferences.Editor editor = this.sharedPref.edit();
        editor.putString("Flag", data);
        editor.putInt("Unlocked", loadCharsUnlocked(this.sharedPref) + 1);
        editor.apply();
    }

    public String loadFlag(SharedPreferences sharedPref2) {
        return sharedPref2.getString("Flag", "");
    }

    public int loadCharsUnlocked(SharedPreferences sharedPref2) {
        return sharedPref2.getInt("Unlocked", 0);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), new MainActivity$$ExternalSyntheticLambda0());
        this.tv = (TextView) findViewById(R.id.textView);
        this.db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);
        this.sharedPref = getApplicationContext().getSharedPreferences(getString(R.string.shared_pref), 0);
        this.b1 = (Button) findViewById(R.id.button);
        int delay = (int) ((double) (loadCharsUnlocked(this.sharedPref) * 10));
        this.b1.setEnabled(true);
        Toast.makeText(this, "Button will be enabled in " + delay + " seconds", 0).show();
        String flag = loadFlag(this.sharedPref);
        if (!flag.isEmpty()) {
            ((TextView) findViewById(R.id.textView)).setText(flag);
        }
    }

    static /* synthetic */ WindowInsetsCompat lambda$onCreate$0(View v, WindowInsetsCompat insets) {
        Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
        return insets;
    }

    public void play(View view) {
        int delay = (int) ((double) (loadCharsUnlocked(this.sharedPref) * 10));
        this.b1.setEnabled(false);
        Toast.makeText(this, "Button will be enabled in " + delay + " seconds", 0).show();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                MainActivity.this.b1.setEnabled(true);
            }
        }, ((long) delay) * 1000);
        int a = new Random().nextInt(20) + 1;
        this.db.collection("flag").document(String.valueOf(a)).get().addOnSuccessListener(new MainActivity$$ExternalSyntheticLambda1(this, a)).addOnFailureListener(new MainActivity$$ExternalSyntheticLambda2(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$play$1$com-hacktober-luck-MainActivity  reason: not valid java name */
    public /* synthetic */ void m0lambda$play$1$comhacktoberluckMainActivity(int a, DocumentSnapshot documentSnapshot) {
        if (documentSnapshot.exists()) {
            String val = documentSnapshot.getString("flag");
            StringBuffer sbf = new StringBuffer(this.tv.getText().toString());
            sbf.setCharAt(a - 1, val.charAt(0));
            this.tv.setText(sbf.toString());
            storeFlag(sbf.toString());
            return;
        }
        Log.e("Firestore", "Document does not exist");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$play$2$com-hacktober-luck-MainActivity  reason: not valid java name */
    public /* synthetic */ void m1lambda$play$2$comhacktoberluckMainActivity(Exception e) {
        if (e instanceof FirebaseFirestoreException) {
            FirebaseFirestoreException fbe = (FirebaseFirestoreException) e;
            if (fbe.getCode() == FirebaseFirestoreException.Code.UNAVAILABLE) {
                Log.e("Firestore", "Network unavailable. Please check your connection.");
                Toast.makeText(this, "No internet !!!", 0).show();
                return;
            }
            Log.e("Firestore", "Firestore error: " + fbe.getCode());
            return;
        }
        Log.e("Firestore", "Unexpected error: " + e.getMessage());
        Toast.makeText(this, e.getMessage(), 0).show();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.sharedPref = null;
    }
}
