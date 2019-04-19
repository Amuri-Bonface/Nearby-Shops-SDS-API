package org.nearbyshops.sds.Globals;


import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class GlobalConfig {




    public static void loadGlobalConfiguration()
    {

        Configuration configuration = getConfiguration();


        if(configuration==null)
        {
            System.out.println("Configuration is null : Unable to load Global Configuration ... " +
                    "please ensure that you have configuration file with name api_config.properties and jar file in the same folder !");



            return;
        }




        GlobalConstants.BASE_URI = configuration.getString("base.url");
        GlobalConstants.POSTGRES_CONNECTION_URL = configuration.getString("connection.url");

        GlobalConstants.POSTGRES_USERNAME = configuration.getString("postgres.username");
        GlobalConstants.POSTGRES_PASSWORD = configuration.getString("postgres.password");


        GlobalConstants.ADMIN_USERNAME = configuration.getString("admin_username");
        GlobalConstants.ADMIN_PASSWORD = configuration.getString("admin_password");



        GlobalConstants.MAILGUN_DOMAIN = configuration.getString("mailgun.domain");
        GlobalConstants.MAILGUN_API_KEY = configuration.getString("mailgun.apikey");
        GlobalConstants.MAILGUN_NAME = configuration.getString("mailgun.name");
        GlobalConstants.MAILGUN_EMAIL = configuration.getString("mailgun.email");


        GlobalConstants.max_service_range = configuration.getInt("max_market_visibility_range");


        GlobalConstants.MSG91_SMS_SERVICE_API_KEY = configuration.getString("msg91.apikey");
        GlobalConstants.default_country_code_value = configuration.getString("default_country_code");
        GlobalConstants.sender_id_for_sms_value = configuration.getString("sender_id_for_sms");
        GlobalConstants.service_name_for_sms_value = configuration.getString("service_name_for_sms");


        GlobalConstants.enable_login_using_otp_value = configuration.getBoolean("enable_login_using_otp");
        GlobalConstants.url_for_notification_icon_value = configuration.getString("url_for_notification_icon");


        GlobalConstants.TOKEN_DURATION_MINUTES = configuration.getInt("token_duration_minutes");
        GlobalConstants.EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES = configuration.getInt("email_verification_code_expiry_minutes");
        GlobalConstants.PHONE_OTP_EXPIRY_MINUTES = configuration.getInt("phone_otp_expiry_minutes");
        GlobalConstants.PASSWORD_RESET_CODE_EXPIRY_MINUTES = configuration.getInt("password_reset_code_expiry_minutes");


        GlobalConstants.max_limit = configuration.getInt("max_limit");


        printGlobalConfiguration();
    }







    static void printGlobalConfiguration()
    {
        System.out.println("Printing API Configuration :  ");

        System.out.println("Base URI : " + GlobalConstants.BASE_URI);

        System.out.println("Postgres Connection URL : " + GlobalConstants.POSTGRES_CONNECTION_URL);
//        System.out.println("Connection URL Create DB : " + GlobalConstants.CONNECTION_URL_CREATE_DB);min_account_balance_for_shop_owner

        System.out.println("Postgres Username : " + GlobalConstants.POSTGRES_USERNAME);
        System.out.println("Postgres Password : " + GlobalConstants.POSTGRES_PASSWORD);

        System.out.println("MSG91_KEY : " + GlobalConstants.MSG91_SMS_SERVICE_API_KEY);

        System.out.println("Mailgun Domain : " + GlobalConstants.MAILGUN_DOMAIN);
        System.out.println("Mailgun API-KEY : " + GlobalConstants.MAILGUN_API_KEY);
        System.out.println("Mailgun Name : " + GlobalConstants.MAILGUN_NAME);
        System.out.println("Mailgun E-mail : " + GlobalConstants.MAILGUN_EMAIL);



        System.out.println("Token Duration Minutes : " + GlobalConstants.TOKEN_DURATION_MINUTES);
        System.out.println("Email Verification Code Expiry Minutes : " + GlobalConstants.EMAIL_VERIFICATION_CODE_EXPIRY_MINUTES);

        System.out.println("Phone OTP Expiry Minutes : " + GlobalConstants.PHONE_OTP_EXPIRY_MINUTES);
        System.out.println("Password Reset Code Expiry Minutes : " + GlobalConstants.PASSWORD_RESET_CODE_EXPIRY_MINUTES);

//        System.out.println("Trip Request Code Expiry Minutes : " + GlobalConstants.TRIP_REQUEST_EXPIRY_MINUTES);
//        System.out.println("Trip Request Code Expiry Extension Minutes : " + GlobalConstants.TRIP_REQUEST_EXPIRY_EXTENSION_MINUTES);
//
//
//        System.out.println("Months to extend Taxi Registration Minimum : " + GlobalConstants.MONTHS_TO_EXTEND_TAXI_REGISTRATION_MIN);
//        System.out.println("Months to extend Taxi Registration Maximum : " + GlobalConstants.MONTHS_TO_EXTEND_TAXI_REGISTRATION_MAX);

        System.out.println("MAX LIMIT : " + GlobalConstants.max_limit);
//        System.out.println("MQTT Host address : " + GlobalConstants.NOTIFICATION_SERVER_HOST_MQTT);
    }



    public static void reloadConfiguration()
    {

        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName("api_config.properties"));


        try
        {
            GlobalConfig.configuration = builder.getConfiguration();

        }
        catch(ConfigurationException cex)
        {
            // loading of the configuration file failed

            System.out.println(cex.getStackTrace());

            configuration = null;
        }
    }








    private static Configuration configuration;


    public static org.apache.commons.configuration2.Configuration getConfiguration() {
        if (configuration == null) {
            Parameters params = new Parameters();
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setFileName("api_config.properties"));


            try {
                configuration = builder.getConfiguration();

            } catch (ConfigurationException cex) {
                // loading of the configuration file failed

                System.out.println(cex.getStackTrace());

                configuration = null;
            }

            return configuration;
        } else {
            return configuration;
        }
    }



}
