package org.nearbyshops.sds.RESTEndpointReviewItem;

import org.nearbyshops.sds.DAOPreparedReviewItem.MarketReviewDAO;
import org.nearbyshops.sds.DAORoles.DAOUserNew;
import org.nearbyshops.sds.Globals.Globals;
import org.nearbyshops.sds.ModelReviewEndpoint.ItemReviewEndPoint;
import org.nearbyshops.sds.ModelReviewMarket.MarketReview;
import org.nearbyshops.sds.ModelReviewMarket.MarketReviewStatRow;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by sumeet on 9/8/16.
 */

@Path("/api/v1/MarketReview")
public class MarketReviewRESTEndpoint {


    private MarketReviewDAO itemReviewDAOPrepared = Globals.marketReviewDAO;
    private DAOUserNew endUserDAOPrepared = Globals.daoUserNew;


//    BookReviewDAO bookReviewDAO;

    public MarketReviewRESTEndpoint() {

    }

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response saveItemReview(MarketReview itemReview)
        {
//            int idOfInsertedRow = Globals.bookDAO.saveBook(book);

            int idOfInsertedRow = itemReviewDAOPrepared.saveItemReview(itemReview);

            itemReview.setItemReviewID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {

                return Response.status(Response.Status.CREATED)
                        .entity(itemReview)
                        .build();


            }
            else {

                return Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();
            }


        }


        @PUT
        @Path("/{ItemReviewID}")
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateItem(MarketReview itemReview, @PathParam("ItemReviewID")int itemReviewID)
        {

            itemReview.setItemReviewID(itemReviewID);

//            int rowCount = Globals.bookDAO.updateBook(book);

            int rowCount = itemReviewDAOPrepared.updateItemReview(itemReview);


            if(rowCount >= 1)
            {

                return Response.status(Response.Status.OK)
                        .entity(null)
                        .build();

            }
            else
            {

                return Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();
            }
        }



        @PUT
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateReviewsBulk(List<MarketReview> itemReviewsList)
        {
            int rowCountSum = 0;


            for(MarketReview item : itemReviewsList)
            {
                rowCountSum = rowCountSum + itemReviewDAOPrepared.updateItemReview(item);
            }

            if(rowCountSum ==  itemReviewsList.size())
            {

                return Response.status(Response.Status.OK)
                        .build();
            }
            else if( rowCountSum < itemReviewsList.size() && rowCountSum > 0)
            {

                return Response.status(Response.Status.PARTIAL_CONTENT)
                        .build();
            }
            else if(rowCountSum == 0 ) {

                return Response.status(Response.Status.NOT_MODIFIED)
                        .build();
            }

            return null;
        }


        @DELETE
        @Path("/{ItemReviewID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response deleteItem(@PathParam("ItemReviewID")int itemReviewID)
        {

            int rowCount = itemReviewDAOPrepared.deleteItemReview(itemReviewID);

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
        public Response getItemReviews(
                @QueryParam("ItemID")Integer itemID,
                @QueryParam("EndUserID")Integer endUserID,
                @QueryParam("GetEndUser")Boolean getEndUser,
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

            ItemReviewEndPoint endPoint = itemReviewDAOPrepared.getEndPointMetadata(itemID,endUserID);

            endPoint.setLimit(set_limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(set_offset);

            List<MarketReview> list = null;


            if(metaonly==null || (!metaonly)) {

                list =
                        itemReviewDAOPrepared.getItemReviews(
                                itemID,endUserID,
                                sortBy,set_limit,set_offset
                        );


                if(getEndUser!=null && getEndUser)
                {
                    for(MarketReview itemReview: list)
                    {
                        itemReview.setRt_end_user_profile(endUserDAOPrepared.getProfile(itemReview.getEndUserID()));
                    }
                }

                endPoint.setResults(list);
            }


            //Marker

            return Response.status(Response.Status.OK)
                    .entity(endPoint)
                    .build();

        }





        @GET
        @Path("/{ItemReviewID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getItem(@PathParam("ItemReviewID")int itemReviewID)
        {
            System.out.println("ItemReviewID=" + itemReviewID);


            //marker
            MarketReview item = itemReviewDAOPrepared.getItemReview(itemReviewID);

            if(item!= null)
            {

                return Response.status(Response.Status.OK)
                        .entity(item)
                        .build();
            }
            else
            {
                return Response.status(Response.Status.NO_CONTENT)
                        .build();

            }
        }



    @GET
    @Path("/Stats/{ItemID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStats(@PathParam("ItemID")int itemID)
    {

        List<MarketReviewStatRow> rowList = itemReviewDAOPrepared.getStats(itemID);


        if(rowList.size()>0)
        {

            return Response.status(Response.Status.OK)
                    .entity(rowList)
                    .build();

        } else
        {

            return Response.status(Response.Status.NO_CONTENT)
                    .build();

        }
    }


}
