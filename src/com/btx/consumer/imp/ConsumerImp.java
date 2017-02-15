package com.btx.consumer.imp;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.btx.consumer.Consumer;

public class ConsumerImp implements Consumer
{
    private String name = ActiveMQConnectionFactory.DEFAULT_USER;
    private String pwd = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
    private String url = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
    private Connection conn;
    private ConnectionFactory conFactory;
    private Session session;

    @Override
    public void init()
    {
        conFactory = new ActiveMQConnectionFactory(name, pwd, url);
        try
        {
            conn = conFactory.createConnection();
            conn.start();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        }
        catch (JMSException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void getMessage(String queueName)
    {
        try
        {
            Queue queue = session.createQueue(queueName);
            MessageConsumer msConsumer = session.createConsumer(queue);
            while (true)
            {
                TextMessage txMessage = (TextMessage) msConsumer.receive();
                if (txMessage != null)
                {
                    System.out.println(txMessage.getText());
                    txMessage.acknowledge();
                }
                else
                {
                    break;
                }
            }
        }
        catch (JMSException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            if (conn != null)
            {
                try
                {
                    conn.close();
                }
                catch (JMSException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
