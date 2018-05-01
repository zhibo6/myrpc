package org.learn.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientInvocationHandler implements InvocationHandler{
    private Class<?> serviceClass;
    private InetSocketAddress address;

    public ClientInvocationHandler(Class<?> serviceClass, InetSocketAddress address) {
        this.serviceClass = serviceClass;
        this.address = address;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Socket socket = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;

        try {
            socket = new Socket();
            socket.connect(address);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeUTF(serviceClass.getName());
            outputStream.writeUTF(method.getName());
            outputStream.writeObject(method.getParameterTypes());
            outputStream.writeObject(args);

            inputStream = new ObjectInputStream(socket.getInputStream());
            return inputStream.readObject();
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }
}
