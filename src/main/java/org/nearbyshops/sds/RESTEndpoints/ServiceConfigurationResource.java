package org.nearbyshops.sds.RESTEndpoints;

import com.google.gson.Gson;
import net.coobird.thumbnailator.Thumbnails;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.nearbyshops.sds.DAOPrepared.ServiceConfigurationDAO;
import org.nearbyshops.sds.Globals.APIErrors;
import org.nearbyshops.sds.Globals.ErrorNBSAPI;
import org.nearbyshops.sds.Globals.GlobalConstants;
import org.nearbyshops.sds.Globals.Globals;
import org.nearbyshops.sds.Model.Endpoints.ServiceConfigurationEndPoint;
import org.nearbyshops.sds.Model.Image;
import org.nearbyshops.sds.Model.ServiceConfigurationGlobal;
import org.nearbyshops.sds.Model.ServiceConfigurationLocal;
import org.nearbyshops.sds.ModelRoles.User;


import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Path("/api/v1/ServiceConfiguration")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceConfigurationResource {

	private ServiceConfigurationDAO shopDAO = Globals.serviceConfigurationDAO;




	@GET
	@Path("/UpdateService")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateService(@QueryParam("ServiceURL") String serviceURL)
	{
		int idOfInsertedRow = -1;
		boolean isUpdated = false;
		int rowCountUpdated = -1;

		ServiceConfigurationLocal serviceConfigurationLocal = null;

		try {

			serviceConfigurationLocal = fetchServiceConfiguration(serviceURL);

		} catch (Exception e) {
			e.printStackTrace();
		}



		ServiceConfigurationEndPoint endPoint =
				shopDAO.getListQuerySimple(null,null,null,"'" + serviceURL + "'",
						null,null,
						null,null,null,null,null);

		String oldImageID = "";
		String newImageID = "";
		String newImageIDUploaded = "";


		if(endPoint.getItemCount()>=1)
		{
			oldImageID = endPoint.getResults().get(0).getLogoImagePath();
		}

		if (serviceConfigurationLocal != null) {
			newImageID = serviceConfigurationLocal.getLogoImagePath();
		}


		if(endPoint.getItemCount()==1)
		{
			// already exist: Make an Update Call
//			shopDAO.updateServiceByStaff(endPoint.getResults().get(0));

			if(serviceConfigurationLocal !=null) {

				// check image changed

//				if (!newImageID.equals(oldImageID))
//				{

//						!endPoint.getResults().get(0).getLogoImagePath().equals(serviceConfigurationLocal.getLogoImagePath())

					// image has Changed
					newImageIDUploaded = saveNewImage(serviceURL, newImageID);
					serviceConfigurationLocal.setLogoImagePath(newImageIDUploaded);

					rowCountUpdated = shopDAO.updateShop(Globals.localToGlobal(serviceConfigurationLocal, serviceURL));
					isUpdated = true;


					if (rowCountUpdated >= 1)
					{
						// update Successful
						deleteImageFileInternal(oldImageID);
					}
					else if(rowCountUpdated<=0)
					{
						// update Failed
						deleteImageFileInternal(newImageIDUploaded);
					}

					// 1. Upload new Image
					// 2. Set new Image
					// 3. update Configuration
					// 4. Delete Old Image

					// 5. Update Configuration
					// 6. If Update is failed Delete New Image

//				}
//				else
//				{
						// image has not changed
//					rowCountUpdated = shopDAO.updateServiceByStaff(Globals.localToGlobal(serviceConfigurationLocal,serviceURL));
//					isUpdated = true;
//				}


			}


		}
		else
		{
			// does not exist: Make an Insert Call

			if(serviceConfigurationLocal !=null)
			{

				newImageIDUploaded = saveNewImage(serviceURL, newImageID);
				serviceConfigurationLocal.setLogoImagePath(newImageIDUploaded);
				idOfInsertedRow = shopDAO.insertShop(Globals.localToGlobal(serviceConfigurationLocal,serviceURL));
			}
		}




//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}



		if(isUpdated)
		{
			if(rowCountUpdated >= 1)
			{
				return Response.status(Status.OK)
						.build();
			}
			if(rowCountUpdated <= 0)
			{
				return Response.status(Status.NOT_MODIFIED)
						.build();
			}
		}
		else
		{
			if(idOfInsertedRow >=1)
			{
				return Response.status(Status.CREATED)
						.location(URI.create("/api/Shop/" + idOfInsertedRow))
						.entity(serviceConfigurationLocal)
						.build();

			}else if(idOfInsertedRow <= 0)
			{
				return Response.status(Status.NOT_MODIFIED)
						.build();
			}
		}

		return null;
	}





	private ServiceConfigurationLocal fetchServiceConfiguration(String serviceURL) throws Exception
	{
		serviceURL = serviceURL + "/api/serviceconfiguration";

		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url(serviceURL)
				.build();

		okhttp3.Response response = null;
		response = client.newCall(request).execute();

		Gson gson = new Gson();

		if (response != null) {
			return gson.fromJson(response.body().string(),ServiceConfigurationLocal.class);
		}
		else
		{
			return null;
		}
	}




	@PUT
	@Path("/UpdateByStaff/{ServiceID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({GlobalConstants.ROLE_ADMIN, GlobalConstants.ROLE_STAFF})
	public Response updateServiceByStaff(ServiceConfigurationGlobal serviceConfigurationGlobal,
										 @PathParam("ServiceID")int serviceID)
	{



		if(Globals.accountApproved instanceof User) {
			// checking permission
			/*Staff staff = (Staff) Globals.accountApproved;
			if (!staff.isCreateUpdateItems())
			{
				// the staff member doesnt have persmission to post Item Category
				throw new ForbiddenException("Not Permitted");
			}*/
		}


		serviceConfigurationGlobal.setServiceID(serviceID);

		int rowCount = shopDAO.updateForStaff(serviceConfigurationGlobal);

		if(rowCount >= 1)
		{

			return Response.status(Status.OK)
					.build();
		}
		if(rowCount <= 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.build();
		}

		return null;
	}




	@DELETE
	@Path("/{ServiceID}")
	@RolesAllowed({GlobalConstants.ROLE_ADMIN,GlobalConstants.ROLE_STAFF})
	public Response deleteService(@PathParam("ServiceID")int serviceID)
	{
		/*if(Globals.accountApproved instanceof Staff)
		{
			if(!((Staff) Globals.accountApproved).isCreateUpdateItems())
			{
				// an attempt to delete the account of other staff member. Which is not permitted !
				Response responseError = Response.status(Status.FORBIDDEN)
						.entity(new ErrorNBSAPI(403, APIErrors.UPDATE_BY_WRONG_USER))
						.build();

				throw new ForbiddenException(APIErrors.UPDATE_BY_WRONG_USER,responseError);
			}
		}*/



		int rowCount = shopDAO.deleteService(serviceID);


		if(rowCount>=1)
		{

			return Response.status(Status.OK)
					.entity(null)
					.build();
		}

		if(rowCount == 0)
		{

			return Response.status(Status.NOT_MODIFIED)
					.entity(null)
					.build();
		}


		return null;
	}







	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getServicesListSimple(
			@QueryParam("latCenter")Double latCenter, @QueryParam("lonCenter")Double lonCenter,
			@QueryParam("proximity")Double proximity,
			@QueryParam("ServiceURL") String serviceURL,
			@QueryParam("SearchString") String searchString,
			@QueryParam("IsOfficial") Boolean isOfficial,@QueryParam("IsVerified")Boolean isVerified,
			@QueryParam("ServiceType") Integer serviceType,
			@QueryParam("SortBy") String sortBy,
			@QueryParam("Limit") Integer limit, @QueryParam("Offset") int offset
	)
	{

		final int max_limit = 100;

		if(limit!=null)
		{
			if(limit>=max_limit)
			{
				limit = max_limit;
			}
		}
		else
		{
			limit = 30;
		}



		ServiceConfigurationEndPoint endPoint = shopDAO.getListQuerySimple(
									latCenter,lonCenter,
									proximity,
									serviceURL,searchString,
									isOfficial,isVerified,
									serviceType,
									sortBy,limit,offset);


		endPoint.setLimit(limit);
		endPoint.setMax_limit(max_limit);
		endPoint.setOffset(offset);


		/*try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/



		//Marker
		return Response.status(Status.OK)
				.entity(endPoint)
				.build();
	}



	// Image Utility Methods

	boolean deleteImageFileInternal(String fileName)
	{
		boolean deleteStatus = false;

		System.out.println("Filename: " + fileName);

		try {

			//Files.delete(BASE_DIR.resolve(fileName));
			deleteStatus = Files.deleteIfExists(BASE_DIR.resolve(fileName));

			// delete thumbnails
			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + fileName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + fileName + ".jpg"));


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return deleteStatus;
	}




	private String saveNewImage(String serviceURL,String imageID)
	{
		try
		{
			serviceURL = serviceURL + "/api/serviceconfiguration/Image/" + imageID;

			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url(serviceURL)
					.build();

			okhttp3.Response response = null;
			response = client.newCall(request).execute();
			response.body().byteStream();
			System.out.println();
			return uploadNewImage(response.body().byteStream());

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return null;
	}



	String uploadNewImage(InputStream in)
	{

		File theDir = new File(BASE_DIR.toString());

		// if the directory does not exist, create it
		if (!theDir.exists()) {

			System.out.println("Creating directory: " + BASE_DIR.toString());

			boolean result = false;

			try{
				theDir.mkdir();
				result = true;
			}
			catch(Exception se){
				//handle it
			}
			if(result) {
				System.out.println("DIR created");
			}
		}



		String fileName = "" + System.currentTimeMillis();


		try {

				// Copy the file to its location.
			long filesize = 0;

			filesize = Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

			if(filesize > MAX_IMAGE_SIZE_MB * 1048 * 1024)
			{
				// delete file if it exceeds the file size limit
				Files.deleteIfExists(BASE_DIR.resolve(fileName));
				return null;
			}

			createThumbnails(fileName);

			Image image = new Image();
			image.setPath(fileName);

			// Return a 201 Created response with the appropriate Location header.

		}
		catch (IOException e) {
		e.printStackTrace();

			return null;
		}

		return fileName;
	}





	// Image MEthods

	private static final java.nio.file.Path BASE_DIR = Paths.get("./images/serviceconfiguration");
	private static final double MAX_IMAGE_SIZE_MB = 2;


	@POST
	@Path("/Image")
	@Consumes({MediaType.APPLICATION_OCTET_STREAM})
	public Response uploadImage(InputStream in, @HeaderParam("Content-Length") long fileSize,
								@QueryParam("PreviousImageName") String previousImageName
	) throws Exception
	{




		if(previousImageName!=null)
		{
			Files.deleteIfExists(BASE_DIR.resolve(previousImageName));
			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + previousImageName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + previousImageName + ".jpg"));
		}


		File theDir = new File(BASE_DIR.toString());

		// if the directory does not exist, create it
		if (!theDir.exists()) {

			System.out.println("Creating directory: " + BASE_DIR.toString());

			boolean result = false;

			try{
				theDir.mkdir();
				result = true;
			}
			catch(Exception se){
				//handle it
			}
			if(result) {
				System.out.println("DIR created");
			}
		}



		String fileName = "" + System.currentTimeMillis();

		// Copy the file to its location.
		long filesize = Files.copy(in, BASE_DIR.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

		if(filesize > MAX_IMAGE_SIZE_MB * 1048 * 1024)
		{
			// delete file if it exceeds the file size limit
			Files.deleteIfExists(BASE_DIR.resolve(fileName));

			return Response.status(Status.EXPECTATION_FAILED).build();
		}


		createThumbnails(fileName);

		Image image = new Image();
		image.setPath(fileName);

		// Return a 201 Created response with the appropriate Location header.

		return Response.status(Status.CREATED)
				.location(URI.create("/api/Images/" + fileName))
				.entity(image)
				.build();
	}



	private void createThumbnails(String filename)
	{
		try {

			Thumbnails.of(BASE_DIR.toString() + "/" + filename)
					.size(300,300)
					.outputFormat("jpg")
					.toFile(new File(BASE_DIR.toString() + "/" + "three_hundred_" + filename));

			//.toFile(new File("five-" + filename + ".jpg"));

			//.toFiles(Rename.PREFIX_DOT_THUMBNAIL);


			Thumbnails.of(BASE_DIR.toString() + "/" + filename)
					.size(500,500)
					.outputFormat("jpg")
					.toFile(new File(BASE_DIR.toString() + "/" + "five_hundred_" + filename));



		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	@GET
	@Path("/Image/{name}")
	@Produces("image/jpeg")
	public InputStream getImage(@PathParam("name") String fileName) {

		//fileName += ".jpg";
		java.nio.file.Path dest = BASE_DIR.resolve(fileName);

		if (!Files.exists(dest)) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}


		try {
			return Files.newInputStream(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}



	@DELETE
	@Path("/Image/{name}")
	public Response deleteImageFile(@PathParam("name")String fileName)
	{

		boolean deleteStatus = false;

		Response response;

		System.out.println("Filename: " + fileName);

		try {


			//Files.delete(BASE_DIR.resolve(fileName));
			deleteStatus = Files.deleteIfExists(BASE_DIR.resolve(fileName));

			// delete thumbnails
			Files.deleteIfExists(BASE_DIR.resolve("three_hundred_" + fileName + ".jpg"));
			Files.deleteIfExists(BASE_DIR.resolve("five_hundred_" + fileName + ".jpg"));


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if(!deleteStatus)
		{
			response = Response.status(Status.NOT_MODIFIED).build();

		}else
		{
			response = Response.status(Status.OK).build();
		}

		return response;
	}
	
}
