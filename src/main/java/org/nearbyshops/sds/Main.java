package org.nearbyshops.sds;

import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.nearbyshops.sds.Globals.GlobalConfig;
import org.nearbyshops.sds.Globals.GlobalConstants;
import org.nearbyshops.sds.Globals.Globals;
import org.nearbyshops.sds.Model.ServiceConfigurationGlobal;
import org.nearbyshops.sds.ModelRoles.EmailVerificationCode;
import org.nearbyshops.sds.ModelRoles.PhoneVerificationCode;
import org.nearbyshops.sds.ModelRoles.StaffPermissions;
import org.nearbyshops.sds.ModelRoles.User;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Main__ class.
 *
 */






public class Main {

    // Base URI the Grizzly HTTP server will listen on

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */




    public static void startJettyServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in org.taxireferral.api package
        final ResourceConfig rc = new ResourceConfig().packages("org.nearbyshops.sds");


        System.out.println("Base URL : " + GlobalConstants.BASE_URI);
        JettyHttpContainerFactory.createServer(URI.create(GlobalConstants.BASE_URI),rc);
    }




    public static void main(String[] args) throws IOException {


        GlobalConfig.loadGlobalConfiguration();

//        createDB();
        upgradeTables();

        createTables();
        startJettyServer();

    }







    private static void createTables()
    {

        Connection connection = null;
        Statement statement = null;



        try {

            connection = DriverManager.getConnection(GlobalConstants.POSTGRES_CONNECTION_URL,
                    GlobalConstants.POSTGRES_USERNAME, GlobalConstants.POSTGRES_PASSWORD);


            statement = connection.createStatement();

            statement.executeUpdate(User.createTable);
            statement.executeUpdate(StaffPermissions.createTablePostgres);
            statement.executeUpdate(EmailVerificationCode.createTablePostgres);
            statement.executeUpdate(PhoneVerificationCode.createTablePostgres);


            // create table service configuration
            statement.executeUpdate(ServiceConfigurationGlobal.createTableServiceConfigurationPostgres);




            System.out.println("Tables Created ... !");





            // developers Note: whenever adding a table please check that tables it depends on are created first


            // Create admin account with given username and password if it does not exit | or update in case admin account exist

            User admin = new User();
            admin.setUsername(GlobalConstants.ADMIN_USERNAME);
            admin.setRole(1);
            admin.setPassword(GlobalConstants.ADMIN_PASSWORD);


            System.out.println("Admin Username : " + GlobalConstants.ADMIN_USERNAME + " | " + " Admin Password : " + GlobalConstants.ADMIN_PASSWORD);



            boolean adminRoleExist = Globals.daoUserSignUp.checkRoleExists(GlobalConstants.ROLE_ADMIN_CODE);

            if(adminRoleExist)
            {
                Globals.daoUserSignUp.updateAdminUsername(admin);
            }
            else
            {
                Globals.daoUserSignUp.createAdmin(admin,true);
            }




//
//            // Insert the root category whose ID is 1
//
//            String insertItemCategory = "";
//
//            // The root ItemCategory has id 1. If the root category does not exist then insert it.
//            if(Globals.itemCategoryDAO.checkRoot(1) == null)
//            {
//
//                insertItemCategory = "INSERT INTO "
//                        + ItemCategory.TABLE_NAME
//                        + "("
//                        + ItemCategory.ITEM_CATEGORY_ID + ","
//                        + ItemCategory.ITEM_CATEGORY_NAME + ","
//                        + ItemCategory.PARENT_CATEGORY_ID + ","
//                        + ItemCategory.ITEM_CATEGORY_DESCRIPTION + ","
//                        + ItemCategory.IMAGE_PATH + ","
//                        + ItemCategory.IS_LEAF_NODE + ") VALUES("
//                        + "" + "1"	+ ","
//                        + "'" + "ROOT"	+ "',"
//                        + "" + "NULL" + ","
//                        + "'" + "This is the root Category. Do not modify it." + "',"
//                        + "'" + " " + "',"
//                        + "'" + "FALSE" + "'"
//                        + ")";
//
//
//
//                statement.executeUpdate(insertItemCategory);
//
//            }








            // create directory images

            final java.nio.file.Path BASE_DIR = Paths.get("./images");

            File theDir = new File(BASE_DIR.toString());

            // if the directory does not exist, create it
            if (!theDir.exists()) {

                System.out.println("Creating directory: " + BASE_DIR.toString());

                boolean result = false;

                try{
                    theDir.mkdir();
                    result = true;
                }
                catch(Exception se){
                    //handle it
                }
                if(result) {
                    System.out.println("DIR created");
                }
            }





        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally{


            // close the connection and statement accountApproved

            if(statement !=null)
            {

                try {
                    statement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }


            if(connection!=null)
            {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }




    private static void upgradeTables()
    {

        Connection connection = null;
        Statement statement = null;


//        Configuration configuration = GlobalConfig.getConfiguration();
//
//
//        if(configuration==null)
//        {
//            System.out.println("Configuration is null : Upgrade Tables !");
//
//            return;
//        }
//
//
//        String connection_url = configuration.getString(ConfigurationKeys.CONNECTION_URL);
//        String username = configuration.getString(ConfigurationKeys.POSTGRES_USERNAME);
//        String password = configuration.getString(ConfigurationKeys.POSTGRES_PASSWORD);


        try {

//            connection = DriverManager.getConnection(connection_url, username,password);

            connection = DriverManager.getConnection(GlobalConstants.POSTGRES_CONNECTION_URL,
                    GlobalConstants.POSTGRES_USERNAME, GlobalConstants.POSTGRES_PASSWORD);


            statement = connection.createStatement();



            System.out.println("Tables Upgrade Complete ... !");


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally{


            // close the connection and statement accountApproved

            if(statement !=null)
            {

                try {
                    statement.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }


            if(connection!=null)
            {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }




}

