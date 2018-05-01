package org.learn;

public class RpcServerTest {
    public static void main(String [] args){
        RpcManager.execute("127.0.0.1", 8888);
    }
}
