package com.btx.consumer.test;

import com.btx.consumer.Consumer;
import com.btx.consumer.imp.ConsumerImp;

public class CusmorTtest
{
    public static void main(String[] args)
    {
        Consumer consumer = new ConsumerImp();
        consumer.init();
        consumer.getMessage("bao-queue");
    }
}
