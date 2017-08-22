package com.xinaliu.inspiration.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;

import com.xinaliu.inspiration.R;
import com.xinaliu.inspiration.activity.TestActivity;
import com.xinaliu.inspiration.util.DeviceUtils;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.internal.MemoryPersistence;

import java.util.Locale;



public class MqService extends Service {

    private String serverURI = "tcp://%s:%s";
//	private String BROKER_URL = "tcp://%s:1883";

    private String clientId;

    private String TOPIC_KEY= "TOPIC";

    private static final String IP_KEY = "IP";

    private static final String PORT_KEY = "PORT";
    private String IS_CLEAN_SESSION_KEY = "isCleanSession";

    private boolean isCleanSession;
    //    private String topic = "finance.stock.ibm55";
    private String topic = null;//finance.stock.ibm55

    public MqService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void actionStart(Context ctx) {
        actionStart(ctx,null,null);
    }
    public static void actionStart(Context ctx,String ip) {
        actionStart(ctx, ip,null);
    }
    /**
     * Start MQTT Client
     * @param ctx context to start the service with
     * @param ip
     */
    public static void actionStart(Context ctx, String ip, String port) {
        Intent i = new Intent(ctx,MqService.class);
        i.putExtra(IP_KEY,ip);
        i.putExtra(PORT_KEY,port);
        ctx.startService(i);
    }

    private MqttClient mqttClient;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {
            Bundle bundle = intent.getExtras();
            String ip = "192.168.0.103";
            String port = "1883";
            if (bundle != null){
                ip = bundle.getString(IP_KEY, "192.168.0.103");
                port = bundle.getString(PORT_KEY, "1883");
                topic = bundle.getString(TOPIC_KEY,"");
                isCleanSession = bundle.getBoolean(IS_CLEAN_SESSION_KEY,false);
            }
            String url = String.format(Locale.US, serverURI, ip, port);
            clientId = DeviceUtils.getUDID();
//            new MqttClient(url,clientId,new MemoryPersistence());
//            if(clientId != null && clientId.length() != 0 && clientId.length() <= 23)
            clientId = clientId.length() >= 23 ? clientId.substring(0,22) : clientId;//这步的原因就是上面那个if
            connect(url,clientId);
        return super.onStartCommand(intent, flags, startId);
    }

    private Thread thread;
    /***
     * 客户端和activeMQ服务器建立连接
     * @param url
     * @param clientId : 用于标识客户端,相当于ios中的device token
     * @return 是否启动成功
     */
    private synchronized boolean connect(final String url,final String clientId){
        stopClient();
         thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mqttClient = new MqttClient(url, clientId, new MemoryPersistence());
                    MqttConnectOptions options = new MqttConnectOptions();
                    options.setCleanSession(isCleanSession);//mqtt receive offline message
                    options.setKeepAliveInterval(30);
                    //推送回调类,在此类中处理消息,用于消息监听
                    mqttClient.setCallback(new PushCallback(getApplication()));

                    boolean isSuccess = false;
                    try {
                        mqttClient.connect(options);//CLIENT ID CAN NOT BE SAME
                        isSuccess = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!isSuccess) {
                        String message = "连接失败,请检查client id是否重复了 或者activeMQ是否启动";
                    } else {
                        //Subscribe to topics
                        mqttClient.subscribe(TextUtils.isEmpty(topic) ? new String[]{clientId} : new String[]{topic, clientId});
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return true;
    }

    private void stopClient() {
        if (thread != null && thread.isInterrupted()){
            thread.interrupt();
        }
        if (mqttClient != null && mqttClient.isConnected()){
            try {
                mqttClient.unsubscribe(TextUtils.isEmpty(topic) ? new String[]{clientId} : new String[]{topic, clientId});
            } catch (MqttException e) {
                e.printStackTrace();
            }
            try {
                mqttClient.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }


    private class PushCallback implements MqttCallback {

        private Context mContext;

        public PushCallback(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public void connectionLost(Throwable throwable) {

        }

        @Override
        public void messageArrived(MqttTopic mqttTopic, MqttMessage mqttMessage) throws Exception {
            final NotificationManager notificationManager = (NotificationManager)
                    mContext.getSystemService(Context.NOTIFICATION_SERVICE);


            final Intent intent = new Intent(mContext, TestActivity.class);
            final PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);

            Notification notification = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                Notification.Builder builder = new Notification.Builder(mContext);
                builder.setSmallIcon(R.mipmap.app_logo);
                builder.setTicker("灵感Ticker");
                builder.setContentTitle("灵感Title");
                builder.setContentText(new String(mqttMessage.getPayload()));
                builder.setWhen(System.currentTimeMillis()); //发送时间
                builder.setDefaults(Notification.DEFAULT_ALL);
                builder.setAutoCancel(true);//打开程序后图标消失
                builder.setContentIntent(pendingIntent);
                notification = builder.build();
            }else {
                notification = new Notification(R.mipmap.ic_launcher,
                    "灵感Ticker", System.currentTimeMillis());
                notification.when = System.currentTimeMillis();
                notification.contentIntent = pendingIntent;
                notification.defaults = Notification.DEFAULT_ALL;
                // Hide the notification after its selected
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
            }
            notification.number += 1;
            notificationManager.notify(0, notification);
        }

        @Override
        public void deliveryComplete(MqttDeliveryToken mqttDeliveryToken) {

        }
    }

    @Override
    public void onDestroy() {
        stopClient();
        super.onDestroy();
    }
}
