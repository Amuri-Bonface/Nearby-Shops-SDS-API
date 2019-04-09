package org.nearbyshops.sds.Globals;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.nearbyshops.sds.DAOPrepared.ServiceConfigurationDAO;
import org.nearbyshops.sds.DAOsPreparedRoles.AdminDAOPrepared;

import org.nearbyshops.sds.DAOsPreparedRoles.StaffDAOPrepared;
import org.nearbyshops.sds.JDBCContract;
import org.nearbyshops.sds.Model.ServiceConfigurationLocal;
import org.nearbyshops.sds.Model.ServiceConfigurationGlobal;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;


public class Globals {

	public static AdminDAOPrepared adminDAOPrepared = new AdminDAOPrepared();
	public static StaffDAOPrepared staffDAOPrepared = new StaffDAOPrepared();


	public static org.nearbyshops.sds.DAOsPreparedRolesOld.AdminDAOPrepared adminDAOPreparedOld = new org.nearbyshops.sds.DAOsPreparedRolesOld.AdminDAOPrepared();
	public static org.nearbyshops.sds.DAOsPreparedRolesOld.StaffDAOPrepared staffDAOPreparedOld = new org.nearbyshops.sds.DAOsPreparedRolesOld.StaffDAOPrepared();

	public static ServiceConfigurationDAO serviceConfigurationDAO = new ServiceConfigurationDAO();


	// static reference for holding security accountApproved

	public static Object accountApproved;


	// Configure Connection Pooling

	private static HikariDataSource dataSource;



	public static HikariDataSource getDataSource()
	{
		if(dataSource==null)
		{
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(JDBCContract.CURRENT_CONNECTION_URL);
			config.setUsername(JDBCContract.CURRENT_USERNAME);
			config.setPassword(JDBCContract.CURRENT_PASSWORD);

			dataSource = new HikariDataSource(config);
		}

		return dataSource;
	}



	// SSE Notifications Support

	public static Map<Integer,SseBroadcaster> broadcasterMap = new HashMap<>();

	public static String broadcastMessage(String message, int shopID) {

		OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
		OutboundEvent event = eventBuilder.name("Order Received !")
				.mediaType(MediaType.TEXT_PLAIN_TYPE)
				.data(String.class, message)
				.build();


		if(broadcasterMap.get(shopID)!=null)
		{
			broadcasterMap.get(shopID).broadcast(event);
		}

		return "Message '" + message + "' has been broadcast.";
	}




	public static SseBroadcaster broadcaster = new SseBroadcaster();

	public static String broadcastMessage(String message) {
		OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
		OutboundEvent event = eventBuilder.name("message")
				.mediaType(MediaType.TEXT_PLAIN_TYPE)
				.data(String.class, message)
				.build();

		broadcaster.broadcast(event);

		return "Message '" + message + "' has been broadcast.";
	}




	public static ServiceConfigurationGlobal localToGlobal(ServiceConfigurationLocal serviceConfigurationLocalLocal, String serviceURL)
	{
		ServiceConfigurationGlobal serviceConfigurationGlobal  = new ServiceConfigurationGlobal();

		serviceConfigurationGlobal.setServiceID(serviceConfigurationLocalLocal.getServiceID());
		serviceConfigurationGlobal.setLogoImagePath(serviceConfigurationLocalLocal.getLogoImagePath());
		serviceConfigurationGlobal.setBackdropImagePath(serviceConfigurationLocalLocal.getBackdropImagePath());

		serviceConfigurationGlobal.setServiceName(serviceConfigurationLocalLocal.getServiceName());
		serviceConfigurationGlobal.setHelplineNumber(serviceConfigurationLocalLocal.getHelplineNumber());
		serviceConfigurationGlobal.setDescriptionShort(serviceConfigurationLocalLocal.getDescriptionShort());
		serviceConfigurationGlobal.setDescriptionLong(serviceConfigurationLocalLocal.getDescriptionLong());

		serviceConfigurationGlobal.setAddress(serviceConfigurationLocalLocal.getAddress());
		serviceConfigurationGlobal.setCity(serviceConfigurationLocalLocal.getCity());
		serviceConfigurationGlobal.setPincode(serviceConfigurationLocalLocal.getPincode());
		serviceConfigurationGlobal.setLandmark(serviceConfigurationLocalLocal.getLandmark());
		serviceConfigurationGlobal.setState(serviceConfigurationLocalLocal.getState());
		serviceConfigurationGlobal.setCountry(serviceConfigurationLocalLocal.getCountry());

		serviceConfigurationGlobal.setISOCountryCode(serviceConfigurationLocalLocal.getISOCountryCode());
		serviceConfigurationGlobal.setISOLanguageCode(serviceConfigurationLocalLocal.getISOLanguageCode());
		serviceConfigurationGlobal.setISOCurrencyCode(serviceConfigurationLocalLocal.getISOCurrencyCode());

		serviceConfigurationGlobal.setServiceType(serviceConfigurationLocalLocal.getServiceType());
		serviceConfigurationGlobal.setServiceLevel(serviceConfigurationLocalLocal.getServiceLevel());

		serviceConfigurationGlobal.setLatCenter(serviceConfigurationLocalLocal.getLatCenter());
		serviceConfigurationGlobal.setLonCenter(serviceConfigurationLocalLocal.getLonCenter());
		serviceConfigurationGlobal.setServiceRange(serviceConfigurationLocalLocal.getServiceRange());

		serviceConfigurationGlobal.setUpdated(serviceConfigurationLocalLocal.getUpdated());
		serviceConfigurationGlobal.setCreated(serviceConfigurationLocalLocal.getCreated());

		serviceConfigurationGlobal.setServiceURL(serviceURL);

		return serviceConfigurationGlobal;
	}




}
