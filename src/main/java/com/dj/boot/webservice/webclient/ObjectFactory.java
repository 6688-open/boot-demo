
package com.dj.boot.webservice.webclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.dj.boot.webservice.webserver.impl package. 
 * &lt;p&gt;An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TransportGrantUserPermission_QNAME = new QName("http://impl.webserver.webservice.boot.dj.com/", "transportGrantUserPermission");
    private final static QName _TransportGrantUserPermissionResponse_QNAME = new QName("http://impl.webserver.webservice.boot.dj.com/", "transportGrantUserPermissionResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.dj.boot.webservice.webserver.impl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TransportGrantUserPermission }
     * 
     */
    public TransportGrantUserPermission createTransportGrantUserPermission() {
        return new TransportGrantUserPermission();
    }

    /**
     * Create an instance of {@link TransportGrantUserPermissionResponse }
     * 
     */
    public TransportGrantUserPermissionResponse createTransportGrantUserPermissionResponse() {
        return new TransportGrantUserPermissionResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransportGrantUserPermission }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TransportGrantUserPermission }{@code >}
     */
    @XmlElementDecl(namespace = "http://impl.webserver.webservice.boot.dj.com/", name = "transportGrantUserPermission")
    public JAXBElement<TransportGrantUserPermission> createTransportGrantUserPermission(TransportGrantUserPermission value) {
        return new JAXBElement<TransportGrantUserPermission>(_TransportGrantUserPermission_QNAME, TransportGrantUserPermission.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransportGrantUserPermissionResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TransportGrantUserPermissionResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://impl.webserver.webservice.boot.dj.com/", name = "transportGrantUserPermissionResponse")
    public JAXBElement<TransportGrantUserPermissionResponse> createTransportGrantUserPermissionResponse(TransportGrantUserPermissionResponse value) {
        return new JAXBElement<TransportGrantUserPermissionResponse>(_TransportGrantUserPermissionResponse_QNAME, TransportGrantUserPermissionResponse.class, null, value);
    }

}
