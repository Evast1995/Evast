package com.evast.evastcore.xmpp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Registration;

import java.util.Map;

/**
 * Created by 72963 on 2015/12/18.
 */
public class XmlppManager {
    private static XmlppManager xmlppManager = null;
    private XMPPConnection connection = null;

    public static synchronized XmlppManager getInstance(){
        if(xmlppManager == null){
            xmlppManager = new XmlppManager();
        }
        return xmlppManager;
    }

    /**
     * 打开连接
     */
    public void openConnection(){
        /** 未连接时，connection为null，或者未登录isAuthenticated*/
        if (connection==null||!connection.isAuthenticated()){
            /** 配置连接*/
            ConnectionConfiguration configuration = new ConnectionConfiguration(
                    ConstantsValue.SERVER_HOST,ConstantsValue.SERVER_PORT,ConstantsValue.SERVER_NAME);
            /** 设置运行断线重连*/
            configuration.setReconnectionAllowed(true);
            /** 设置安全加密模式*/
            configuration.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
            configuration.setSendPresence(true);
            connection = new XMPPConnection(configuration);
            try {
                connection.connect();
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取连接
     * @return
     */
    public XMPPConnection getConnection(){
        if(connection == null){
            openConnection();
        }
        return connection;
    }

    /**
     * 关闭连接
     */
    public void closeConnnection(){
        if(connection!=null && connection.isConnected()){
            connection.disconnect();
            connection = null;
        }
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     * @throws XMPPException
     */
    public boolean login(String username,String password) throws XMPPException {
        if(getConnection() == null){
            return false;
        }
        if(!connection.isAuthenticated()&&getConnection().isConnected()){
            getConnection().login(username, password);
            Presence presence = new Presence(Presence.Type.available);
            presence.setMode(Presence.Mode.available);
            return true;
        }
        return false;
    }

    /**
     * 注册账号
     * @param attributesAttrs
     * @return
     */
    public IQ register(Map<String,String> attributesAttrs){
        if(getConnection() == null){
            return null;
        }
        Registration registration = new Registration();
        registration.setType(IQ.Type.SET);
        registration.setTo(getConnection().getServiceName());
        registration.setUsername("evast");
        registration.setPassword("123456");
//        registration.setAttributes(attributesAttrs);
        PacketFilter filter = new AndFilter(new PacketIDFilter(registration.getPacketID())
            ,new PacketTypeFilter(IQ.class));
        PacketCollector collector = getConnection().createPacketCollector(filter);
        getConnection().sendPacket(registration);
        IQ result = (IQ) collector.nextResult(SmackConfiguration.getPacketReplyTimeout());
        collector.cancel();
        return result;
    }

    /**
     * 退出登录
     */
    public void logout(){
        if(getConnection() == null){
            return;
        }
        Presence presence = new Presence(Presence.Type.unavailable);
        getConnection().sendPacket(presence);
        closeConnnection();
    }

    /**
     * 获取验证码
     */
    public Packet getAuthCode(){
        AuthCodePacket packet = new AuthCodePacket();
        packet.setPhone("13681135735");
        packet.setRequestType(0);
        return packet;
    }
}
