
package com.itheima.bos.service;

import com.itheima.bos.domain.take_delivery.Order;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "OrderService", targetNamespace = "http://service.bos.itheima.com/")
public interface OrderService {


    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "saveOrder", targetNamespace = "http://service.bos.itheima.com/", className = "com.itheima.bos.service.SaveOrder")
    @ResponseWrapper(localName = "saveOrderResponse", targetNamespace = "http://service.bos.itheima.com/", className = "com.itheima.bos.service.SaveOrderResponse")
    public void saveOrder(
            @WebParam(name = "arg0", targetNamespace = "")
                    Order arg0);

}
