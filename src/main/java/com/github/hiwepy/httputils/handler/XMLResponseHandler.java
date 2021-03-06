 package com.github.hiwepy.httputils.handler;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.StatusLine;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.github.hiwepy.httputils.exception.HttpResponseException;

/**
 * http请求响应处理：返回org.w3c.dom.Document对象
 * @author <a href="https://github.com/vindell">vindell</a>
 */
public class XMLResponseHandler implements ResponseHandler<Document> {

	private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	
	@Override
	public void preHandle(HttpClient httpclient) {
		
	}
	
	@Override
    public Document handleResponse(HttpMethodBase httpMethod) throws IOException {
		StatusLine statusLine = httpMethod.getStatusLine();
		int status = statusLine.getStatusCode();
		if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
	        try {
	            DocumentBuilder docBuilder = factory.newDocumentBuilder();
	            return docBuilder.parse(httpMethod.getResponseBodyAsString());
	        } catch (ParserConfigurationException ex) {
	            throw new IllegalStateException(ex);
	        } catch (SAXException ex) {
	            throw new HttpResponseException("Malformed XML document", ex);
	        } finally {
			}
		} else {
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}
    }
}

 
