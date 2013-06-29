/**
 * Mar 13, 2009
 * @author Neeraj Goswamy
 */
package com.pgi.imeet.vcard;

import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.Router;
import org.restlet.Guard;
import org.restlet.data.MediaType;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.data.ChallengeScheme;

/**
 * This class is responsible for all delegation related tasks.
 */
public class VCardApplication extends Application {

    public VCardApplication() {
	/* Do not touch this */
    }

    /**
     * @param context
     */
    public VCardApplication(Context context) {
	super(context);
	// override if required.
    }

    /*
     * These are just my patterns. Feel free to modify according to your
     * requirements.
     */
    public synchronized Restlet createRoot() {
    //Guard guard = new Guard(getContext(),
    //      ChallengeScheme.HTTP_BASIC, "VCARD");
    //guard.getSecrets().put("name", "password".toCharArray());
	Router router = new Router(getContext());
	router.attach("/vcard/", com.pgi.imeet.vcard.resources.UsersResource.class);
    router.attach("/vcard/auth", com.pgi.imeet.vcard.authenticate.AuthToken.class);

	/*Restlet restlet = new Restlet() {
	    @Override
	    public void handle(Request request, Response response) {
			response.setEntity("RESTlet is running", MediaType.TEXT_PLAIN);
        }
    }; */
    //router.attach("", restlet);
	//router.attach("/user/", com.pgi.imeet.vcard.resources.UsersResource.class);
	// router.attach("/user/{user_id}", UserResource.class);
	// router.attach("/user/{user_id}/contact", UserContactsResource.class);
	// router.attach("/user/{user_id}/contact/{contact_id}",
	// UserContactResource.class);
	//-guard.setNext(router);
	//-return guard;
	return router;
    }

}
