package org.nearbyshops.sds.RESTEndpointReviewItem;


import org.nearbyshops.sds.DAOPreparedReviewItem.FavoriteMarketDAO;
import org.nearbyshops.sds.Globals.GlobalConstants;
import org.nearbyshops.sds.Globals.Globals;
import org.nearbyshops.sds.ModelReviewEndpoint.FavouriteItemEndpoint;
import org.nearbyshops.sds.ModelReviewMarket.FavouriteMarket;
import org.nearbyshops.sds.ModelRoles.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by sumeet on 9/8/16.
 */





@Path("/api/v1/FavouriteMarket")
public class FavouriteMarketResource {


    private FavoriteMarketDAO favoriteItemDAOPrepared = Globals.favoriteMarketDAO;


        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        @RolesAllowed({GlobalConstants.ROLE_END_USER})
        public Response saveFavouriteBook(FavouriteMarket item)
        {

            User userProfile = (User) Globals.accountApproved;
            item.setEndUserID(userProfile.getUserID());


            int idOfInsertedRow = favoriteItemDAOPrepared.saveFavouriteItem(item);

            item.setItemID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {

                return Response.status(Response.Status.CREATED)
                        .entity(item)
                        .build();

            }
            else {

                    return Response.status(Response.Status.NOT_MODIFIED)
                            .build();
            }


        }



        @DELETE
        @Produces(MediaType.APPLICATION_JSON)
        @RolesAllowed({GlobalConstants.ROLE_END_USER})
        public Response deleteItem(@QueryParam("ItemID")Integer itemID,
                                   @QueryParam("EndUserID")Integer endUserID)
        {


            User userProfile = (User) Globals.accountApproved;

            int rowCount = favoriteItemDAOPrepared.deleteFavouriteItem(itemID,userProfile.getUserID());


            if(rowCount>=1)
            {

                return Response.status(Response.Status.OK)
                        .build();
            }
            else
            {

                return Response.status(Response.Status.NOT_MODIFIED)
                        .build();
            }
        }







        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getFavouriteItems(
                @QueryParam("ItemID")Integer itemID,
                @QueryParam("EndUserID")Integer endUserID,
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



            System.out.println("ItemID : " + itemID + " | EndUserID : " + endUserID);


            FavouriteItemEndpoint endPoint = favoriteItemDAOPrepared.getEndPointMetadata(itemID,endUserID);

            endPoint.setLimit(set_limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(set_offset);

            List<FavouriteMarket> list = null;


            if(metaonly==null || (!metaonly)) {

                list =
                        favoriteItemDAOPrepared.getFavouriteItem(
                                itemID,endUserID,
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
