package com.example.android.bluetooth2;

import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.widget.EditText;

import com.github.pires.obd.commands.SpeedCommand;
import com.github.pires.obd.commands.engine.RPMCommand;

import java.io.IOException;

/**
 * Created by Jakub on 08.05.2017.
 */

public class asyncTaskClass extends AsyncTask<Object, Object, Object> {
    RPMCommand engineRpmCommand;
    SpeedCommand speedCommand;
    @Override
    protected Object doInBackground(Object[] params) {
        BluetoothSocket socket = (BluetoothSocket)params[1];
        engineRpmCommand = new RPMCommand();
        speedCommand = new SpeedCommand();
        try {
            engineRpmCommand.run(socket.getInputStream(), socket.getOutputStream());
            speedCommand.run(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return params[0];
    }
    @Override
    protected void onPostExecute(Object results) {
        //super.onPostExecute(results);
        EditText textPanel = (EditText)results;

        String text = "RPM: " + engineRpmCommand.getFormattedResult()+ "\n"+
                "Speed: " + speedCommand.getFormattedResult();
        textPanel.setText(text);
    }
}
