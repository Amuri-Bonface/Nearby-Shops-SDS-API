package org.nearbyshops.sds.ModelReviewMarket;


import org.nearbyshops.sds.Model.ServiceConfigurationGlobal;
import org.nearbyshops.sds.ModelRoles.User;

/**
 * Created by sumeet on 8/8/16.
 */
public class FavouriteMarket {


    // Table Name
    public static final String TABLE_NAME = "FAVOURITE_MARKETS";

    // column Names
    public static final String END_USER_ID = "END_USER_ID"; // foreign Key
    public static final String ITEM_ID = "ITEM_ID"; // foreign Key




    // Create Table Statement
    public static final String createTablePostgres = "CREATE TABLE IF NOT EXISTS "
            + FavouriteMarket.TABLE_NAME + "("

            + " " + FavouriteMarket.END_USER_ID + " INT,"
            + " " + FavouriteMarket.ITEM_ID + " INT,"

            + " FOREIGN KEY(" + FavouriteMarket.END_USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + "),"
            + " FOREIGN KEY(" + FavouriteMarket.ITEM_ID +") REFERENCES " + ServiceConfigurationGlobal.TABLE_NAME + "(" + ServiceConfigurationGlobal.SERVICE_CONFIGURATION_ID + "),"
            + " PRIMARY KEY (" + FavouriteMarket.END_USER_ID + ", " + FavouriteMarket.ITEM_ID + ")"
            + ")";


    // instance Variables

    private int endUserID;
    private int itemID;





    // Getter and Setter

    public int getEndUserID() {
        return endUserID;
    }

    public void setEndUserID(int endUserID) {
        this.endUserID = endUserID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
}
