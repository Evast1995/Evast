package com.evast.evastcore.xmpp;

import com.evast.evastcore.util.other.L;

import org.jivesoftware.smack.packet.IQ;

/**
 * Created by 72963 on 2015/12/20.
 */
public class AuthCodePacket extends IQ {
    public static final String ELEMENT_NAME = "iTureFace";
    public static final String NAMESPACE = "iTureFace";

    private String phone;
    private int requestType;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }


    @Override
    public String getChildElementXML() {
        StringBuffer buf = new StringBuffer();
        buf.append("<" + ELEMENT_NAME + " xmlns=\"" + NAMESPACE + "\">");
        if (getType() == IQ.Type.GET) {
            buf.append("<phone>").append(phone).append("</phone>");
            buf.append("<type>").append(requestType).append("</type>");
            buf.append(getExtensionsXML());
        }
        buf.append("</" + ELEMENT_NAME + ">");
        L.e(buf.toString());
        return buf.toString();
    }
}
