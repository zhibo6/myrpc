package org.learn;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class RpcTask implements Runnable{
    Socket client = null;

    public RpcTask(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;

        try {
            inputStream = new ObjectInputStream(client.getInputStream());
            String interfaceName = inputStream.readUTF();
            Class<?> service = Class.forName(interfaceName);
            String methodName = inputStream.readUTF();
            Class<?>[] parameterTypes = (Class<?>[])inputStream.readObject();
            Object[] arguments = (Object[])inputStream.readObject();

            Method method = service.getMethod(methodName, parameterTypes);
            Object result = method.invoke(service.newInstance(), arguments);

            outputStream = new ObjectOutputStream(client.getOutputStream());
            outputStream.writeObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
