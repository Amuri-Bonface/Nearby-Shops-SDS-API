package org.nearbyshops.sds.RESTEndpointsLogin;


import com.google.gson.Gson;
import org.nearbyshops.sds.DAORoles.DAOLoginUsingOTP;
import org.nearbyshops.sds.DAORoles.DAOPhoneVerificationCodes;
import org.nearbyshops.sds.DAORoles.DAOUserNew;
import org.nearbyshops.sds.Globals.Globals;
import org.nearbyshops.sds.ModelRoles.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.Base64;
import java.util.StringTokenizer;





@Path("/api/v1/User/LoginGlobal")
public class LoginGlobalRESTEndpoint {


    private DAOUserNew daoUser = Globals.daoUserNew;
    private DAOLoginUsingOTP daoLoginUsingOTP = new DAOLoginUsingOTP();
    private DAOPhoneVerificationCodes daoPhoneVerificationCodes = Globals.daoPhoneVerificationCodes;


    private static final String AUTHENTICATION_SCHEME = "Basic";



//    @Path("/GetProfileWithLogin")





    @GET
    @Path("/VerifyCredentials")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfileWithLogin(
            @HeaderParam("Authorization")String headerParam,
            @QueryParam("GetUserProfile")boolean getUserProfile
    )
    {



        final String encodedUserPassword = headerParam.replaceFirst(AUTHENTICATION_SCHEME + " ", "");

        //Decode username and password
        String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));

        //Split username and password tokens
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        //Verifying Username and password
//        System.out.println(username);
//        System.out.println(password);


//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }






        User user = daoUser.verifyUser(username,password);

//        Gson gson = new Gson();
//        System.out.println(gson.toJson(user));






        if(user == null)
        {

            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }
        else
        {

            if(getUserProfile)
            {
                User userProfile = daoUser.getProfile(username,password);

                return Response.status(Response.Status.OK)
                        .entity(userProfile)
                        .build();
            }
            else
            {
                return Response.status(Response.Status.OK)
                        .build();
            }
        }
    }







}
