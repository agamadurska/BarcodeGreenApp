package com.greenapp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.util.Log;

public class HttpRequestHelper {
	
	public enum ContentType {
	    /** HTML-like content type, including HTML, XHTML, etc. */
	    HTML,
	    /** JSON content */
	    JSON,
	    /** Plain text content */
	    TEXT,
	 }

	/**
	 * @param uri URI to retrieve
	 * @param type expected text-like MIME type of that content
	 * @return content as a {@code String}
	 * @throws IOException if the content can't be retrieved because of a bad URI, network problem, etc.
	 */
	public static String downloadViaHttp(String uri, ContentType type) throws IOException {
	  String contentTypes;
	  switch (type) {
	    case HTML:
	      contentTypes = "application/xhtml+xml,text/html,text/*,*/*";
	      break;
	    case JSON:
	      contentTypes = "application/json,text/*,*/*";
	      break;
	    case TEXT:
	    default:
	      contentTypes = "text/*,*/*";
	  }
	  return downloadViaHttp(uri, contentTypes);
	}

	private static String downloadViaHttp(String uri, String contentTypes) throws IOException {
	  URL url = new URL(uri);
	  Log.v("HTTP HELPER", url.toString());
	  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	  connection.setRequestProperty("Accept", contentTypes);
	  connection.setRequestProperty("Accept-Charset", "utf-8,*");
	  connection.setRequestProperty("User-Agent", "ZXing (Android)");
	  try {
	    connection.connect();
	    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
	      throw new IOException("Bad HTTP response: " + connection.getResponseCode());
	    }
	    return consume(connection);
	  } finally {
	    connection.disconnect();
	  }
	}

    private static String consume(URLConnection connection) throws IOException {
	    String encoding = getEncoding(connection);
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    InputStream in = connection.getInputStream();
	    try {
	      in = connection.getInputStream();
	      byte[] buffer = new byte[1024];
	      int bytesRead;
	      while ((bytesRead = in.read(buffer)) > 0) {
	        out.write(buffer, 0, bytesRead);
	      }
	    } finally {
		      try {
		        in.close();
		      } catch (IOException ioe) {
		        // continue
	          }
          }
	    try {
	      return new String(out.toByteArray(), encoding);
	    } catch (UnsupportedEncodingException uee) {
	      try {
	        return new String(out.toByteArray(), "UTF-8");
	      } catch (UnsupportedEncodingException uee2) {
	        // can't happen
	        throw new IllegalStateException(uee2);
	      }
        }
    }
  
    private static String getEncoding(URLConnection connection) {
	    String contentTypeHeader = connection.getHeaderField("Content-Type");
	    if (contentTypeHeader != null) {
	      int charsetStart = contentTypeHeader.indexOf("charset=");
	      if (charsetStart >= 0) {
	        return contentTypeHeader.substring(charsetStart + "charset=".length());
	      }
	    }
	    return "UTF-8";
    }

	public static String fetchProductInfo(String productID) {
        String encodedProductID = "";
        String content = "";
		try {
			encodedProductID = URLEncoder.encode(productID, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			encodedProductID = "error";
			e1.printStackTrace();
		}
        String uri = "http://www.google.co.uk"
                + "/m/products?ie=utf8&oe=utf8&scoring=p&source=zxing&q=" + encodedProductID;
		try {
			content = HttpRequestHelper.downloadViaHttp(uri, HttpRequestHelper.ContentType.HTML);
		} catch (IOException e) {
			content = "error2";
			e.printStackTrace();
		}
		return content;
	}
}
