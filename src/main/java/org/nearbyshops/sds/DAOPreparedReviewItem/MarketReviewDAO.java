package org.nearbyshops.sds.DAOPreparedReviewItem;

import com.zaxxer.hikari.HikariDataSource;
import org.nearbyshops.sds.Globals.Globals;
import org.nearbyshops.sds.ModelReviewEndpoint.ItemReviewEndPoint;
import org.nearbyshops.sds.ModelReviewMarket.MarketReview;
import org.nearbyshops.sds.ModelReviewMarket.MarketReviewStatRow;
import org.nearbyshops.sds.ModelReviewMarket.MarketReviewThanks;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sumeet on 8/8/16.
 */
public class MarketReviewDAO {


        private HikariDataSource dataSource = Globals.getDataSource();


        @Override
        protected void finalize() throws Throwable {
            // TODO Auto-generated method stub
            super.finalize();
        }


        public int saveItemReview(MarketReview itemReview)
        {


            Connection connection = null;
            PreparedStatement statement = null;
            int idOfInsertedRow = 0;



            String insertStatement = "INSERT INTO "
                    + MarketReview.TABLE_NAME
                    + "("
                    + MarketReview.ITEM_ID + ","
                    + MarketReview.END_USER_ID + ","
                    + MarketReview.RATING + ","

                    + MarketReview.REVIEW_TEXT + ","
                    + MarketReview.REVIEW_TITLE + ""

                    + ") VALUES(?,?,?,?,?)";

            try {

                connection = dataSource.getConnection();

                statement = connection.prepareStatement(insertStatement,PreparedStatement.RETURN_GENERATED_KEYS);

                statement.setObject(1,itemReview.getItemID());
                statement.setObject(2,itemReview.getEndUserID());
                statement.setObject(3,itemReview.getRating());

                statement.setString(4,itemReview.getReviewText());
                statement.setString(5,itemReview.getReviewTitle());

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






        public int updateItemReview(MarketReview itemReview)
        {


            String updateStatement = "UPDATE " + MarketReview.TABLE_NAME

                    + " SET "
                    + MarketReview.ITEM_ID + " = ?,"
                    + MarketReview.END_USER_ID + " = ?,"
                    + MarketReview.RATING + " = ?,"

                    + MarketReview.REVIEW_TEXT + " = ?,"
                    + MarketReview.REVIEW_TITLE + " = ?"


                    + " WHERE "
                    + MarketReview.ITEM_REVIEW_ID + " = ?";


            Connection connection = null;
            PreparedStatement statement = null;

            int rowCountUpdated = 0;

            try {


                connection = dataSource.getConnection();
                statement = connection.prepareStatement(updateStatement);

                statement.setInt(1,itemReview.getItemID());
                statement.setInt(2,itemReview.getEndUserID());
                statement.setInt(3,itemReview.getRating());

                statement.setString(4,itemReview.getReviewText());
                statement.setString(5,itemReview.getReviewTitle());

                statement.setInt(6,itemReview.getItemReviewID());


                rowCountUpdated = statement.executeUpdate();
                System.out.println("Total rows updated: " + rowCountUpdated);


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

            return rowCountUpdated;
        }



        public int deleteItemReview(int itemReviewID)
        {

            String deleteStatement = "DELETE FROM " + MarketReview.TABLE_NAME
                    + " WHERE " + MarketReview.ITEM_REVIEW_ID + " = ?";

            Connection connection= null;
            PreparedStatement statement = null;
            int rowCountDeleted = 0;
            try {


                connection = dataSource.getConnection();
                statement = connection.prepareStatement(deleteStatement);

                statement.setInt(1,itemReviewID);

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





        public List<MarketReview> getItemReviews(
                Integer itemID,
                Integer endUserID,
                String sortBy,
                Integer limit, Integer offset
        ) {



            boolean isFirst = true;

            String query = "";

            String queryNormal = "SELECT * FROM " + MarketReview.TABLE_NAME;


            String queryJoin = "SELECT "

                    + MarketReview.TABLE_NAME + "." + MarketReview.ITEM_REVIEW_ID + ","
                    + MarketReview.TABLE_NAME + "." + MarketReview.ITEM_ID + ","
                    + MarketReview.TABLE_NAME + "." + MarketReview.END_USER_ID + ","
                    + MarketReview.TABLE_NAME + "." + MarketReview.RATING + ","
                    + MarketReview.TABLE_NAME + "." + MarketReview.REVIEW_TEXT + ","
                    + MarketReview.TABLE_NAME + "." + MarketReview.REVIEW_DATE + ","
                    + MarketReview.TABLE_NAME + "." + MarketReview.REVIEW_TITLE + ","
                    + " count(" + MarketReviewThanks.TABLE_NAME + "." + MarketReviewThanks.ITEM_REVIEW_ID + ") as thanks_count "

                    + " FROM " + MarketReview.TABLE_NAME + " LEFT OUTER JOIN " + MarketReviewThanks.TABLE_NAME
                    + " ON (" + MarketReview.TABLE_NAME + "." + MarketReview.ITEM_REVIEW_ID
                    + " = " + MarketReviewThanks.TABLE_NAME + "." + MarketReviewThanks.ITEM_REVIEW_ID + ") ";


            if(itemID != null)
            {
                queryJoin = queryJoin + " WHERE "
                        + MarketReview.TABLE_NAME
                        + "."
                        + MarketReview.ITEM_ID + " = " + itemID;


                queryNormal = queryNormal + " WHERE "
                        + MarketReview.TABLE_NAME
                        + "."
                        + MarketReview.ITEM_ID + " = " + itemID;

                isFirst = false;
            }


            if(endUserID != null)
            {

                String queryPartMember =
                        MarketReview.TABLE_NAME
                                + "."
                        + MarketReview.END_USER_ID + " = " + endUserID;

                if(isFirst)
                {
                    queryJoin = queryJoin + " WHERE " + queryPartMember;
                    queryNormal = queryNormal + " WHERE " + queryPartMember;

                }else
                {
                    queryJoin = queryJoin + " AND " + queryPartMember;
                    queryNormal = queryNormal + " AND " + queryPartMember;
                }


                isFirst = false;

            }


            queryJoin = queryJoin

                    + " group by "

                    + MarketReview.TABLE_NAME + "." + MarketReview.ITEM_REVIEW_ID + ","
                    + MarketReview.TABLE_NAME + "." + MarketReview.ITEM_ID + ","
                    + MarketReview.TABLE_NAME + "." + MarketReview.END_USER_ID + ","
                    + MarketReview.TABLE_NAME + "." + MarketReview.RATING + ","
                    + MarketReview.TABLE_NAME + "." + MarketReview.REVIEW_TEXT + ","
                    + MarketReview.TABLE_NAME + "." + MarketReview.REVIEW_DATE + ","
                    + MarketReview.TABLE_NAME + "." + MarketReview.REVIEW_TITLE ;




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



            ArrayList<MarketReview> itemReviewsList = new ArrayList<>();


            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;

            try {

                connection = dataSource.getConnection();
                statement = connection.createStatement();
                rs = statement.executeQuery(query);

                while(rs.next())
                {
                    MarketReview itemReview = new MarketReview();

                    itemReview.setItemReviewID(rs.getInt(MarketReview.ITEM_REVIEW_ID));
                    itemReview.setItemID(rs.getInt(MarketReview.ITEM_ID));
                    itemReview.setEndUserID(rs.getInt(MarketReview.END_USER_ID));
                    itemReview.setRating(rs.getInt(MarketReview.RATING));
                    itemReview.setReviewText(rs.getString(MarketReview.REVIEW_TEXT));

                    itemReview.setReviewTitle(rs.getString(MarketReview.REVIEW_TITLE));
                    itemReview.setReviewDate(rs.getTimestamp(MarketReview.REVIEW_DATE));

                    itemReview.setRt_thanks_count(rs.getInt("thanks_count"));
                    itemReviewsList.add(itemReview);
                }



                System.out.println("books By CategoryID " + itemReviewsList.size());

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

            return itemReviewsList;
        }



        public ItemReviewEndPoint getEndPointMetadata(
                Integer itemID,
                Integer endUserID)
        {


            boolean isFirst = true;

            String query = "";

            String queryNormal = "SELECT "
                    + "count( DISTINCT " + MarketReview.ITEM_REVIEW_ID + ") as item_count" + ""
                    + " FROM " + MarketReview.TABLE_NAME;




            if(itemID != null)
            {

                queryNormal = queryNormal + " WHERE "
                        + MarketReview.TABLE_NAME
                        + "."
                        + MarketReview.ITEM_ID + " = " + itemID;

                isFirst = false;
            }


            if(endUserID != null)
            {

                String queryPartMember =
                        MarketReview.TABLE_NAME
                                + "."
                                + MarketReview.END_USER_ID + " = " + endUserID;

                if(isFirst)
                {
                    queryNormal = queryNormal + " WHERE " + queryPartMember;

                }else
                {
                    queryNormal = queryNormal + " AND " + queryPartMember;
                }


                isFirst = false;

            }




/*
            if(bookID != null)
            {
*//*
                queryJoin = queryJoin + " AND "
                        + ItemContract.TABLE_NAME
                        + "."
                        + ItemContract.ITEM_CATEGORY_ID + " = " + itemCategoryID;

*//*


                //" WHERE ITEM_CATEGORY_ID = " + itemCategoryID

                queryNormal = queryNormal + " WHERE "
                        + BookReview.TABLE_NAME
                        + "."
                        + BookReview.BOOK_ID + " = " + bookID;
            }



            if(memberID != null)
            {
*//*
                queryJoin = queryJoin + " AND "
                        + ItemContract.TABLE_NAME
                        + "."
                        + ItemContract.ITEM_CATEGORY_ID + " = " + itemCategoryID;

*//*


                //" WHERE ITEM_CATEGORY_ID = " + itemCategoryID

                queryNormal = queryNormal + " WHERE "
                        + BookReview.TABLE_NAME
                        + "."
                        + BookReview.MEMBER_ID + " = " + memberID;
            }*/



            // Applying filters





		/*
		Applying filters Ends
		 */



            query = queryNormal;


            ItemReviewEndPoint endPoint = new ItemReviewEndPoint();


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





        public MarketReview getItemReview(int itemReviewID)
        {

            String query = "SELECT * FROM " +  MarketReview.TABLE_NAME
                        + " WHERE " + MarketReview.ITEM_REVIEW_ID + " = " + itemReviewID;


            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;


            //ItemCategory itemCategory = new ItemCategory();
            MarketReview itemReview = null;

            try {

                connection = dataSource.getConnection();
                statement = connection.createStatement();
                rs = statement.executeQuery(query);



                while(rs.next())
                {
                    itemReview = new MarketReview();

                    itemReview.setItemReviewID(rs.getInt(MarketReview.ITEM_REVIEW_ID));
                    itemReview.setItemID(rs.getInt(MarketReview.ITEM_ID));
                    itemReview.setEndUserID(rs.getInt(MarketReview.END_USER_ID));
                    itemReview.setRating(rs.getInt(MarketReview.RATING));
                    itemReview.setReviewText(rs.getString(MarketReview.REVIEW_TEXT));

                    itemReview.setReviewTitle(rs.getString(MarketReview.REVIEW_TITLE));
                    itemReview.setReviewDate(rs.getTimestamp(MarketReview.REVIEW_DATE));

                    System.out.println("Get BookReview by DELIVERY_GUY_SELF_ID : " + itemReview.getItemID());
                }

                //System.out.println("Total itemCategories queried " + itemCategoryList.size());



            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally

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

            return itemReview;
        }




        public List<MarketReviewStatRow> getStats(Integer itemID)
        {
            //select rating, count(book_review_id) as reviews_count from book_review group by rating

            String query = "SELECT " + MarketReview.RATING + ", count(" + MarketReview.ITEM_REVIEW_ID + ") as reviews_count "
                        + " FROM " +  MarketReview.TABLE_NAME;


            if(itemID!=null)
            {
                query = query + " WHERE " + MarketReview.ITEM_ID + " = " + itemID;
            }

            query = query + " GROUP BY " + MarketReview.RATING;

            Connection connection = null;
            Statement statement = null;
            ResultSet rs = null;




//            ShopReviewStats shopReviewStats = new ShopReviewStats();

            ArrayList<MarketReviewStatRow> rowList = new ArrayList<>();

            try {

                connection = dataSource.getConnection();
                statement = connection.createStatement();
                rs = statement.executeQuery(query);



                while(rs.next())
                {

                    MarketReviewStatRow row = new MarketReviewStatRow();

                    row.setRating(rs.getInt(MarketReview.RATING));
                    row.setReviews_count(rs.getInt("reviews_count"));

                    rowList.add(row);

//                    System.out.println("Get BookReview by DELIVERY_GUY_SELF_ID : " + shopReview.getItemID());
                }

                //System.out.println("Total itemCategories queried " + itemCategoryList.size());



            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally

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


            return rowList;
        }


}
