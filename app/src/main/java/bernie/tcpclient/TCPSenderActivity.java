package bernie.tcpclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TCPSenderActivity extends Activity {

    Client client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcpsender);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tcpsender, menu);
        return true;
    }

    public void send(View view){
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
            case R.id.sendButton:
                msg = ((TextView)findViewById(R.id.inputText)).getText().toString();
                break;
            case R.id.buttonEnd:
                msg = "end";
                break;
        }
        String addr = ((TextView)findViewById(R.id.addrInput)).getText().toString();
        int port;
        try{
            port = Integer.parseInt(((TextView)findViewById(R.id.portInput)).getText().toString());
            if(client == null){
                client = new Client();
            }
            client.safeSend(addr,port,msg);
        }
        catch(Exception e){
            ((TextView)findViewById(R.id.textOutput)).setText("Invalid port");
            return;
        }
    }

    public void init(View view){
        String addr = ((TextView)findViewById(R.id.addrInput)).getText().toString();
        int port;
        try{
            port = Integer.parseInt(((TextView)findViewById(R.id.portInput)).getText().toString());
        }
        catch(Exception e){
            ((TextView)findViewById(R.id.textOutput)).setText("Invalid port");
            return;
        }
        client = new Client();
        int ret = client.init(addr,port);
        switch(ret){
            case 0:
                ((TextView)findViewById(R.id.textOutput)).setText("Connected to "+addr+":"+port);
            case 1:
                ((TextView)findViewById(R.id.textOutput)).setText("Connection failed!");
                break;
            case 2:
                ((TextView)findViewById(R.id.textOutput)).setText("IO Error");
                break;
            case 3:
                ((TextView)findViewById(R.id.textOutput)).setText("IO Error");
                break;
            default:
                ((TextView)findViewById(R.id.textOutput)).setText("Connecting...");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.action_settings:
                //Intent intent = new Intent(this,SettingsActivity.class);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
