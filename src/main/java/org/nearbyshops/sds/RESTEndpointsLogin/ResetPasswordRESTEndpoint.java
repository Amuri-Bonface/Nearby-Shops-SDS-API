package org.nearbyshops.sds.RESTEndpointsLogin;




import org.nearbyshops.sds.Globals.GlobalConstants;
import org.nearbyshops.sds.Globals.Globals;
import org.nearbyshops.sds.Globals.SendSMS;
import org.nearbyshops.sds.ModelRoles.User;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Random;

import static org.nearbyshops.sds.Globals.Globals.getMailerInstance;

/**
 * Created by sumeet on 14/8/17.
 */




@Path("/api/v1/User/ResetPassword")
public class ResetPasswordRESTEndpoint {




    /* Reset Password */
//    public Response resetPassword(User user)
//    public Response checkPasswordResetCode(@PathParam("emailOrPhone")String emailOrPhone,
//                                           @QueryParam("ResetCode")String resetCode)
//    public Response generateResetCode(User user)




    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response resetPassword(User user)
    {

        //        @Path("/ResetPassword")

//        System.out.println(
//                "Email : " + user.getEmail() +
//                        "Phone : " + user.getPhone() +
//                        "\nReset Code : " + user.getPasswordResetCode() +
//                        "\nResult : "
//        );

        int rowCount = Globals.daoResetPassword.resetPassword(user);

        if(rowCount >= 1)
        {

            return Response.status(Response.Status.OK)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }


        return null;
    }






//    , @PathParam("EmailOrPhone") String emailOrPhone
//    /{EmailOrPhone}







    @GET
    @Path("/CheckPasswordResetCode/{emailOrPhone}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkPasswordResetCode(
            @PathParam("emailOrPhone")String emailOrPhone,
            @QueryParam("ResetCode")String resetCode
    )
    {
        // Roles allowed not used for this method due to performance and effeciency requirements. Also
        // this endpoint doesnt required to be secured as it does not expose any confidential information

        boolean result = Globals.daoResetPassword.checkPasswordResetCode(emailOrPhone,resetCode);

//        System.out.println(
//                "EmailOrPhone : " + emailOrPhone +
//                        "\nReset Code : " + resetCode +
//                        "\nResult : " + Boolean.toString(result)
//        );


//        System.out.println(email);

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        if(result)
        {
            return Response.status(Response.Status.OK)
                    .build();

        } else
        {
            return Response.status(Response.Status.NO_CONTENT)
                    .build();
        }

    }







