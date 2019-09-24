package com.mvp.mapactivity;

import android.app.Application;

import io.socket.client.Socket;
import com.mvp.mapactivity.utills.Constant;

import java.net.URISyntaxException;
import io.socket.client.IO;




public class MyApplication extends Application {

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Constant.BASEURL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
