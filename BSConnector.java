import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BSConnector {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice device;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;
    private InputStream inputStream;
    BSConnector()
    {
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
    }

    public boolean isBluetoothPresent()
    {
        return bluetoothAdapter!=null;
    }

    public boolean isBluetoothOn()
    {
        return bluetoothAdapter.isEnabled();
    }

    public void closeAll() throws IOException
    {
        bluetoothSocket.close();
        outputStream.close();
        inputStream.close();
    }

    public void onDestroy() throws IOException
    {
        this.closeAll();
        bluetoothAdapter=null;
        device=null;
        outputStream=null;
        inputStream=null;
    }

    public String[] getPairedDevices()
    {
        final Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        String[] deviceNames=new String[pairedDevices.size()];
        if(pairedDevices.size() > 0)
        {
            int i=0;
            for(BluetoothDevice device : pairedDevices)
            {
                deviceNames[i]=device.getName();
                i++;
            }
        }
        return deviceNames;
    }

    public void setDeviceByName(String deviceName)
    {
        final Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        for(BluetoothDevice device : pairedDevices)
        {
            if(device.getName().equals(deviceName))
            {
                this.device=device;
            }
        }
    }

    public void connect() throws IOException
    {
        bluetoothSocket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
        bluetoothSocket.connect();
        outputStream=bluetoothSocket.getOutputStream();
        inputStream=bluetoothSocket.getInputStream();
    }

    public void write(String data) throws IOException
    {
        outputStream.write(data.getBytes());
    }

    public boolean isDataRecieved() throws IOException
    {
        return (inputStream.available()>1);
    }

    public String read(int buffersize) throws IOException
    {
        byte[] b=new byte[buffersize];
        inputStream.read(b);
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<b.length;i++)
        {
            builder.append((char)b[i]);
        }
        return builder.toString();
    }

    public OutputStream getOutputStream()
    {
        return this.outputStream;
    }

}