    @PUT
    @Path("/GenerateResetCode")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response generateResetCode(User user)
    {

        int rowCount = 0;
        String resetCode = "";
        Timestamp timestampExpiry = null;



        if(!Globals.daoResetPassword.checkPasswordResetCodeExpired(user))
        {
            // code is expired

//            resetCode = new BigInteger(30, Globals.random).toString(32);


//            BigInteger phoneCode = new BigInteger(15, Globals.random);
//            int phoneOTP = phoneCode.intValue();
//            resetCode = String.valueOf(phoneOTP);

            resetCode = String.valueOf(Globals.generateOTP(5));


            timestampExpiry
                    = new Timestamp(
                    System.currentTimeMillis()
                            + GlobalConstants.PASSWORD_RESET_CODE_EXPIRY_MINUTES * 60 * 1000
            );


            rowCount = Globals.daoResetPassword.updateResetCode(user,resetCode,timestampExpiry);


            if(rowCount==1)
            {
                // saved successfully


                if(user.getRt_registration_mode()==User.REGISTRATION_MODE_EMAIL)
                {





//                    Mail.using(Globals.getMailgunConfiguration())
//                            .body()
//                            .h1("Your E-mail Verification Code is given below")
//                            .p("You have requested to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.")
//                            .h3("The e-mail verification code is : " + resetCode)
//                            .p("This verification code will expire at " + timestampExpiry.toLocaleString() + ". Please use this code before it expires.")
//                            .mail()
//                            .to(user.getEmail())
//                            .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
//                            .from("Taxi Referral Service","noreply@taxireferral.org")
//                            .build()
//                            .send();



//                    String message = "The e-mail verification code is : " + resetCode +
//                            "  | This verification code will expire at " + timestampExpiry.toLocaleString() + ". Please use this code before it expires.";
//
//
//
//                    Globals.sendEmail(user.getEmail(),user.getEmail(),"E-mail Verification Code",message);



                        String htmlText = "";



                        htmlText = "<p>You have made a request to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.</p>"
                                + "<h2>The e-mail verification code is : " + resetCode + "</h2>" +
                                "<p>This verification code will expire at " +
                                timestampExpiry.toLocalDateTime().getHour() + ":" + timestampExpiry.toLocalDateTime().getMinute() + ":"+ timestampExpiry.toLocalDateTime().getSecond()
                                + ". Please use this code before it expires.<p>";




                        Email emailComposed = EmailBuilder.startingBlank()
                                .from(GlobalConstants.EMAIL_SENDER_NAME, GlobalConstants.EMAIL_ADDRESS_FOR_SENDER)
                                .to("user",user.getEmail())
                                .withSubject("E-mail Verification Code")
                                .withHTMLText(htmlText)
                                .buildEmail();

                        getMailerInstance().sendMail(emailComposed,true);



                }
                else if(user.getRt_registration_mode()==User.REGISTRATION_MODE_PHONE)
                {
                    SendSMS.sendOTP(resetCode,user.getPhone());
                }


            }



        }
        else
        {
            // code is not expired


            User user_credentials = Globals.daoResetPassword.getResetCode(user);


            if(user.getRt_registration_mode()==User.REGISTRATION_MODE_EMAIL)
            {
//                Mail.using(Globals.getMailgunConfiguration())
//                        .body()
//                        .h1("Your E-mail Verification Code is given below")
//                        .p("You have requested to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.")
//                        .h3("The e-mail verification code is : " + user_credentials.getPasswordResetCode())
//                        .p("This verification code will expire at " + user_credentials.getResetCodeExpires().toLocaleString() + ". Please use this code before it expires.")
//                        .mail()
//                        .to(user.getEmail())
//                        .subject("E-mail Verification Code for Taxi Referral Service (TRS)")
//                        .from("Taxi Referral Service","noreply@taxireferral.org")
//                        .build()
//                        .send();




//
//                String message = "The e-mail verification code is : " + user_credentials.getPasswordResetCode() +
//                        "  | This verification code will expire at " + user_credentials.getResetCodeExpires().toLocaleString() + ". Please use this code before it expires.";
//
//
//
//                Globals.sendEmail(user.getEmail(),user.getEmail(),"E-mail Verification Code",message);



                String htmlText = "";



                htmlText = "<p>You have made a request to verify your e-mail. If you did not request the e-mail verification please ignore this e-mail message.</p>"
                        + "<h2>The e-mail verification code is : " + user_credentials.getPasswordResetCode() + "</h2>" +
                        "<p>This verification code will expire at " +
                        user_credentials.getResetCodeExpires().toLocalDateTime().getHour() + ":" + user_credentials.getResetCodeExpires().toLocalDateTime().getMinute()
                        + ":"+ user_credentials.getResetCodeExpires().toLocalDateTime().getSecond()
                        + ". Please use this code before it expires.<p>";




                Email emailComposed = EmailBuilder.startingBlank()
                        .from(GlobalConstants.EMAIL_SENDER_NAME, GlobalConstants.EMAIL_ADDRESS_FOR_SENDER)
                        .to("user",user.getEmail())
                        .withSubject("E-mail Verification Code")
                        .withHTMLText(htmlText)
                        .buildEmail();

                getMailerInstance().sendMail(emailComposed,true);




            }
            else if(user.getRt_registration_mode()==User.REGISTRATION_MODE_PHONE)
            {
                SendSMS.sendOTP(user_credentials.getPasswordResetCode(),user.getPhone());
            }



            rowCount=1;
        }






        if(rowCount >= 1)
        {

            return Response.status(Response.Status.OK)
                    .build();
        }
        if(rowCount == 0)
        {

            return Response.status(Response.Status.NOT_MODIFIED)
                    .build();
        }


        return null;
    }






}
