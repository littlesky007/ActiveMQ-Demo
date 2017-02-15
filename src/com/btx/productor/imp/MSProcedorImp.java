package com.btx.productor.imp;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.btx.productor.MSProcedor;

public class MSProcedorImp implements MSProcedor
{
    private String name = ActiveMQConnectionFactory.DEFAULT_USER;
    private String pwd = ActiveMQConnectionFactory.DEFAULT_PASSWORD;
    private String url = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
    private ConnectionFactory conFactory;
    private Connection conn;
    private Session session;

    @Override
    public void init()
    {
        conFactory = new ActiveMQConnectionFactory(name, pwd, url);
        try
        {
            conn = conFactory.createConnection();
            conn.start();
            session = conn.createSession(true, Session.SESSION_TRANSACTED);
        }
        catch (JMSException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String queneName)
    {
        // TODO Auto-generated method stub
        try
        {
            // 根据消息队列名字创建消息队列
            Queue queue = session.createQueue(queneName);
            // 根据消息队列创建消息生产者（session创建生产者）
            MessageProducer mpMessageProducer = session.createProducer(queue);
            int i = 0;
            while (true)
            {
                // session创建消息
                Message tm = session.createTextMessage("我是xx平台，内容是：" + i++);
                mpMessageProducer.send(tm);
                if (i % 100 == 0)
                {
                    session.commit();
                }
            }
            // 因为session是事物开启的，所有要进行commit提交
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
