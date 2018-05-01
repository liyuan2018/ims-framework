package com.spark.ims.common.util;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;

/**
 * xml与bean的转换工具
 * Created by liyuan on 2018/4/27.
 */
public class XmlBeanUtils {

    public static Object unmarshall(String xml, Class clz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshaller.unmarshal(new StringReader(xml));
    }

    public static Object unmarshall(InputStream inputStream, Class clz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return unmarshaller.unmarshal(inputStream);
    }

    public static String marshall(Object object, Class clz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clz);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty("jaxb.formatted.output", true);
        marshaller.setProperty("jaxb.encoding", System.getProperty("file.encoding"));
        marshaller.marshal(object, out);
        return out.toString();
    }
}
