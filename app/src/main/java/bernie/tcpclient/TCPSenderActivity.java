package bernie.tcpclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bernie.tcpclient.Preference.SettingsActivity;
import bernie.tcpclient.Preference.SettingsFragment;


public class TCPSenderActivity extends Activity {

    Client client = new Client();
    private static  final int PORT = 80;
    private String ADDR = "";
    private String CODE = "";
    private final String QRSPLITTER = ":";
    private final String BARCODEAPPNAME = "com.google.zxing.client.android";
    private boolean scanSuccess = true;
    AlertDialog noConnDialog;
    AlertDialog noScanDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(TCPSenderActivity.this);
        builder.setMessage(R.string.noConnDialogText)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setTitle(R.string.noConnDialogTitle);
        noConnDialog = builder.create();
        builder.setMessage(R.string.noScanDialogText)
                .setPositiveButton(R.string.download, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + BARCODEAPPNAME)));
                        } catch (android.content.ActivityNotFoundException anfe2) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BARCODEAPPNAME)));
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setTitle(R.string.noScanDialogTitle);
        noScanDialog = builder.create();

        setContentView(R.layout.activity_tcpsender);
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadLabels();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tcpsender, menu);
        return true;
    }

    public void send(View view){
        //#ff2b4b75 normal
        //#ff3c5c86 gedrückt
        String msg = "";
        switch(view.getId()){
            case R.id.button1:
                msg = ""+1;
                break;
            case R.id.button2:
                msg = ""+2;
                break;
            case R.id.button3:
                msg = ""+3;
                break;
            case R.id.button4:
                msg = ""+4;
                break;
            case R.id.button5:
                msg = ""+5;
                break;
            case R.id.button6:
                msg = ""+6;
                break;
            case R.id.button7:
                msg = ""+7;
                break;
            case R.id.button8:
                msg = ""+8;
                break;
            case R.id.button9:
                msg = ""+9;
                break;
            case R.id.button10:
                msg = ""+10;
                break;
        }
        String addr = ADDR;
        if(addr.length() >=11)
            client.safeSend(addr,PORT,CODE+QRSPLITTER+msg);
    }

    public void connect(View v){
        Button b = (Button) v;
        b.setText("...connecting");
        b.setEnabled(false);
        try {
            scanSuccess = false;
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.setPackage("com.google.zxing.client.android");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
            scanSuccess = true;
        }
        catch(ActivityNotFoundException anfe){
            noScanDialog.show();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String[] contents = intent.getStringExtra("SCAN_RESULT").split(QRSPLITTER);
                CODE = contents[0];
                ADDR = contents[1];
                Button b = (Button)findViewById(R.id.btnConnect);
                b.setText("Reconnect");
                if(ADDR.startsWith("No")){
                    noConnDialog.show();
                    b.setText("Connect");
                }
                b.setEnabled(true);
            } else if (resultCode == RESULT_CANCELED) {
                Button b = (Button)findViewById(R.id.btnConnect);
                b.setText("Connect");
                b.setEnabled(true);
            }
        }
    }

    public void init(View view){
        String addr = ADDR;
        client = new Client();
        int ret = client.init(addr,PORT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.action_settings:
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void loadLabels(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String[] btnlbls = new String[10];
        for(int i = 0; i<10; i++) {
            btnlbls[i]=sharedPref.getString("pref_btn"+(i+1)+"lbl", "");
        }
        Button[] b = new Button[10];
        b[0] = (Button)(findViewById(R.id.button1));
        b[1] = (Button)(findViewById(R.id.button2));
        b[2] = (Button)(findViewById(R.id.button3));
        b[3] = (Button)(findViewById(R.id.button4));
        b[4] = (Button)(findViewById(R.id.button5));
        b[5] = (Button)(findViewById(R.id.button6));
        b[6] = (Button)(findViewById(R.id.button7));
        b[7] = (Button)(findViewById(R.id.button8));
        b[8] = (Button)(findViewById(R.id.button9));
        b[9] = (Button)(findViewById(R.id.button10));



        for(int i = 0; i<10; i++){
            b[i].setText(btnlbls[i]);
        }
    }
}
