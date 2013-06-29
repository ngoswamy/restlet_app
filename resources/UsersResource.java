/**
 * Mar 13, 2009
 * @author Neeraj Goswamy
 */
package com.pgi.imeet.vcard.resources;

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

public class UsersResource extends Resource {

private String vCard = null;

public UsersResource(Context context, Request request, Response response) {
 super(context, request, response);

 getVariants().add(new Variant(MediaType.TEXT_VCARD));
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
 if (null == this.vCard) {
  ErrorMessage em = new ErrorMessage();
  return representError(variant, em);
 } else {
   result = new StringRepresentation(this.vCard);
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

 vCardObjManager vcmgr = vCardObjManager.getInstance();
 try {
  if (entity.getMediaType().equals(MediaType.APPLICATION_WWW_FORM,true)) {
   Form form = new Form(entity);

   String token = form.getFirstValue("token",true,"");
   if(!validate(token)){
	   Representation resp = new StringRepresentation("Token Expired");
	   getResponse().setEntity(resp);
	   return;
   }
   getResponse().setStatus(Status.SUCCESS_OK);
   StringBuffer sbcacheId=new StringBuffer();
   //Get XML and marshall it
   String reqxml = form.getFirstValue("reqxml",true,"");
   IMeetUnMarshaller imeetum=IMeetUnMarshaller.getInstance();
   Data datavalues=imeetum.unMarshaller(reqxml);
   HashMap<String,String> hm=new HashMap<String,String>();
   List<Vcard> vcrds= datavalues.getDatas();
   Iterator it= vcrds.iterator();
   while(it.hasNext()){
	   Vcard vcrd=(Vcard)it.next();
	   hm.put(vcrd.getAttributeName(), vcrd.getValue());
   }
   String result="";
   String category=hm.get("Category");
   if(category == null || category.trim().equalsIgnoreCase(""))
     category="";
   else
      sbcacheId.append(category);
   String fname=hm.get("FirstName");
   if(fname == null || fname.trim().equalsIgnoreCase(""))
     fname="";
   else
      sbcacheId.append(fname);
   String lname=hm.get("LastName");
   if(lname == null || lname.trim().equalsIgnoreCase(""))
     lname="";
   else
      sbcacheId.append(lname);
   String street=hm.get("Street");
   if(street == null || street.trim().equalsIgnoreCase(""))
     street="";
   else
      sbcacheId.append(street);
   String city=hm.get("City");
   if(city == null || city.trim().equalsIgnoreCase(""))
     city="";
   else
      sbcacheId.append(city);
   String zip=hm.get("Zip");
   if(zip == null || zip.trim().equalsIgnoreCase(""))
     zip="";
   else
      sbcacheId.append(zip);
   String state=hm.get("State");
   if(state == null || state.trim().equalsIgnoreCase(""))
     state="";
   else
      sbcacheId.append(state);
   String country=hm.get("Country");
   if(country == null || country.trim().equalsIgnoreCase(""))
     country="";
   else
      sbcacheId.append(country);
   String wstreet=hm.get("WorkStreet");
   if(wstreet == null || wstreet.trim().equalsIgnoreCase(""))
        wstreet="";
   else
      sbcacheId.append(wstreet);
   String wcity=hm.get("WorkCity");
   if(wcity == null || wcity.trim().equalsIgnoreCase(""))
        wcity="";
   else
      sbcacheId.append(wcity);
   String wzip=hm.get("WorkZip");
   if(wzip == null || wzip.trim().equalsIgnoreCase(""))
        wzip="";
   else
      sbcacheId.append(wzip);
   String wstate=hm.get("WorkState");
   if(wstate == null || wstate.trim().equalsIgnoreCase(""))
        wstate="";
   else
      sbcacheId.append(wstate);
   String wcountry=hm.get("WorkCountry");
   if(wcountry == null || wcountry.trim().equalsIgnoreCase(""))
     wcountry="";
   else
      sbcacheId.append(wcountry);
   String workphone=hm.get("WorkPhone");
   if(workphone == null || workphone.trim().equalsIgnoreCase(""))
        workphone="";
   else
      sbcacheId.append(workphone);
   String homephone=hm.get("HomePhone");
   if(homephone == null || homephone.trim().equalsIgnoreCase(""))
        homephone="";
   else
      sbcacheId.append(homephone);
   String email=hm.get("Email");
   if(email == null || email.trim().equalsIgnoreCase(""))
        email="";
   else
      sbcacheId.append(email);
   String role=hm.get("Role");
   if(role == null || role.trim().equalsIgnoreCase(""))
       role="";
   else
      sbcacheId.append(role);
   String title=hm.get("Title");
   if(title == null || title.trim().equalsIgnoreCase(""))
     title="";
   else
      sbcacheId.append(title);
   String URL=hm.get("URL");
   if(URL == null || URL.trim().equalsIgnoreCase(""))
       URL="";
   else
      sbcacheId.append(URL);
   String orgname=hm.get("Organization");
   if(orgname == null || orgname.trim().equalsIgnoreCase(""))
      orgname="";
   else
      sbcacheId.append(orgname);
   String unit=hm.get("Unit");
   if(unit == null || unit.trim().equalsIgnoreCase(""))
     unit="";
   else
      sbcacheId.append(unit);
   String cacheId = ""+sbcacheId.toString().hashCode();
   String carddata = sbcacheId.toString();
   String cached_carddata=null;
   vCardObj vobj = vcmgr.getVCardObj(cacheId);
   if(vobj != null){
	 cached_carddata = vobj.getCardData();
	 if(cached_carddata != null && !cached_carddata.equalsIgnoreCase("")
			 && cached_carddata.equals(carddata))
        result=  vobj.getVCard();
	 else
		result = null;
   }
   if(result == null || result.trim().equalsIgnoreCase("")){
     result=getVCard(category,fname,lname,street,city, zip, state,country,wstreet, wcity,wzip,wstate,wcountry,workphone,homephone,email,role,title,URL,orgname,unit);
     vCardObj vobj1 = new vCardObj();
     vobj1.setCardData(carddata);
     vobj1.putVCard(result);
     vcmgr.putVCardObj(cacheId,vobj1);
     //result = result + "New vCard"+ cacheId;
   }
   //else
   //  result = result + "From Cache"+ cacheId;
   //-Representation rep = new StringRepresentation(result);
   final String res_vcard=result;
   OutputRepresentation rep = new OutputRepresentation(MediaType.TEXT_VCARD){
	   @Override
	   public void write(OutputStream os){
		   try{
		   os.write(res_vcard.getBytes());
		   } catch (IOException ioe){
			   ioe.printStackTrace();
		   }
	   }
   };
   rep.setDownloadName("vcard.vcf");
   rep.setDownloadable(true);
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
private String getVCard(String category,
			 String fname, String lname,
	         String street, String city, String zip, String state, String country,
	         String wstreet, String wcity, String wzip, String wstate, String wcountry,
	         String workphone, String homephone, String email, String role, String title,
	         String URL, String orgname, String unit){
			 String retValue="";

	         try{
			 			     ContactModelFactory cmf = Pim.getContactModelFactory();
			 			     Contact contact = cmf.createContact();
			 			     if(chkValue(category))
			 			 	  contact.addCategory(category);

			 			 	//a personal identity
			 			 	PersonalIdentity pid = cmf.createPersonalIdentity();
			 			 	pid.setFormattedName(fname+" "+lname);
			 			 	pid.setFirstname(fname);
			 			 	pid.setLastname(lname);
			 			 	contact.setPersonalIdentity(pid);

			 			 	//an address
			 			 	Address addr = cmf.createAddress();
			 			 	addr.setStreet(street);
			 			 	addr.setCity(city);
			 			 	addr.setPostalCode(zip);
			 			 	addr.setRegion(state);
			 			 	addr.setCountry(country);
			 			 	addr.setWork(false);
			 			 	if(chkValue(street) || chkValue(city) || chkValue(zip) || chkValue(state) || chkValue(country))
			 			 	  contact.addAddress(addr);

			                 //an address
			 				Address addrw = cmf.createAddress();
			 				addrw.setStreet(wstreet);
			 				addrw.setCity(wcity);
			 				addrw.setPostalCode(wzip);
			 				addrw.setRegion(wstate);
			 				addrw.setCountry(wcountry);
			 			 	addrw.setWork(true);
			 			 	if(chkValue(wstreet) || chkValue(wcity) || chkValue(wzip) || chkValue(wstate) || chkValue(wcountry))
			 			 	  contact.addAddress(addrw);
			 			 	//some communications
			 			 	Communications comm = cmf.createCommunications();
			 			 	contact.setCommunications(comm);

			 			 	//a phone number
			 			 	PhoneNumber number = cmf.createPhoneNumber();
			 			 	number.setNumber(workphone);
			 			 	number.setWork(true);
			 			 	if(chkValue(workphone))
			 			    	comm.addPhoneNumber(number);

			                 //a phone number
			 				PhoneNumber numberh = cmf.createPhoneNumber();
			 				numberh.setNumber(homephone);
			 				numberh.setWork(false);
			 				if(chkValue(homephone))
			 			 	   comm.addPhoneNumber(numberh);

			 			 	//an email address
			 			 	EmailAddress emailadd = cmf.createEmailAddress();
			 			 	emailadd.setAddress(email);
			 			 	if(chkValue(email))
			 			 	 comm.addEmailAddress(emailadd);

			 			 	//an organizational identity
			 			 	OrganizationalIdentity orgid = cmf.createOrganizationalIdentity();
			 			 	if(chkValue(role))
			 			 	  orgid.setRole(role);
			 			 	if(chkValue(title))
			 			 	  orgid.setTitle(title);

			 			 	//and the organization
			 			 	Organization org = cmf.createOrganization();
			 			 	if(chkValue(URL))
			 			 	  org.setURL(URL);
			 			 	if(chkValue(orgname))
			 			 	  org.setName(orgname);

			 			 	String[] tmp=null;
			 			 	if(unit!= null){
			 			        tmp = unit.split(",");
			 			        for (int i = 0 ; i < tmp.length ; i++)
			 			    	  org.addUnit(tmp[i]);
			 			    }
			 			    if(chkValue(URL) || chkValue(orgname) || chkValue(unit))
			 			 	  orgid.setOrganization(org);
			 			 	if(chkValue(role)|| chkValue(title) || chkValue(URL) || chkValue(orgname) || chkValue(unit))
			 			     contact.setOrganizationalIdentity(orgid);

			                ByteArrayOutputStream out = new ByteArrayOutputStream();
			 			 //and now write the vCard to standard out
			 			      ContactIOFactory ciof = Pim.getContactIOFactory();
			 			       ContactMarshaller marshaller = ciof.createContactMarshaller();
			 			       marshaller.marshallContact(out, contact);
			 			       retValue = out.toString();

			 			    } catch (Exception ex) {
			 			          ex.printStackTrace();
			        }

	         return retValue;
}
private boolean chkValue(String s){

		 	if(s==null || s.trim().equalsIgnoreCase(""))
		 	  return false;
		 	else
		 	  return true;

}
private boolean validate(String token){
	// Validate
	if(token !=null && !token.trim().equalsIgnoreCase("")
			&& token.equalsIgnoreCase("namepassword"))
		return true;
	else
		return false;
}
}