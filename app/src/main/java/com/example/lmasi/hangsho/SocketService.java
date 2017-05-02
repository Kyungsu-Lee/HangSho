package com.example.lmasi.hangsho;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by lmasi on 2016-10-17.
 */
public class SocketService extends Service {

    private Socket socket;
    static BufferedReader socket_in;
    static PrintWriter socket_out;
    Thread work;

    Handler toastHandler;
    String toastMessage;

    String ID;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(getApplicationContext(), "연결되었습니다", Toast.LENGTH_SHORT).show();

        DbResource.conn = new DBConect(this, "HangSho.db", null, 1);

        DbResource.db = DbResource.conn.getWritableDatabase();

        Cursor c = DbResource.db.rawQuery("select * from Login_info where isLogged=1", null);
        c.moveToNext();
        ID = c.getString(c.getColumnIndex("ID"));

        work = new Thread() {

            public void run() {
                try {
                    socket = new Socket(DbResource.IPADDR, DbResource.port);
                    socket_out = new PrintWriter(socket.getOutputStream(), true);
                    socket_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    Log.e("SOCKET", "SUCC");

                } catch (IOException e) {
                    Log.e("ERROR", e.toString());
                }

                try {
                    socket_out.println("multi " + ID);
                    Log.e("WELL MULTI", "WELL : " + ID);

                    Looper.prepare();
                    while(true) {
                        toastMessage = socket_in.readLine();
                        Log.e("MESSAGE FROM SERVER", toastMessage);

                        if (toastMessage.substring(0, toastMessage.indexOf(' ') == -1 ? 1 : toastMessage.indexOf( ' ')).equals("CALLSHOP")) {
                            //toastHandler.sendEmptyMessage(0);


                            //알림(Notification)을 관리하는 NotificationManager 얻어오기
                            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            //알림(Notification)을 만들어내는 Builder 객체 생성
                            //API 11 버전 이하도 지원하기 위해 NotificationCampat 클래스 사용
                            //만약 minimum SDK가 API 11 이상이면 Notification 클래스 사용 가능
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                            //Notification.Builder에게 Notification 제목, 내용, 이미지 등을 설정//////////////////////////////////////

                            builder.setSmallIcon(R.drawable.result_boyshop);//상태표시줄에 보이는 아이콘 모양
                            builder.setTicker("Notification"); //알림이 발생될 때 잠시 보이는 글씨

                            //상태바를 드래그하여 아래로 내리면 보이는 알림창(확장 상태바)의 아이콘 모양 지정
                            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.result_boyeye));
                            builder.setContentTitle("쇼핑을 시작하세요!");    //알림창에서의 제목
                            builder.setContentText("");   //알림창에서의 글씨
                            ///////////////////////////////////////////////////////////////////////////////////////////////////////
                            Notification notification = builder.build();   //Notification 객체 생성
                            manager.notify(1, notification);             //NotificationManager가 알림(Notification)을 표시

                            DbResource.iscalled = true;
                            DbResource.Location = toastMessage.substring(toastMessage.indexOf(' '), toastMessage.length());

                        }

                        else if(toastMessage.equals("STOP"))
                        {
                            DbResource.stop = true;
                        }

                        else if(toastMessage.equals("RESTART"))
                        {
                            DbResource.restart = true;
                        }
                        else if(toastMessage.equals("PAUSE"))
                        {
                            DbResource.pause = true;
                        }
                        else  if(toastMessage.equals("LSI"))
                        {
                            DbResource.LSI = true;
                        }
                        else  if(toastMessage.equals("COUPON"))
                        {
                            DbResource.COUPON = true;
                        }
                    }
                }
                catch (Exception e)
                {
                        Log.e("MULTIFAIL", e.toString());
                        e.printStackTrace();
                }
            }

        };

        work.start();

        toastHandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("BEY", "BYE");

        try {
            socket_in.close();
            socket_out.close();
            socket.close();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }

    public static void sendmessage(String str)
    {
        socket_out.println(str);
    }
}
