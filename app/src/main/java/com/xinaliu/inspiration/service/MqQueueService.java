//package com.xinaliu.inspiration.service;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.IBinder;
//
//import com.xinaliu.inspiration.R;
//import com.xinaliu.inspiration.activity.TestActivity;
//import com.xinaliu.inspiration.util.DeviceUtils;
//
//import java.util.Locale;
//
////import org.apache.activemq.command.ActiveMQTextMessage;
////
////import java.util.Locale;
////
////import javax.jms.JMSException;
////import javax.jms.Message;
////import javax.jms.MessageListener;
////import javax.jms.QueueConnection;
////import javax.jms.QueueSession;
//
//
//public class MqQueueService extends Service {
//
//    private String serverURI = "tcp://%s:%s";
////	private String BROKER_URL = "tcp://%s:1883";
//
//    private String clientId;
//
//    private String TOPIC_KEY= "TOPIC";
//
//    private static final String IP_KEY = "IP";
//
//    private static final String PORT_KEY = "PORT";
//    private String IS_CLEAN_SESSION_KEY = "isCleanSession";
//
//    private boolean isCleanSession;
//    //    private String topic = "finance.stock.ibm55";
//    private String topic = null;//finance.stock.ibm55
//
//    public MqQueueService() {
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    public static void actionStart(Context ctx) {
//        actionStart(ctx,null,null);
//    }
//    public static void actionStart(Context ctx,String ip) {
//        actionStart(ctx, ip,null);
//    }
//    /**
//     * Start MQTT Client
//     * @param ctx context to start the service with
//     * @param ip
//     */
//    public static void actionStart(Context ctx, String ip, String port) {
//        Intent i = new Intent(ctx,MqQueueService.class);
//        i.putExtra(IP_KEY,ip);
//        i.putExtra(PORT_KEY,port);
//        ctx.startService(i);
//    }
//
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//    }
//
//    @Override
//    public int onStartCommand(Intent intent,int flags, int startId) {
//            Bundle bundle = intent.getExtras();
//            String ip = "192.168.0.103";
//            String port = "1883";
//            if (bundle != null){
//                ip = bundle.getString(IP_KEY, "192.168.0.103");
//                port = bundle.getString(PORT_KEY, "1883");
//                topic = bundle.getString(TOPIC_KEY,"");
//                isCleanSession = bundle.getBoolean(IS_CLEAN_SESSION_KEY,false);
//            }
//            String url = String.format(Locale.US, serverURI, ip, port);
//            clientId = DeviceUtils.getUDID();
////            new MqttClient(url,clientId,new MemoryPersistence());
////            if(clientId != null && clientId.length() != 0 && clientId.length() <= 23)
//            clientId = clientId.length() >= 23 ? clientId.substring(0,22) : clientId;//这步的原因就是上面那个if
//            connect(url,clientId);
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    private Thread thread;
//
//    /***
//     * 客户端和activeMQ服务器建立连接
//     * @param url
//     * @param clientId : 用于标识客户端,相当于ios中的device token
//     * @return 是否启动成功
//     */
//    private synchronized boolean connect(String url,String clientId){
//        stopClient();
////        thread = new MqQueueThread(url, clientId, new MessageListener() {
////             @Override
////             public void onMessage(Message message) {
////                 try {
////                     showNotification(message);
////                 } catch (JMSException e) {
////                     e.printStackTrace();
////                 }
////             }
////         });
////        thread.start();
//        return true;
//    }
//
//    public class MqQueueThread extends Thread{
//        private QueueConnection connection = null;
//        private QueueSession session = null;
//        private String url;
//        private String clientId;
//        private MessageListener mMessageListener;
//
//        public MqQueueThread(final String url,final String clientId,final MessageListener mMessageListener) {
//            super();
//            this.url = url;
//            this.clientId = clientId;
//            this.mMessageListener = mMessageListener;
//        }
//
//        @Override
//        public void interrupt() {
//             // 关闭释放资源
//             if (session != null) {
//                 try {
//                     session.close();
//                 } catch (JMSException e) {
//                     e.printStackTrace();
//                 }
//             }
//             if (connection != null) {
//                 try {
//                     connection.close();
//                 } catch (JMSException e) {
//                     e.printStackTrace();
//                 }
//             }
//            super.interrupt();
//        }
//
//        @Override
//        public void run() {
////            try {
//////                String brokerUrl = String.format(BROKER_URL, ip);
////                // 创建链接工厂
////                QueueConnectionFactory factory = new ActiveMQConnectionFactory(
////                        ActiveMQConnection.DEFAULT_USER,
////                        ActiveMQConnection.DEFAULT_PASSWORD, url);
////
////                // 通过工厂创建一个连接
////                connection = factory.createQueueConnection();
////                // 启动连接
////                connection.start();
////                // 创建一个session会话
////                session = connection.createQueueSession(Boolean.FALSE,
////                        Session.AUTO_ACKNOWLEDGE);
////
////
////                Destination destination = session.createQueue(clientId);
////                MessageConsumer consumer = session.createConsumer(destination);
////
////                consumer.setMessageListener(new MessageListener() {
////                    @Override
////                    public void onMessage(Message message) {
////                        if (mMessageListener != null) {
////                            mMessageListener.onMessage(message);
////                        }
////                    }
////                });
////            } catch (JMSException e) {
////                e.printStackTrace();
////            }
//        }
//    }
//    private void stopClient() {
//        if (thread != null && thread.isInterrupted()){
//            thread.interrupt();
//        }
//    }
//
//
//    private void showNotification(Message message) throws JMSException {
//        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        if (message != null) {
//            ActiveMQTextMessage mActiveMQTextMessage = (ActiveMQTextMessage) message;
//
//            final Intent intent = new Intent(this, TestActivity.class);
//            final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//            Notification notification = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                Notification.Builder builder = new Notification.Builder(this);
//                builder.setSmallIcon(R.mipmap.app_logo);
//                builder.setTicker("灵感Ticker");
//                builder.setContentTitle("灵感Title");
//                builder.setContentText(mActiveMQTextMessage.getText());
//                builder.setWhen(System.currentTimeMillis()); //发送时间
//                builder.setDefaults(Notification.DEFAULT_ALL);
//                builder.setAutoCancel(true);//打开程序后图标消失
//                builder.setContentIntent(pendingIntent);
//                notification = builder.build();
//            }else {
//                notification = new Notification(R.mipmap.ic_launcher,
//                        "灵感Ticker", System.currentTimeMillis());
//                notification.when = System.currentTimeMillis();
//                notification.contentIntent = pendingIntent;
//                notification.tickerText = mActiveMQTextMessage.getText();
//                notification.defaults = Notification.DEFAULT_ALL;
//                // Hide the notification after its selected
//                notification.flags |= Notification.FLAG_AUTO_CANCEL;
//            }
//            notification.number += 1;
//            notificationManager.notify(0, notification);
//        }
//    }
//    @Override
//    public void onDestroy() {
//        stopClient();
//        super.onDestroy();
//    }
//}
