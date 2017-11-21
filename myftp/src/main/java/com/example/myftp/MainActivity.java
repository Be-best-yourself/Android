package com.example.myftp;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.minedeed.utils.Utils;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn;
TextView text;
    Button btnClose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                    setContentView(R.layout.main);
                    btn = (Button) findViewById(R.id.btn);
                    text = (TextView) findViewById(R.id.ftpIp);
                    btnClose = (Button) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.exit(0);
            }


        });
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try { FtpServerFactory serverFactory = new FtpServerFactory();
                                ListenerFactory listenerFactory = new ListenerFactory();
                                listenerFactory.setPort(1111);
                                serverFactory.addListener("default", listenerFactory.createListener());
                                BaseUser user = new BaseUser();
                                user.setName("anonymous");
                                user.setHomeDirectory("/");
                                List<Authority> authorities = new ArrayList<Authority>();
                                authorities.add(new WritePermission());
                                user.setAuthorities(authorities);
                                serverFactory.getUserManager().save(user);
                                FtpServer server = serverFactory.createServer();
                                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                                String id = intToIp(wifiInfo.getIpAddress());
                                text.setText("开启成功,ip地址是"+id+":"+listenerFactory.getPort()+"。");
                                server.start();



                            } catch (FtpException e) {
                    e.printStackTrace();
                }
            }
            private String intToIp(int i) {



                return (i & 0xFF ) + "." +

                        ((i >> 8 ) & 0xFF) + "." +

                        ((i >> 16 ) & 0xFF) + "." +

                        ( i >> 24 & 0xFF) ;

            }

        });
    }


}

}
