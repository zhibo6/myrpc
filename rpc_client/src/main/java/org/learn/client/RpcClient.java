package org.learn.client;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

public class RpcClient<S> {
        public S execute(final Class<?> serviceClass, final InetSocketAddress address){
            return (S) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?> [] {
                    serviceClass.getInterfaces()[0]}, new ClientInvocationHandler(serviceClass, address));
        }
}
