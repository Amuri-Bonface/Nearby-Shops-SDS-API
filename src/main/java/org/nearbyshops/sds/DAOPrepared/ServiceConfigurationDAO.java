package org.nearbyshops.sds.DAOPrepared;


import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.sds.Globals.Globals;
import org.nearbyshops.sds.Model.Endpoints.ServiceConfigurationEndPoint;
import org.nearbyshops.sds.Model.ServiceConfigurationGlobal;
import org.nearbyshops.sds.ModelReviewMarket.FavouriteMarket;
import org.nearbyshops.sds.ModelReviewMarket.MarketReview;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by sumeet on 28/12/16.
 */
public class ServiceConfigurationDAO {

    private HikariDataSource dataSource = Globals.getDataSource();


    public int insertServiceConfig(ServiceConfigurationGlobal serviceConfigurationGlobal)
    {

        Connection connection = null;
        PreparedStatement statement = null;
        int rowIdOfInsertedRow = -1;

        String insertShop = "INSERT INTO "
                + ServiceConfigurationGlobal.TABLE_NAME
                + "("

                + ServiceConfigurationGlobal.LOGO_IMAGE_PATH + ","
                + ServiceConfigurationGlobal.BACKDROP_IMAGE_PATH + ","

                + ServiceConfigurationGlobal.SERVICE_NAME + ","
                + ServiceConfigurationGlobal.HELPLINE_NUMBER + ","

                + ServiceConfigurationGlobal.DESCRIPTION_SHORT + ","
                + ServiceConfigurationGlobal.DESCRIPTION_LONG + ","

                + ServiceConfigurationGlobal.ADDRESS + ","
                + ServiceConfigurationGlobal.CITY + ","
                + ServiceConfigurationGlobal.PINCODE + ","
                + ServiceConfigurationGlobal.LANDMARK + ","
                + ServiceConfigurationGlobal.STATE + ","
                + ServiceConfigurationGlobal.COUNTRY + ","

                + ServiceConfigurationGlobal.ISO_COUNTRY_CODE + ","
                + ServiceConfigurationGlobal.ISO_LANGUAGE_CODE + ","
                + ServiceConfigurationGlobal.ISO_CURRENCY_CODE + ","

                + ServiceConfigurationGlobal.SERVICE_TYPE + ","
                + ServiceConfigurationGlobal.SERVICE_LEVEL + ","

                + ServiceConfigurationGlobal.LAT_CENTER + ","
                + ServiceConfigurationGlobal.LON_CENTER + ","

                + ServiceConfigurationGlobal.SERVICE_RANGE + ","

                + ServiceConfigurationGlobal.IS_OFFICIAL_SERVICE_PROVIDER + ","
                + ServiceConfigurationGlobal.IS_VERIFIED + ","
                + ServiceConfigurationGlobal.SERVICE_URL + ","

                + ServiceConfigurationGlobal.CREATED + ","
                + ServiceConfigurationGlobal.UPDATED + ""
                + " ) VALUES (?,? ,?,? ,?,? ,?,?,?,?,?,? ,?,?,?, ?,?, ?,?, ?, ?,?,? ,?,?)";

        try {

            connection = dataSource.getConnection();

            statement = connection.prepareStatement(insertShop,PreparedStatement.RETURN_GENERATED_KEYS);

            int i = 0;
            statement.setString(++i,serviceConfigurationGlobal.getLogoImagePath());
            statement.setString(++i,serviceConfigurationGlobal.getBackdropImagePath());
            statement.setString(++i,serviceConfigurationGlobal.getServiceName());
            statement.setString(++i,serviceConfigurationGlobal.getHelplineNumber());

            statement.setString(++i,serviceConfigurationGlobal.getDescriptionShort());
            statement.setString(++i,serviceConfigurationGlobal.getDescriptionLong());

            statement.setString(++i,serviceConfigurationGlobal.getAddress());
            statement.setString(++i,serviceConfigurationGlobal.getCity());
            statement.setObject(++i,serviceConfigurationGlobal.getPincode());
            statement.setString(++i,serviceConfigurationGlobal.getLandmark());
            statement.setString(++i,serviceConfigurationGlobal.getState());
            statement.setString(++i,serviceConfigurationGlobal.getCountry());

            statement.setString(++i,serviceConfigurationGlobal.getISOCountryCode());
            statement.setString(++i,serviceConfigurationGlobal.getISOLanguageCode());
            statement.setString(++i,serviceConfigurationGlobal.getISOCurrencyCode());

            statement.setObject(++i,serviceConfigurationGlobal.getServiceType());
            statement.setObject(++i,serviceConfigurationGlobal.getServiceLevel());

            statement.setObject(++i,serviceConfigurationGlobal.getLatCenter());
            statement.setObject(++i,serviceConfigurationGlobal.getLonCenter());

            statement.setObject(++i,serviceConfigurationGlobal.getServiceRange());

            statement.setObject(++i,serviceConfigurationGlobal.getOfficial());
            statement.setObject(++i,serviceConfigurationGlobal.getVerified());
            statement.setString(++i,serviceConfigurationGlobal.getServiceURL());

            statement.setObject(++i,serviceConfigurationGlobal.getCreated());
            statement.setObject(++i,serviceConfigurationGlobal.getUpdated());

            rowIdOfInsertedRow = statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();

            if(rs.next())
            {
                rowIdOfInsertedRow = rs.getInt(1);
            }

            System.out.println("Key autogenerated ServiceConfigurationGlobal : " + rowIdOfInsertedRow);


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return rowIdOfInsertedRow;
    }


    public int updateServiceConfig(ServiceConfigurationGlobal serviceConfigurationGlobal)
    {


        String updateStatement = "UPDATE " + ServiceConfigurationGlobal.TABLE_NAME
                + " SET "

                + ServiceConfigurationGlobal.LOGO_IMAGE_PATH + "= ?,"
                + ServiceConfigurationGlobal.BACKDROP_IMAGE_PATH + "= ?,"

                + ServiceConfigurationGlobal.SERVICE_NAME + "= ?,"
                + ServiceConfigurationGlobal.HELPLINE_NUMBER + "= ?,"

                + ServiceConfigurationGlobal.DESCRIPTION_SHORT + "=?,"
                + ServiceConfigurationGlobal.DESCRIPTION_LONG + "=?,"

                + ServiceConfigurationGlobal.ADDRESS + "=?,"
                + ServiceConfigurationGlobal.CITY + "=?,"
                + ServiceConfigurationGlobal.PINCODE + "=?,"
                + ServiceConfigurationGlobal.LANDMARK + "=?,"
                + ServiceConfigurationGlobal.STATE + "=?,"
                + ServiceConfigurationGlobal.COUNTRY + "=?,"

                + ServiceConfigurationGlobal.ISO_COUNTRY_CODE + "=?,"
                + ServiceConfigurationGlobal.ISO_LANGUAGE_CODE + "=?,"
                + ServiceConfigurationGlobal.ISO_CURRENCY_CODE + "=?,"

                + ServiceConfigurationGlobal.SERVICE_TYPE + "=?,"
                + ServiceConfigurationGlobal.SERVICE_LEVEL + "=?,"

                + ServiceConfigurationGlobal.LAT_CENTER + "=?,"
                + ServiceConfigurationGlobal.LON_CENTER + "=?,"

                + ServiceConfigurationGlobal.SERVICE_RANGE + "=?,"

//                + ServiceConfigurationGlobal.IS_OFFICIAL_SERVICE_PROVIDER + "=?,"
//                + ServiceConfigurationGlobal.IS_VERIFIED + "=?,"
//                + ServiceConfigurationGlobal.SERVICE_URL + "=?,"

                + ServiceConfigurationGlobal.CREATED + "=?,"
                + ServiceConfigurationGlobal.UPDATED + "=?"

                + " WHERE " + ServiceConfigurationGlobal.SERVICE_URL + " = ?";



        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);


            int i = 0;
            statement.setString(++i,serviceConfigurationGlobal.getLogoImagePath());
            statement.setString(++i,serviceConfigurationGlobal.getBackdropImagePath());
            statement.setString(++i,serviceConfigurationGlobal.getServiceName());
            statement.setString(++i,serviceConfigurationGlobal.getHelplineNumber());

            statement.setString(++i,serviceConfigurationGlobal.getDescriptionShort());
            statement.setString(++i,serviceConfigurationGlobal.getDescriptionLong());

            statement.setString(++i,serviceConfigurationGlobal.getAddress());
            statement.setString(++i,serviceConfigurationGlobal.getCity());
            statement.setObject(++i,serviceConfigurationGlobal.getPincode());
            statement.setString(++i,serviceConfigurationGlobal.getLandmark());
            statement.setString(++i,serviceConfigurationGlobal.getState());
            statement.setString(++i,serviceConfigurationGlobal.getCountry());

            statement.setString(++i,serviceConfigurationGlobal.getISOCountryCode());
            statement.setString(++i,serviceConfigurationGlobal.getISOLanguageCode());
            statement.setString(++i,serviceConfigurationGlobal.getISOCurrencyCode());

            statement.setObject(++i,serviceConfigurationGlobal.getServiceType());
            statement.setObject(++i,serviceConfigurationGlobal.getServiceLevel());

            statement.setObject(++i,serviceConfigurationGlobal.getLatCenter());
            statement.setObject(++i,serviceConfigurationGlobal.getLonCenter());




            statement.setObject(++i,serviceConfigurationGlobal.getServiceRange());

//            statement.setObject(++i,serviceConfigurationGlobal.getOfficial());
//            statement.setObject(++i,serviceConfigurationGlobal.getVerified());

            statement.setObject(++i,serviceConfigurationGlobal.getCreated());
            statement.setObject(++i,serviceConfigurationGlobal.getUpdated());
            statement.setString(++i,serviceConfigurationGlobal.getServiceURL());

            updatedRows = statement.executeUpdate();


            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally

        {

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return updatedRows;
    }




    public int updateForStaff(ServiceConfigurationGlobal serviceConfigurationGlobal)
    {


        String updateStatement =
                        " UPDATE " + ServiceConfigurationGlobal.TABLE_NAME +
                        " SET " + ServiceConfigurationGlobal.IS_OFFICIAL_SERVICE_PROVIDER + "=?,"
                                + ServiceConfigurationGlobal.IS_VERIFIED + "=?"
                        + " WHERE " + ServiceConfigurationGlobal.SERVICE_URL + " = ?";


        Connection connection = null;
        PreparedStatement statement = null;
        int updatedRows = -1;

        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);


            int i = 0;
            statement.setObject(++i,serviceConfigurationGlobal.getOfficial());
            statement.setObject(++i,serviceConfigurationGlobal.getVerified());
            statement.setObject(++i,serviceConfigurationGlobal.getServiceURL());
            updatedRows = statement.executeUpdate();


            System.out.println("Total rows updated: " + updatedRows);

            //conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally

        {

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return updatedRows;

    }




    public int deleteService(int serviceConfigurationID)
    {

        String deleteStatement = "DELETE FROM " + ServiceConfigurationGlobal.TABLE_NAME
                + " WHERE " + ServiceConfigurationGlobal.SERVICE_CONFIGURATION_ID + "= ?";


        Connection connection= null;
        PreparedStatement statement = null;
        int rowCountDeleted = 0;
        try {

            connection = dataSource.getConnection();
            statement = connection.prepareStatement(deleteStatement);
            statement.setObject(1,serviceConfigurationID);


            rowCountDeleted = statement.executeUpdate();
            System.out.println(" Deleted Count: " + rowCountDeleted);

            connection.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        finally

        {

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return rowCountDeleted;
    }





//    Double proximity,
//    Boolean isOfficial, Boolean isVerified,
//    Integer serviceType,


    public ServiceConfigurationEndPoint getListQuerySimple(
            Double latCenter, Double lonCenter,
            String serviceURL, String searchString,
            boolean getSavedMarkets,int endUserID,
            String sortBy,
            Integer limit, Integer offset
    )
    {

        String query = "";
        String queryJoin = "";

        // flag for tracking whether to put "AND" or "WHERE"
//        boolean isFirst = true;


        String queryNormal = "SELECT "
                + "6371 * acos( cos( radians("
                + latCenter + ")) * cos( radians( " + ServiceConfigurationGlobal.LAT_CENTER + ") ) * cos(radians( " + ServiceConfigurationGlobal.LON_CENTER + " ) - radians("
                + lonCenter + "))" + " + sin( radians(" + latCenter + ")) * sin(radians( " + ServiceConfigurationGlobal.LAT_CENTER + " ))) as distance" + ","

                + "count(*) over() AS full_count " + ","

                +  "avg(" + MarketReview.TABLE_NAME + "." + MarketReview.RATING + ") as avg_rating" + ","
                +  "count( DISTINCT " + MarketReview.TABLE_NAME + "." + MarketReview.END_USER_ID + ") as rating_count" + ","

                + ServiceConfigurationGlobal.SERVICE_CONFIGURATION_ID + ","
                + ServiceConfigurationGlobal.LOGO_IMAGE_PATH + ","
                + ServiceConfigurationGlobal.BACKDROP_IMAGE_PATH + ","

                + ServiceConfigurationGlobal.SERVICE_NAME + ","
                + ServiceConfigurationGlobal.HELPLINE_NUMBER + ","

                + ServiceConfigurationGlobal.DESCRIPTION_SHORT + ","
                + ServiceConfigurationGlobal.DESCRIPTION_LONG + ","

                + ServiceConfigurationGlobal.ADDRESS + ","
                + ServiceConfigurationGlobal.CITY + ","
                + ServiceConfigurationGlobal.PINCODE + ","
                + ServiceConfigurationGlobal.LANDMARK + ","
                + ServiceConfigurationGlobal.STATE + ","
                + ServiceConfigurationGlobal.COUNTRY + ","

                + ServiceConfigurationGlobal.ISO_COUNTRY_CODE + ","
                + ServiceConfigurationGlobal.ISO_LANGUAGE_CODE + ","
                + ServiceConfigurationGlobal.ISO_CURRENCY_CODE + ","

                + ServiceConfigurationGlobal.SERVICE_TYPE + ","
                + ServiceConfigurationGlobal.SERVICE_LEVEL + ","

                + ServiceConfigurationGlobal.LAT_CENTER + ","
                + ServiceConfigurationGlobal.LON_CENTER + ","

                + ServiceConfigurationGlobal.SERVICE_RANGE + ","

                + ServiceConfigurationGlobal.IS_OFFICIAL_SERVICE_PROVIDER + ","
                + ServiceConfigurationGlobal.IS_VERIFIED + ","
                + ServiceConfigurationGlobal.SERVICE_URL + ","

                + ServiceConfigurationGlobal.CREATED + ","
                + ServiceConfigurationGlobal.UPDATED + ""

                + " FROM " + ServiceConfigurationGlobal.TABLE_NAME
                + " LEFT OUTER JOIN " + MarketReview.TABLE_NAME  + " ON (" + MarketReview.TABLE_NAME + "." + MarketReview.ITEM_ID + " = " + ServiceConfigurationGlobal.TABLE_NAME + "." + ServiceConfigurationGlobal.SERVICE_CONFIGURATION_ID + ")";


                if(getSavedMarkets)
                {
                    queryNormal = queryNormal + " INNER JOIN " + FavouriteMarket.TABLE_NAME  + " ON (" + FavouriteMarket.TABLE_NAME + "." + FavouriteMarket.ITEM_ID + " = " + ServiceConfigurationGlobal.TABLE_NAME + "." + ServiceConfigurationGlobal.SERVICE_CONFIGURATION_ID + ")";
                }


                queryNormal = queryNormal + " WHERE TRUE ";




                if(getSavedMarkets)
                {
                    queryNormal = queryNormal + " AND " + FavouriteMarket.TABLE_NAME + "." + FavouriteMarket.END_USER_ID + " = " + endUserID;
                }




        // Visibility Filter : Apply
        if(latCenter != null && lonCenter != null)
        {

            String queryPartlatLonCenter = "";

//            queryNormal = queryNormal + " AND ";
            // reset the flag
//            isFirst = false;

            queryPartlatLonCenter = queryPartlatLonCenter
                    + "6371 * acos( cos( radians("
                    + latCenter + ")) * cos( radians( " + ServiceConfigurationGlobal.LAT_CENTER + ") ) * cos(radians( " + ServiceConfigurationGlobal.LON_CENTER + " ) - radians("
                    + lonCenter + "))" + " + sin( radians(" + latCenter + ")) * sin(radians( " + ServiceConfigurationGlobal.LAT_CENTER + " ))) <= " + ServiceConfigurationGlobal.SERVICE_RANGE;


            queryNormal = queryNormal + " AND " + queryPartlatLonCenter;

        }






//        // Proximity Filter
//        if(proximity != null)
//        {
//            // proximity > 0 && (deliveryRangeMax==0 || (deliveryRangeMax > 0 && proximity <= deliveryRangeMax))
//
//            String queryPartProximity = "";
////			String queryPartProximityTwo = "";
//
//
//            // filter using Haversine formula using SQL math functions
//            queryPartProximity = queryPartProximity
//                    + " (6371.01 * acos(cos( radians(" + latCenter + ")) * cos( radians(" + ServiceConfigurationGlobal.LAT_CENTER + " )) * cos(radians( "
//                    + ServiceConfigurationGlobal.LON_CENTER + ") - radians(" + lonCenter + "))" + " + sin( radians(" + latCenter + ")) * sin(radians("
//                    + ServiceConfigurationGlobal.LAT_CENTER + ")))) <= " + proximity ;
//
//
//            if(isFirst)
//            {
//                queryNormal = queryNormal + " WHERE " + queryPartProximity;
//
//                // reset the flag
//                isFirst = false;
//
//            }else
//            {
//                queryNormal = queryNormal + " AND " + queryPartProximity;
//            }
//
//        }



//        if(isOfficial!=null)
//        {
//            String queryPartOfficial = "";
//
//            queryPartOfficial = queryPartOfficial + ServiceConfigurationGlobal.TABLE_NAME + "." + ServiceConfigurationGlobal.IS_OFFICIAL_SERVICE_PROVIDER + " = " + isOfficial;
//
////            if(isFirst)
////            {
////                queryNormal = queryNormal + " WHERE " + queryPartOfficial;
////
////                // reset the flag
////                isFirst = false;
////
////            }else
////            {
////                queryNormal = queryNormal + " AND " + queryPartOfficial;
////            }
//
//            queryNormal = queryNormal + " AND " + queryPartOfficial;
//        }
//
//
//
//        if(isVerified!=null)
//        {
//            String queryPartVerified = "";
//
//            queryPartVerified = queryPartVerified + ServiceConfigurationGlobal.TABLE_NAME + "." + ServiceConfigurationGlobal.IS_VERIFIED + " = " + isVerified;
//
////            if(isFirst)
////            {
////                queryNormal = queryNormal + " WHERE " + queryPartVerified;
////
////                // reset the flag
////                isFirst = false;
////
////            }else
////            {
////                queryNormal = queryNormal + " AND " + queryPartVerified;
////            }
//
//            queryNormal = queryNormal + " AND " + queryPartVerified;
//        }
//
//
//
//        if(serviceType!=null)
//        {
//            String queryPartServiceType = "";
//
//            queryPartServiceType = queryPartServiceType + ServiceConfigurationGlobal.TABLE_NAME + "." + ServiceConfigurationGlobal.SERVICE_TYPE + " = " + serviceType;
//
////            if(isFirst)
////            {
////                queryNormal = queryNormal + " WHERE " + queryPartServiceType;
////
////                // reset the flag
////                isFirst = false;
////
////            }else
////            {
////                queryNormal = queryNormal + " AND " + queryPartServiceType;
////            }
//
//
//            queryNormal = queryNormal + " AND " + queryPartServiceType;
//        }



        if(searchString !=null)
        {
            String queryPartSearch = "(" + ServiceConfigurationGlobal.TABLE_NAME + "." + ServiceConfigurationGlobal.SERVICE_NAME +" ilike '%" + searchString + "%'"
                    + " or " + ServiceConfigurationGlobal.TABLE_NAME + "." + ServiceConfigurationGlobal.ADDRESS + " ilike '%" + searchString + "%'"
                    + " or " + ServiceConfigurationGlobal.TABLE_NAME + "." + ServiceConfigurationGlobal.CITY + " ilike '%" + searchString + "%' )";



//            if(isFirst)
//            {
////				queryJoin = queryJoin + " WHERE " + queryPartSearch;
//
//                queryNormal = queryNormal + " WHERE " + queryPartSearch;
//
//                isFirst = false;
//            }
//            else
//            {
//                queryNormal = queryNormal + " AND " + queryPartSearch;
//            }


            queryNormal = queryNormal + " AND " + queryPartSearch;
        }




        if(serviceURL !=null)
        {
            String serviceURLquery = "(" + ServiceConfigurationGlobal.TABLE_NAME + "." + ServiceConfigurationGlobal.SERVICE_URL + " = " + serviceURL + " )";



//            if(isFirst)
//            {
//                queryNormal = queryNormal + " WHERE " + serviceURLquery;
//
//                isFirst = false;
//            }
//            else
//            {
//                queryNormal = queryNormal + " AND " + serviceURLquery;
//            }


            queryNormal = queryNormal + " AND " + serviceURLquery;
        }






        String queryGroupBy = "";

        queryGroupBy = queryGroupBy + " group by "
                + ServiceConfigurationGlobal.TABLE_NAME + "." + ServiceConfigurationGlobal.SERVICE_CONFIGURATION_ID ;


        queryNormal = queryNormal + queryGroupBy;




        // Applying Filters



        if(sortBy!=null)
        {
            if(!sortBy.equals(""))
            {
                String queryPartSortBy = " ORDER BY " + sortBy;

                queryNormal = queryNormal + queryPartSortBy;
            }
        }



        if(limit !=null)
        {

            String queryPartLimitOffset = "";

            if(offset!=null)
            {
                queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

            }else
            {
                queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
            }


            queryNormal = queryNormal + queryPartLimitOffset;
        }



        query = queryNormal;





        ServiceConfigurationEndPoint endPoint = new ServiceConfigurationEndPoint();
        endPoint.setItemCount(0);

        ArrayList<ServiceConfigurationGlobal> shopList = new ArrayList<ServiceConfigurationGlobal>();


        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {

            connection = dataSource.getConnection();
            statement = connection.createStatement();

            rs = statement.executeQuery(query);

            while(rs.next())
            {

                ServiceConfigurationGlobal sc = new ServiceConfigurationGlobal();

                sc.setRt_distance(rs.getDouble("distance"));
                sc.setRt_rating_avg(rs.getFloat("avg_rating"));
                sc.setRt_rating_count(rs.getFloat("rating_count"));

                sc.setServiceID(rs.getInt(ServiceConfigurationGlobal.SERVICE_CONFIGURATION_ID));
                sc.setLogoImagePath(rs.getString(ServiceConfigurationGlobal.LOGO_IMAGE_PATH));
                sc.setBackdropImagePath(rs.getString(ServiceConfigurationGlobal.BACKDROP_IMAGE_PATH));
                sc.setServiceName(rs.getString(ServiceConfigurationGlobal.SERVICE_NAME));
                sc.setHelplineNumber(rs.getString(ServiceConfigurationGlobal.HELPLINE_NUMBER));

                sc.setDescriptionShort(rs.getString(ServiceConfigurationGlobal.DESCRIPTION_SHORT));
                sc.setDescriptionLong(rs.getString(ServiceConfigurationGlobal.DESCRIPTION_LONG));

                sc.setAddress(rs.getString(ServiceConfigurationGlobal.ADDRESS));
                sc.setCity(rs.getString(ServiceConfigurationGlobal.CITY));
                sc.setPincode(rs.getLong(ServiceConfigurationGlobal.PINCODE));
                sc.setLandmark(rs.getString(ServiceConfigurationGlobal.LANDMARK));
                sc.setState(rs.getString(ServiceConfigurationGlobal.STATE));
                sc.setCountry(rs.getString(ServiceConfigurationGlobal.COUNTRY));

                sc.setISOCountryCode(rs.getString(ServiceConfigurationGlobal.ISO_COUNTRY_CODE));
                sc.setISOLanguageCode(rs.getString(ServiceConfigurationGlobal.ISO_LANGUAGE_CODE));
                sc.setISOCurrencyCode(rs.getString(ServiceConfigurationGlobal.ISO_CURRENCY_CODE));

                sc.setServiceType(rs.getInt(ServiceConfigurationGlobal.SERVICE_TYPE));
                sc.setServiceLevel(rs.getInt(ServiceConfigurationGlobal.SERVICE_LEVEL));

                sc.setLatCenter(rs.getDouble(ServiceConfigurationGlobal.LAT_CENTER));
                sc.setLonCenter(rs.getDouble(ServiceConfigurationGlobal.LON_CENTER));

                sc.setServiceRange(rs.getInt(ServiceConfigurationGlobal.SERVICE_RANGE));

                sc.setOfficial(rs.getBoolean(ServiceConfigurationGlobal.IS_OFFICIAL_SERVICE_PROVIDER));
                sc.setVerified(rs.getBoolean(ServiceConfigurationGlobal.IS_VERIFIED));
                sc.setServiceURL(rs.getString(ServiceConfigurationGlobal.SERVICE_URL));

                sc.setCreated(rs.getTimestamp(ServiceConfigurationGlobal.CREATED));
                sc.setUpdated(rs.getTimestamp(ServiceConfigurationGlobal.UPDATED));

                endPoint.setItemCount(rs.getInt("full_count"));
                shopList.add(sc);
            }

//            System.out.println("Total Items Fetched " + shopList.size());


            endPoint.setResults(shopList);


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        finally

        {

            try {
                if(rs!=null)
                {rs.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(statement!=null)
                {statement.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {

                if(connection!=null)
                {connection.close();}
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return endPoint;
    }

}
