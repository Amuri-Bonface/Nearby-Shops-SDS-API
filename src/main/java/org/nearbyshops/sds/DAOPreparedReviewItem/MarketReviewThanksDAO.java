package org.nearbyshops.sds.DAOPreparedReviewItem;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.sds.Globals.Globals;
import org.nearbyshops.sds.ModelReviewEndpoint.ItemReviewThanksEndpoint;
import org.nearbyshops.sds.ModelReviewMarket.MarketReview;
import org.nearbyshops.sds.ModelReviewMarket.MarketReviewThanks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sumeet on 8/8/16.
 */
public class MarketReviewThanksDAO {

    private HikariDataSource dataSource = Globals.getDataSource();

        @Override
        protected void finalize() throws Throwable {
            // TODO Auto-generated method stub
            super.finalize();
        }


        public int saveItemReviewThanks(MarketReviewThanks itemReviewThanks)
        {


            Connection connection = null;
            PreparedStatement statement = null;
            int idOfInsertedRow = 0;


            String insertStatement = "INSERT INTO "
                    + MarketReviewThanks.TABLE_NAME
                    + "("
                    + MarketReviewThanks.ITEM_REVIEW_ID + ","
                    + MarketReviewThanks.END_USER_ID
                    + ") VALUES(?,?)";

            try {

                connection = dataSource.getConnection();
                statement = connection.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);


                statement.setInt(1,itemReviewThanks.getItemReviewID());
                statement.setInt(2,itemReviewThanks.getEndUserID());

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

                    if(connection!=null)
                    {connection.close();}
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return idOfInsertedRow;

        }



        public int deleteItemReviewThanks(int itemReviewID, int endUserID)
        {

            String deleteStatement = "DELETE FROM " + MarketReviewThanks.TABLE_NAME
                    + " WHERE " + MarketReviewThanks.ITEM_REVIEW_ID + " = ?"
                    + " AND " + MarketReviewThanks.END_USER_ID + " = ?";

            Connection connection= null;
            PreparedStatement statement = null;
            int rowCountDeleted = 0;
            try {


                connection = dataSource.getConnection();
                statement = connection.prepareStatement(deleteStatement);

                statement.setInt(1,itemReviewID);
                statement.setInt(2,endUserID);

                rowCountDeleted = statement.executeUpdate();
                System.out.println("Rows Deleted: " + rowCountDeleted);


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



        

        public List<MarketReviewThanks> getItemReviewThanks(
                Integer itemReviewID,
                Integer itemID,
                Integer endUserID,
                String sortBy,
                Integer limit, Integer offset
        ) {


            boolean isFirst = true;


            String query = "";

            String queryNormal = "SELECT * FROM " + MarketReviewThanks.TABLE_NAME;


            String queryJoin = "SELECT "

                    + MarketReviewThanks.TABLE_NAME + "." + MarketReviewThanks.ITEM_REVIEW_ID + ","
                    + MarketReviewThanks.TABLE_NAME + "." + MarketReviewThanks.END_USER_ID + ""

                    + " FROM "
                    + MarketReviewThanks.TABLE_NAME;


            if(itemID !=null)
            {
                queryJoin = queryJoin + " INNER JOIN " + MarketReview.TABLE_NAME
                            + " ON( " + MarketReviewThanks.TABLE_NAME + "." + MarketReviewThanks.ITEM_REVIEW_ID
                            + " = " + MarketReview.TABLE_NAME + "." + MarketReview.ITEM_REVIEW_ID + ") ";

            }


            if(itemID!=null)
            {

                String queryPartItemID = "";
                queryPartItemID = MarketReview.TABLE_NAME + "." + MarketReview.ITEM_ID + " = " + itemID;

                if(isFirst)
                {
                    queryJoin = queryJoin + " WHERE " + queryPartItemID;
                    isFirst = false;
                }
                else
                {
                    queryJoin = queryJoin + " AND " + queryPartItemID;
                }
            }




            if(itemReviewID != null)
            {

                String queryPartItemReview;

                queryPartItemReview = MarketReviewThanks.TABLE_NAME
                                        + "."
                                        + MarketReviewThanks.ITEM_REVIEW_ID + " = " + itemReviewID;


                if(isFirst)
                {
                    queryJoin = queryJoin + " WHERE " + queryPartItemReview;
                    queryNormal = queryNormal + " WHERE " + queryPartItemReview;

                    isFirst = false;
                }
                else
                {
                    queryJoin = queryJoin + " AND " + queryPartItemReview;
                    queryNormal = queryNormal + " AND " + queryPartItemReview;

                }
            }



            if(endUserID!=null){

                String queryPartEndUserID;

                queryPartEndUserID = MarketReviewThanks.TABLE_NAME
                                    + "."
                                    + MarketReviewThanks.END_USER_ID + " = " + endUserID;

                if(isFirst)
                {
                    queryJoin = queryJoin + " WHERE " + queryPartEndUserID;
                    queryNormal = queryNormal + " WHERE " + queryPartEndUserID;

                    isFirst = false;

                }else
                {
                    queryJoin = queryJoin + " AND " + queryPartEndUserID;
                    queryNormal = queryNormal + " AND " + queryPartEndUserID;
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



            query = queryJoin;

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



            ArrayList<MarketReviewThanks> thanksList = new ArrayList<>();


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
                    MarketReviewThanks reviewThanks = new MarketReviewThanks();

                    reviewThanks.setEndUserID(rs.getInt(MarketReviewThanks.END_USER_ID));
                    reviewThanks.setItemReviewID(rs.getInt(MarketReviewThanks.ITEM_REVIEW_ID));

                    thanksList.add(reviewThanks);
                }



                System.out.println("Item Review Thanks List Size : " + thanksList.size());

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

            return thanksList;
        }




        public ItemReviewThanksEndpoint getEndPointMetadata(
                Integer itemReviewID,
                Integer endUserID)
        {


            boolean isFirst = true;

            String query = "";

            String queryNormal = "SELECT "
                    + "count(*) as item_count" + ""
                    + " FROM " + MarketReviewThanks.TABLE_NAME;




            if(itemReviewID != null)
            {
            /*    queryJoin = queryJoin + " WHERE "
                        + FavouriteBook.TABLE_NAME
                        + "."
                        + FavouriteBook.BOOK_ID + " = " + bookID;



            */

                queryNormal = queryNormal + " WHERE "
                        + MarketReviewThanks.TABLE_NAME
                        + "."
                        + MarketReviewThanks.ITEM_REVIEW_ID + " = " + itemReviewID;

                isFirst = false;
            }



            if(endUserID != null){

                String queryPartEndUserID;

                queryPartEndUserID = MarketReviewThanks.TABLE_NAME
                        + "."
                        + MarketReviewThanks.END_USER_ID + " = " + endUserID;

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


            ItemReviewThanksEndpoint endPoint = new ItemReviewThanksEndpoint();


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




                System.out.println("Item Count : " + endPoint.getItemCount());


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
