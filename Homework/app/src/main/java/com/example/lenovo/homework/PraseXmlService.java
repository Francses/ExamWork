package com.example.lenovo.homework;

import android.content.Context;
import android.content.pm.PackageManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by lenovo on 2015/6/2.
 */
public class PraseXmlService
{
    public HashMap<String, String> parseXml(InputStream inStream) throws Exception
    {
        HashMap<String, String> hashMap = new HashMap<String, String>();

        // ʵ����һ���ĵ�����������
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // ͨ���ĵ�������������ȡһ���ĵ�������
        DocumentBuilder builder = factory.newDocumentBuilder();
        // ͨ���ĵ�ͨ���ĵ�����������һ���ĵ�ʵ��
        Document document = builder.parse(inStream);
        //��ȡXML�ļ����ڵ�
        Element root = document.getDocumentElement();
        //��������ӽڵ�
        NodeList childNodes = root.getChildNodes();
        for (int j = 0; j < childNodes.getLength(); j++)
        {
            //�����ӽڵ�
            Node childNode = (Node) childNodes.item(j);
            if (childNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element childElement = (Element) childNode;
                //�汾��
                if ("versionCode".equals(childElement.getNodeName())) {
                    hashMap.put("versionCode",childElement.getFirstChild().getNodeValue());
                } else if (("versionName".equals(childElement.getNodeName()))) {
                    hashMap.put("versionName",childElement.getFirstChild().getNodeValue());
                } else if (("updateContent".equals(childElement.getNodeName()))){
                    hashMap.put("updateContent",childElement.getFirstChild().getNodeValue());
                } else if (("apkURL".equals(childElement.getNodeName()))) {
                    hashMap.put("apkURL",childElement.getFirstChild().getNodeValue());
                }
            }
        }
        return hashMap;
    }

    /**
     * ��ȡ����汾��
     *
     * @param context
     * @return
     */
    private int getVersionCode(Context context)
    {
        int versionCode = 0;

        try {
            versionCode = context.getPackageManager().getPackageInfo("com.example.lenovo.homework", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }
}
