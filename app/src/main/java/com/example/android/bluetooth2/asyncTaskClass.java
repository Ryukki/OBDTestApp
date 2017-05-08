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

public class asyncTaskClass extends AsyncTask {
    @Override
    protected Object[] doInBackground(Object[] params) {
        Object[] results = new Object[3];
        results[0] = params[0];
        BluetoothSocket socket = (BluetoothSocket)params[1];
        RPMCommand engineRpmCommand = new RPMCommand();
        SpeedCommand speedCommand = new SpeedCommand();
        try {
            engineRpmCommand.run(socket.getInputStream(), socket.getOutputStream());
            speedCommand.run(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        results[1] = engineRpmCommand;
        results[2] = speedCommand;
        return null;
    }

    protected void onPostExecute(Object[] results) {
        EditText textPanel = (EditText)results[0];
        RPMCommand engineRpmCommand = (RPMCommand) results[1];
        SpeedCommand speedCommand = (SpeedCommand)results[2];

        String text = "RPM: " + engineRpmCommand.getFormattedResult()+ "\n"+
                "Speed: " + speedCommand.getFormattedResult();
        textPanel.setText(text);
    }
}
