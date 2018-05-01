package org.learn.server.impl;

import org.learn.server.EchoService;

public class EchoServiceImpl implements EchoService{
    @Override
    public String echo(String ping) {
        return ping != null ? ping + " I am ok." : "I am ok.";
    }
}
