package org.nearbyshops.sds.ModelReviewMarket;

import org.nearbyshops.sds.ModelRoles.User;

/**
 * Created by sumeet on 21/10/16.
 */

public class MarketReviewThanks {

    // Table Name
    public static final String TABLE_NAME = "ITEM_REVIEW_THANKS";

    // column Names
    public static final String END_USER_ID = "END_USER_ID"; // foreign Key
    public static final String ITEM_REVIEW_ID = "ITEM_REVIEW_ID"; // foreign Key



    // Create Table Statement
    public static final String createTableItemReviewThanksPostgres = "CREATE TABLE IF NOT EXISTS "
            + MarketReviewThanks.TABLE_NAME + "("

            + " " + MarketReviewThanks.END_USER_ID + " INT,"
            + " " + MarketReviewThanks.ITEM_REVIEW_ID + " INT,"

            + " FOREIGN KEY(" + MarketReviewThanks.END_USER_ID +") REFERENCES " + User.TABLE_NAME + "(" + User.USER_ID + ") ON DELETE CASCADE,"
            + " FOREIGN KEY(" + MarketReviewThanks.ITEM_REVIEW_ID +") REFERENCES " + MarketReview.TABLE_NAME + "(" + MarketReview.ITEM_REVIEW_ID + ") ON DELETE CASCADE,"
            + " PRIMARY KEY (" + MarketReviewThanks.END_USER_ID + ", " + MarketReviewThanks.ITEM_REVIEW_ID + ")"
            + ")";


    // instance Variables

    private Integer endUserID;
    private Integer itemReviewID;


    public Integer getEndUserID() {
        return endUserID;
    }

    public void setEndUserID(Integer endUserID) {
        this.endUserID = endUserID;
    }

    public Integer getItemReviewID() {
        return itemReviewID;
    }

    public void setItemReviewID(Integer itemReviewID) {
        this.itemReviewID = itemReviewID;
    }
}
