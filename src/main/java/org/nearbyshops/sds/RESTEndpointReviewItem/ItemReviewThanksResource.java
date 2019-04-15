package org.nearbyshops.sds.RESTEndpointReviewItem;

import org.nearbyshops.sds.DAOPreparedReviewItem.MarketReviewThanksDAO;
import org.nearbyshops.sds.Globals.Globals;
import org.nearbyshops.sds.ModelReviewEndpoint.ItemReviewThanksEndpoint;
import org.nearbyshops.sds.ModelReviewMarket.MarketReviewThanks;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sumeet on 9/8/16.
 */


@Path("/api/v1/ItemReviewThanks")
public class ItemReviewThanksResource {


    private MarketReviewThanksDAO thanksDAOPrepared = Globals.marketReviewThanksDAO;



        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response saveItemReviewThanks(MarketReviewThanks itemReviewThanks)
        {
            int idOfInsertedRow = thanksDAOPrepared.saveItemReviewThanks(itemReviewThanks);

//            shopReviewThanks.setItemID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {

                return Response.status(Response.Status.CREATED)
                        .entity(itemReviewThanks)
                        .build();

            }else {

                return Response.status(Response.Status.NOT_MODIFIED)
                        .build();
            }


        }



        @DELETE
        @Produces(MediaType.APPLICATION_JSON)
        public Response deleteItem(@QueryParam("ItemReviewID")Integer itemReviewID,
                                   @QueryParam("EndUserID")Integer endUserID)
        {

            int rowCount = thanksDAOPrepared.deleteItemReviewThanks(itemReviewID,endUserID);


            if(rowCount>=1)
            {

                return Response.status(Response.Status.OK)
                        .build();
            }

            if(rowCount == 0)
            {

                return Response.status(Response.Status.NOT_MODIFIED)
                        .build();
            }

            return null;
        }



        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getItemReviewThanks(
                @QueryParam("ItemReviewID")Integer itemReviewID,
                @QueryParam("EndUserID")Integer endUserID,
                @QueryParam("ItemID")Integer itemID,
                @QueryParam("SortBy") String sortBy,
                @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
                @QueryParam("metadata_only")Boolean metaonly)
        {

            int set_limit = 30;
            int set_offset = 0;
            final int max_limit = 100;

            if(limit!= null)
            {

                if (limit >= max_limit) {

                    set_limit = max_limit;
                }
                else
                {

                    set_limit = limit;
                }

            }

            if(offset!=null)
            {
                set_offset = offset;
            }

            ItemReviewThanksEndpoint endPoint = thanksDAOPrepared.getEndPointMetadata(itemReviewID,endUserID);

            endPoint.setLimit(set_limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(set_offset);

            List<MarketReviewThanks> list = null;


            if(metaonly==null || (!metaonly)) {

                list =
                        thanksDAOPrepared.getItemReviewThanks(
                                itemReviewID,itemID,endUserID,
                                sortBy,set_limit,set_offset
                        );

                endPoint.setResults(list);
            }


            //Marker
            return Response.status(Response.Status.OK)
                    .entity(endPoint)
                    .build();

        }

}
