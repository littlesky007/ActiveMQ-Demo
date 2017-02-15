package com.btx.consumer;

public interface Consumer
{
    public void init();

    public void getMessage(String queueName);
}
