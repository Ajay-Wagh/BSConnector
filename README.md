# BSConnector
Java class to Establish Bluetooth Serial Connection using RFCOMM socket (for Android)

Coonection with Bluetooth Serial Module such as HC-05

//if you are facing any problem contact on : aw454565@gmail.com

Use : 

BSConnector BT=new BSConnector(); // create object

BT.setDeviceByName("DEvice_Name"); //set the name of the device to connect

Thread r=new Thread(new Runnable() {

                @Override
                
                public void run() {
                
                    try {
                    
                        BT.connect(); //start connection
                        
                        BT.write("Data_to_Write"); // write data
                        
                        int bufferSize=100; //set buffer size (maximum character to read at a time)
                        
                        String dataRecieved = BT.read(bufferSize);
                        
                        BT.closeAll(); // close connection
                        
                    }catch (Exception e)
                    
                    {
                    
                    }
                    
                }
                
            });
            
r.start();



//if BT is class variable then inside onDestroy must call

BT.onDestroy(); 




Methods :

public boolean isBluetoothPresent()

public boolean isBluetoothOn()

public void closeAll()

public void onDestroy()

public String[] getPairedDevices()

public void setDeviceByName(String deviceName)

public void connect()

public void write(String data)

public boolean isDataRecieved()

public String read(int buffersize)

public OutputStream getOutputStream()
