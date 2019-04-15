package org.nearbyshops.sds.DAOPreparedReviewItem;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.sds.Globals.Globals;
import org.nearbyshops.sds.ModelReviewEndpoint.FavouriteItemEndpoint;
import org.nearbyshops.sds.ModelReviewMarket.FavouriteMarket;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sumeet on 8/8/16.
 */
public class FavoriteMarketDAO {


    private HikariDataSource dataSource = Globals.getDataSource();

        @Override
        protected void finalize() throws Throwable {
            // TODO Auto-generated method stub
            super.finalize();
        }


        public int saveFavouriteItem(FavouriteMarket favouriteItem)
        {


            Connection conn = null;
            PreparedStatement statement = null;
            int idOfInsertedRow = 0;


            String insertStatement = "INSERT INTO "
                    + FavouriteMarket.TABLE_NAME
                    + "("
                    + FavouriteMarket.ITEM_ID + ","
                    + FavouriteMarket.END_USER_ID
                    + ") VALUES(?,?)";

            try {

                conn = dataSource.getConnection();
                statement = conn.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setInt(1,favouriteItem.getItemID());
                statement.setInt(2,favouriteItem.getEndUserID());

                idOfInsertedRow = statement.executeUpdate();

                ResultSet rs = statement.getGeneratedKeys();

                if(rs.next())
                {
                    idOfInsertedRow = rs.getInt(1);
                }


            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            finally
            {

                try {

                    if(statement!=null)
                    {statement.close();}

                }
                catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    if(conn!=null)
                    {conn.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return idOfInsertedRow;

        }



        public int deleteFavouriteItem(int itemID, int endUserID)
        {

            String deleteStatement = "DELETE FROM " + FavouriteMarket.TABLE_NAME
                    + " WHERE " + FavouriteMarket.ITEM_ID + " = ?"
                    + " AND " + FavouriteMarket.END_USER_ID + " = ?";

            Connection connection= null;
            PreparedStatement statement = null;
            int rowCountDeleted = 0;
            try {


                connection = dataSource.getConnection();
                statement = connection.prepareStatement(deleteStatement);

                statement.setInt(1,itemID);
                statement.setInt(2,endUserID);

                rowCountDeleted = statement.executeUpdate();
                System.out.println("Rows Deleted Favourite Item: " + rowCountDeleted);


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





        public List<FavouriteMarket> getFavouriteItem(
                Integer itemID,
                Integer endUserID,
                String sortBy,
                Integer limit, Integer offset
        ) {


            boolean isFirst = true;


            String query = "";

            String queryNormal = "SELECT * FROM " + FavouriteMarket.TABLE_NAME;


            String queryJoin = "SELECT "

                    + FavouriteMarket.TABLE_NAME + "." + FavouriteMarket.ITEM_ID + ","
                    + FavouriteMarket.TABLE_NAME + "." + FavouriteMarket.END_USER_ID + ""

                    + " FROM "
                    + FavouriteMarket.TABLE_NAME;


            if(itemID != null)
            {
                queryJoin = queryJoin + " WHERE "
                        + FavouriteMarket.TABLE_NAME
                        + "."
                        + FavouriteMarket.ITEM_ID + " = " + itemID;



                //" WHERE ITEM_CATEGORY_ID = " + itemCategoryID

                queryNormal = queryNormal + " WHERE "
                        + FavouriteMarket.TABLE_NAME
                        + "."
                        + FavouriteMarket.ITEM_ID + " = " + itemID;

                isFirst = false;
            }



            if(endUserID!=null){

                String queryPartMemberID;

                queryPartMemberID = FavouriteMarket.TABLE_NAME
                                    + "."
                                    + FavouriteMarket.END_USER_ID + " = " + endUserID;

                if(isFirst)
                {
                    queryJoin = queryJoin + " WHERE " + queryPartMemberID;
                    queryNormal = queryNormal + " WHERE " + queryPartMemberID;

                }else
                {
                    queryJoin = queryJoin + " AND " + queryPartMemberID;
                    queryNormal = queryNormal + " AND " + queryPartMemberID;
                }

            }





            // Applying filters



            if(sortBy!=null)
            {
                if(!sortBy.equals(""))
                {
                    String queryPartSortBy = " ORDER BY " + sortBy;

                    queryNormal = queryNormal + queryPartSortBy;
                    queryJoin = queryJoin + queryPartSortBy;
                }
            }



            if(limit != null)
            {

                String queryPartLimitOffset = "";

                if(offset>0)
                {
                    queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + offset;

                }else
                {
                    queryPartLimitOffset = " LIMIT " + limit + " " + " OFFSET " + 0;
                }


                queryNormal = queryNormal + queryPartLimitOffset;
                queryJoin = queryJoin + queryPartLimitOffset;
            }






		/*
		Applying filters Ends
		 */



            query = queryNormal;

            /*

            if(bookCategoryID!=null)
            {
                query = queryJoin;
                isJoinQuery = true;

            }else
            {
                query = queryNormal;
            }

            */



            ArrayList<FavouriteMarket> itemsList = new ArrayList<>();


            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;

            try {


                connection = dataSource.getConnection();
                statement = connection.createStatement();


//                System.out.println("Favourite Books " + query);

                rs = statement.executeQuery(query);

                while(rs.next())
                {
                    FavouriteMarket item = new FavouriteMarket();

                    item.setEndUserID(rs.getInt(FavouriteMarket.END_USER_ID));
                    item.setItemID(rs.getInt(FavouriteMarket.ITEM_ID));

                    itemsList.add(item);
                }



                System.out.println("Favourite Items List Size " + itemsList.size());

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

            return itemsList;
        }




        public FavouriteItemEndpoint getEndPointMetadata(
                Integer itemID,
                Integer endUserID)
        {


            boolean isFirst = true;


            String query = "";

            String queryNormal = "SELECT "
                    + "count(*) as item_count" + ""
                    + " FROM " + FavouriteMarket.TABLE_NAME;




            if(itemID != null)
            {
            /*    queryJoin = queryJoin + " WHERE "
                        + FavouriteBook.TABLE_NAME
                        + "."
                        + FavouriteBook.BOOK_ID + " = " + bookID;



            */

                queryNormal = queryNormal + " WHERE "
                        + FavouriteMarket.TABLE_NAME
                        + "."
                        + FavouriteMarket.ITEM_ID + " = " + itemID;

                isFirst = false;
            }



            if(endUserID!=null){

                String queryPartEndUserID;

                queryPartEndUserID = FavouriteMarket.TABLE_NAME
                        + "."
                        + FavouriteMarket.END_USER_ID + " = " + endUserID;

                if(isFirst)
                {
//                    queryJoin = queryJoin + " WHERE " + queryPartMemberID;
                    queryNormal = queryNormal + " WHERE " + queryPartEndUserID;

                }else
                {
//                    queryJoin = queryJoin + " AND " + queryPartMemberID;
                    queryNormal = queryNormal + " AND " + queryPartEndUserID;
                }

            }




            // Applying filters





		/*
		Applying filters Ends
		 */



            query = queryNormal;


            FavouriteItemEndpoint endPoint = new FavouriteItemEndpoint();


            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;

            try {

                connection = dataSource.getConnection();
                statement = connection.createStatement();

                rs = statement.executeQuery(query);

                while(rs.next())
                {
                    endPoint.setItemCount(rs.getInt("item_count"));

                }




                System.out.println("Item Count Favourite Item : " + endPoint.getItemCount());


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
