package com.xinaliu.inspiration.db;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by liuwei on 2017/6/29 15:34
 */

public class TestDb extends Activity{

    private TextView tv_content;
    private TextView tv_hint;
    private EditText et_v;
    private int version = 1;

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            tv_content.setText(msg.obj != null ? msg.obj.toString() : "没有数据");
        }
    };

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test_db);
//         tv_content = (TextView) findViewById(R.id.tv_content);
//         tv_hint = (TextView) findViewById(R.id.tv_hint);
//        et_v = (EditText) findViewById(R.id.et_v);
//        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String trim = et_v.getText().toString().trim();
//                version = Integer.parseInt(trim);
//                UserEntityImpl userEntityImpl = new UserEntityImpl(TestDb.this,version);
//                UserEntity userEntity = new UserEntity();
//                userEntity.setUserid("");
//                userEntity.setEmail(""+System.currentTimeMillis());
//                long insert = userEntityImpl.insert(userEntity);
//                tv_hint.setText("insert=="+insert+"");
//            }
//        });
//
//        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String trim = et_v.getText().toString().trim();
//                version = Integer.parseInt(trim);
//                UserEntityImpl userEntityImpl = new UserEntityImpl(TestDb.this,version);
//                List<UserEntity> userEntities = userEntityImpl.find();
//                Message message = handler.obtainMessage();
//                message.obj = userEntities;
//                handler.sendMessage(message);
//            }
//        });
//        findViewById(R.id.btn_modified_version).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String trim = et_v.getText().toString().trim();
//                try {
//                    version = Integer.parseInt(trim);
//                    UserEntityImpl userEntityImpl = new UserEntityImpl(TestDb.this,version);
//                    UserEntity userEntity = new UserEntity();
//                    userEntity.setEmail(""+System.currentTimeMillis());
//                    long insert = userEntityImpl.insert(userEntity);
//                    tv_hint.setText("insert=="+insert+"");
//                    List<UserEntity> userEntities = userEntityImpl.find();
//                    Message message = handler.obtainMessage();
//                    message.obj = userEntities;
//                    handler.sendMessage(message);
//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
}
