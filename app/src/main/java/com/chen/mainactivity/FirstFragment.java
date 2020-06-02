package com.chen.mainactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;
import java.net.Socket;

public class FirstFragment extends Fragment {

    private EditText textView;

    public static Socket socket;

    @SuppressLint("HandlerLeak")
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Toast.makeText(getContext(), "连接PC端失败，请检查PC端是否开启", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = view.findViewById(R.id.editText);
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("chen", "onClick: "+111);
                if (textView.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "输入的Ip地址为空", Toast.LENGTH_SHORT).show();
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                socket = new Socket(textView.getText().toString(), 8888);
                                Log.d("tagchen" , "已连接");
                                NavHostFragment.findNavController(FirstFragment.this)
                                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
                            } catch (IOException e) {
                                mhandler.sendEmptyMessage(1);
                                e.printStackTrace();
                                Log.d("chen", "run: " + e);
                            }
                        }
                    }).start();
                }

            }
        });


    }
}
