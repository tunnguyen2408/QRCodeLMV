package com.cdac.qrcodescanner;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        findViewById(R.id.buttonScanBarcode).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonScanBarcode) {
            startActivityForResult(new Intent(this, ScannerBarcodeActivity.class), 900);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 900 && resultCode == Activity.RESULT_OK) {
            String barCode = data.getStringExtra("TextCopy");
            showSnackBar(barCode);
        }
    }

    public void showSnackBar(String message) {
        final Snackbar snackbar = Snackbar.make(findViewById(R.id.constraintLayout), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("COPY", v -> {
            copyToClipBoard(message);
            snackbar.dismiss();
        });

        snackbar.show();
    }

    private void copyToClipBoard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("QR code Scanner", text);
        assert clipboard != null;
        clipboard.setPrimaryClip(clip);
    }
}