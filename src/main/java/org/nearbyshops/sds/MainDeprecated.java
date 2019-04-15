//package org.nearbyshops.sds;
//
//import org.eclipse.jetty.server.Server;
////import org.glassfish.grizzly.http.server.HttpServer;
////import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
//import org.eclipse.jetty.util.ssl.SslContextFactory;
//import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.nearbyshops.sds.Globals.Globals;
//import org.nearbyshops.sds.Model.ServiceConfigurationGlobal;
//
//import javax.ws.rs.core.UriBuilder;
//import java.io.File;
//import java.io.IOException;
//import java.net.URI;
//import java.nio.file.Paths;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//
///**
// * MainDeprecated class.
// *
// */
//
//
//
//public class MainDeprecated {
//    // Base URI the Grizzly HTTP server will listen on
//    public static final String BASE_URI = "http://0.0.0.0:5125/api/";
//
//    /**
//     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
//     * @return Grizzly HTTP server.
//     */
//    public static Server startServer() {
//        // create a resource config that scans for JAX-RS resources and providers
//        // in org.nearbyshops.sds package
//        final ResourceConfig rc = new ResourceConfig().packages("org.nearbyshops.sds");
//
//        // create and start a new instance of grizzly http server
//        // exposing the Jersey application at BASE_URI
////        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
//
//
////        URI baseUri = UriBuilder.fromUri("http://localhost/").port(8888).build();
////        ResourceConfig config = new ResourceConfig(TestResource.class);
////        Server server = JettyHttpContainerFactory.createServer(baseUri, config);
//
//        SslContextFactory factory = new SslContextFactory();
//
//
//        return JettyHttpContainerFactory.createServer(URI.create(BASE_URI),rc);
//    }
//
//    /**
//     * MainDeprecated method.
//     * @param args
//     * @throws IOException
//     */
//    public final static void main(String[] args) throws IOException {
//
//
//        createDB();
//        createTables();
//
////        final HttpServer server = startServer();
//
//        startServer();
//
////        System.out.println(String.format("Jersey app started with WADL available at "
////                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
//
////        System.in.read();
////        server.stop();
//    }
//
//
//
//    private static void createDB()
//    {
//
//        Connection conn = null;
//        Statement stmt = null;
//
//        try {
//
//            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres"
//                    ,JDBCContract.CURRENT_USERNAME
//                    ,JDBCContract.CURRENT_PASSWORD);
//
//            stmt = conn.createStatement();
//
//            String createDB = "CREATE DATABASE \"SDS_DB\" WITH ENCODING='UTF8' OWNER=postgres CONNECTION LIMIT=-1";
//
//            stmt.executeUpdate(createDB);
//
//        }
//        catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        finally{
//
//
//            // close the connection and statement accountApproved
//
//            if(stmt !=null)
//            {
//
//                try {
//                    stmt.close();
//                } catch (SQLException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//            }
//
//
//            if(conn!=null)
//            {
//                try {
//                    conn.close();
//                } catch (SQLException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
//
//
//
//
//    private static void createTables()
//    {
//
//        Connection connection = null;
//        Statement statement = null;
//
//        try {
//
//            connection = DriverManager.getConnection(JDBCContract.CURRENT_CONNECTION_URL
//                    ,JDBCContract.CURRENT_USERNAME
//                    ,JDBCContract.CURRENT_PASSWORD);
//
//            statement = connection.createStatement();
//
//
////            statement.executeUpdate(ItemCategory.createTableItemCategoryPostgres);
////            statement.executeUpdate(Item.createTableItemPostgres);
//
//            // Create Table Admin
//            statement.executeUpdate(Usernames.createTableUsernamesPostgres);
//            statement.executeUpdate(Admin.createTableAdminPostgres);
//            statement.executeUpdate(Staff.createTableStaffPostgres);
//
//
//            // create table service configuration
//            statement.executeUpdate(ServiceConfigurationGlobal.createTableServiceConfigurationPostgres);
//
//            System.out.println("Tables Created ... !");
//
//
//
//
//            // developers Note: whenever adding a table please check that its dependencies are already created.
//
//
//
//            // Insert the default administrator if it does not exit
//
//            if(Globals.adminDAOPrepared.getAdmin(null,null).size()<=0)
//            {
//                Admin defaultAdmin = new Admin();
//
//                defaultAdmin.setPassword("password");
//                defaultAdmin.setUsername("username");
//                defaultAdmin.setAdministratorName("default name");
//
//                Globals.adminDAOPrepared.saveDefaultAdmin(defaultAdmin);
//            }
//
//
//
//
//
////            // Insert the default administrator if it does not exit
////
////            if(Globals.adminDAOPrepared.getAdmin(1)==null)
////            {
////                Admin defaultAdminInheritance = new Admin();
////
////                defaultAdminInheritance.setPassword("password");
////                defaultAdminInheritance.setUsername("username");
////                defaultAdminInheritance.setAdministratorName("default name");
////
////                Globals.adminDAOPrepared.saveDefaultAdmin(defaultAdminInheritance);
////            }
////
//
//
//
//
//
//
//            // Insert Default Settings
////            SettingsDAOPrepared settingsDAO = Globals.settingsDAOPrepared;
//
////            if(settingsDAO.getServiceConfiguration()==null){
////                settingsDAO.saveSettings(settingsDAO.getDefaultConfiguration());
////            }
//
//
//
//
//            // Insert Default Service Configuration
//
////            String insertServiceConfig = "";
//
////            if(Globals.serviceConfigurationDAO.getServiceConfiguration()==null)
////            {
//
////                ServiceConfigurationLocal defaultConfiguration = new ServiceConfigurationLocal();
//
////                defaultConfiguration.setServiceLevel(GlobalConstants.SERVICE_LEVEL_CITY);
////                defaultConfiguration.setServiceType(GlobalConstants.SERVICE_TYPE_NONPROFIT);
////                defaultConfiguration.setServiceID(1);
////                defaultConfiguration.setServiceName("DEFAULT_CONFIGURATION");
//
////                Globals.serviceConfigurationDAO.saveService(defaultConfiguration);
//
////            }
//
//
//            // create directory images
//
//            final java.nio.file.Path BASE_DIR = Paths.get("./images");
//
//            File theDir = new File(BASE_DIR.toString());
//
//            // if the directory does not exist, create it
//            if (!theDir.exists()) {
//
//                System.out.println("Creating directory: " + BASE_DIR.toString());
//
//                boolean result = false;
//
//                try{
//                    theDir.mkdir();
//                    result = true;
//                }
//                catch(Exception se){
//                    //handle it
//                }
//                if(result) {
//                    System.out.println("DIR created");
//                }
//            }
//
//
//
//
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        finally{
//
//
//            // close the connection and statement accountApproved
//
//            if(statement !=null)
//            {
//
//                try {
//                    statement.close();
//                } catch (SQLException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//            }
//
//
//            if(connection!=null)
//            {
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//}
//
