package org.nearbyshops.sds.Globals;

/**
 * Created by sumeet on 18/9/16.
 */
public class GlobalConstants {

    public static String BASE_URI = "http://0.0.0.0:5500";

    public static String POSTGRES_CONNECTION_URL;
//    public static String CONNECTION_URL_CREATE_DB;

    public static String POSTGRES_USERNAME;
    public static String POSTGRES_PASSWORD;


    public static String ADMIN_USERNAME;
    public static String ADMIN_PASSWORD;



    public static String MAILGUN_DOMAIN;
    public static String MAILGUN_API_KEY;
    public static String MAILGUN_NAME;
    public static String MAILGUN_EMAIL;


    public static int max_service_range;

    public static String MSG91_SMS_SERVICE_API_KEY = null;
    public static String default_country_code_value;
    public static String sender_id_for_sms_value;

    public static String service_name_for_sms_value;
    public static boolean enable_login_using_otp_value;
    public static String url_for_notification_icon_value;


    // constants
    public static int TOKEN_DURATION_MINUTES; // 24 hours for expiry of a token
    public static int EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES;
    public static int PHONE_OTP_EXPIRY_MINUTES;
    public static int PASSWORD_RESET_CODE_EXPIRY_MINUTES;

    public static int max_limit;





    // Global Constants

    // Whenever new shop is created it is checked that the delivery range of new shop falls under the given range
    // for the given Service Type
    // all values are in Kilometers
    // consider an Example: A shop which desires to Register under

    // These constants are defined for the sake of improving the performance and efficiency. When the services are divided
    // into various different services. The load on each service will become less. This will help us
    // achieve better performance. Instead of one single service hosting all the shops accross the entire globe.
    // Here we have different service for each city.

    public static final Integer SHOP_DELIVERY_RANGE_MIN_CITY = 0;
    public static final Integer SHOP_DELIVERY_RANGE_MAX_CITY = 300;

    public static final Integer SHOP_DELIVERY_RANGE_MIN_STATE = 300;
    public static final Integer SHOP_DELIVERY_RANGE_MAX_STATE = 2500;

    public static final Integer SHOP_DELIVERY_RANGE_MIN_COUNTRY = 2500;
    public static final Integer SHOP_DELIVERY_RANGE_MAX_COUNTRY = 7500;

    public static final Integer SHOP_DELIVERY_RANGE_MIN_GLOBAL = 7500;
    public static final Integer SHOP_DELIVERY_RANGE_MAX_GLOBAL = 25000;


    // A service
    public static final Integer SERVICE_RANGE_MIN_CITY = 300;
    public static final Integer SERVICE_RANGE_MAX_CITY = 500;

    public static final Integer SERVICE_RANGE_MIN_STATE = 2500;
    public static final Integer SERVICE_RANGE_MAX_STATE = 3000;

    public static final Integer SERVICE_RANGE_MIN_COUNTRY = 7500;
    public static final Integer SERVICE_RANGE_MAX_COUNTRY = 9500;

    public static final Integer SERVICE_RANGE_MIN_GLOBAL = 20500;
    public static final Integer SERVICE_RANGE_MAX_GLOBAL = 40700;


    // Constants for Service Level
    public static final Integer SERVICE_LEVEL_CITY = 1;
    public static final Integer SERVICE_LEVEL_STATE = 2;
    public static final Integer SERVICE_LEVEL_COUNTRY = 3;
    public static final Integer SERVICE_LEVEL_GLOBAL = 4;

    // Constants for service type
    public static final Integer SERVICE_TYPE_NONPROFIT = 1;
    public static final Integer SERVICE_TYPE_GOVERNMENT = 2;
    public static final Integer SERVICE_TYPE_COMMERTIAL = 3;


    // Constants for the Roles in the Application

//    public static final String ROLE_ADMIN = "ADMIN";
//    public static final String ROLE_STAFF_DISABLED = "STAFF_DISABLED";
//    public static final String ROLE_STAFF = "STAFF";
//    public static final String ROLE_END_USER = "END_USER";




    // role codes
    public static final int ROLE_ADMIN_CODE = 1;
    public static final int ROLE_STAFF_CODE = 2;
    public static final int ROLE_END_USER_CODE = 3;



    // Constants for the Roles in the Application
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_STAFF = "STAFF";
    public static final String ROLE_END_USER = "END_USER";

}
