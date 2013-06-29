/**
 * Mar 13, 2009
 * @author Neeraj Goswamy
 */
package com.pgi.imeet.vcard.authenticate;

import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.Status;
import org.restlet.resource.Representation;
import org.restlet.resource.Resource;
import org.restlet.resource.ResourceException;
import org.restlet.resource.StringRepresentation;
import org.restlet.resource.Variant;
import org.restlet.util.Series;
import org.restlet.data.Cookie;
/*
import net.wimpi.pim.contact.facades.SimpleContact;
import net.wimpi.pim.contact.model.*;
import net.wimpi.pim.factory.ContactModelFactory;
import net.wimpi.pim.Pim;
import net.wimpi.pim.factory.ContactIOFactory;
import net.wimpi.pim.contact.io.ContactMarshaller;
import com.pgi.imeet.vcard.model.Data;
import com.pgi.imeet.codec.IMeetUnMarshaller;

import java.io.ByteArrayOutputStream;
import com.pgi.imeet.cache.vCardObjManager;
import com.pgi.imeet.vcard.model.vCardObj;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import com.pgi.imeet.vcard.model.Vcard;
import org.restlet.resource.OutputRepresentation;
import java.io.OutputStream;
import java.io.IOException;
*/

public class AuthToken extends Resource {

private String token = null;

public AuthToken(Context context, Request request, Response response) {
 super(context, request, response);

 getVariants().add(new Variant(MediaType.TEXT_PLAIN));
 }

/**
 * Allow a POST http request
 *
 * @return
 */
public boolean allowPost() {
 return true;
}

/**
 * Represent the user object in the requested format.
 *
 * @param variant
 * @return
 * @throws ResourceException
 */
public Representation represent(Variant variant) throws ResourceException {
 Representation result = null;
 if (null == this.token) {
  ErrorMessage em = new ErrorMessage();
  return representError(variant, em);
 } else {
   result = new StringRepresentation(this.token);
  }
 return result;
}

/**
 * Handle a POST Http request. Create a new user
 *
 * @param entity
 * @throws ResourceException
 */
public void acceptRepresentation(Representation entity)
  throws ResourceException {

 try{
  if (entity.getMediaType().equals(MediaType.APPLICATION_WWW_FORM,true)) {
   Form form = new Form(entity);

   getResponse().setStatus(Status.SUCCESS_OK);

   String userName = form.getFirstValue("userName",true,"");
   String password = form.getFirstValue("password",true,"");
   
   String result="";
   result = getToken(userName, password);
   //Store token in database
   Representation rep = new StringRepresentation(result);
      getResponse().setEntity(rep);
  } else {
     getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
  }
 } catch (Exception e) {
  getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
  e.printStackTrace();
 }
}

/**
 * Represent an error message in the requested format.
 *
 * @param variant
 * @param em
 * @return
 * @throws ResourceException
 */
private Representation representError(Variant variant, ErrorMessage em)
  throws ResourceException {
 Representation result = null;
  result = new StringRepresentation(em.toString());
 return result;
}

protected Representation representError(MediaType type, ErrorMessage em)
  throws ResourceException {
 Representation result = null;
  result = new StringRepresentation(em.toString());
 return result;
}

private class ErrorMessage {

	String msg;

	public void ErrorMessage(){
		this.msg="Error in processing";
	}
}
private String getToken(String uname, String pass){
			 String retValue="";

	         try{
                  //Conversion code using base64 encoding
	        	  //Dummy conversion for now
	        	  return uname+pass;
			 			    } catch (Exception ex) {
			 			          ex.printStackTrace();
			        }

	         return retValue;
}
}