package org.nearbyshops.sds.Globals;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.nearbyshops.sds.DAOPrepared.ServiceConfigurationDAO;
import org.nearbyshops.sds.DAOPreparedReviewItem.FavoriteMarketDAO;
import org.nearbyshops.sds.DAOPreparedReviewItem.MarketReviewDAO;
import org.nearbyshops.sds.DAOPreparedReviewItem.MarketReviewThanksDAO;
import org.nearbyshops.sds.DAORoles.*;
import org.nearbyshops.sds.Model.ServiceConfigurationLocal;
import org.nearbyshops.sds.Model.ServiceConfigurationGlobal;
import org.nearbyshops.sds.ModelReviewMarket.FavouriteMarket;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;

import javax.ws.rs.core.MediaType;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;


public class Globals {



	// secure randon for generating tokens
	public static SecureRandom random = new SecureRandom();


	public static DAOUserNew daoUserNew = new DAOUserNew();
	public static DAOStaff daoStaff = new DAOStaff();

	public static DAOResetPassword daoResetPassword= new DAOResetPassword();
	public static DAOUserSignUp daoUserSignUp = new DAOUserSignUp();
	public static DAOEmailVerificationCodes daoEmailVerificationCodes=new DAOEmailVerificationCodes();
	public static DAOPhoneVerificationCodes daoPhoneVerificationCodes=new DAOPhoneVerificationCodes();


	public static MarketReviewDAO marketReviewDAO = new MarketReviewDAO();
	public static FavoriteMarketDAO favoriteMarketDAO = new FavoriteMarketDAO();
	public static MarketReviewThanksDAO marketReviewThanksDAO = new MarketReviewThanksDAO();




//	public static DAOResetPassword daoResetPassword= new DAOResetPassword();
//	public static DAOPhoneVerificationCodes daoPhoneVerificationCodes=new DAOPhoneVerificationCodes();
//



	public static ServiceConfigurationDAO serviceConfigurationDAO = new ServiceConfigurationDAO();


	// static reference for holding security accountApproved

	public static Object accountApproved;


	// Configure Connection Pooling

	private static HikariDataSource dataSource;





//	public static HikariDataSource getDataSourceDeprecated()
//	{
//		if(dataSource==null)
//		{
//			HikariConfig config = new HikariConfig();
//			config.setJdbcUrl(JDBCContract.CURRENT_CONNECTION_URL);
//			config.setUsername(JDBCContract.CURRENT_USERNAME);
//			config.setPassword(JDBCContract.CURRENT_PASSWORD);
//
//			dataSource = new HikariDataSource(config);
//		}
//
//		return dataSource;
//	}


	public static HikariDataSource getDataSource() {


		if (dataSource == null) {


			org.apache.commons.configuration2.Configuration configuration = GlobalConfig.getConfiguration();


			if(configuration==null)
			{
				System.out.println("failed to load api configuration ... " +
						"Configuration is null ...  : getDataSource() HikariCP !");

				return null ;
			}



//            String connection_url = configuration.getString("");
//            String username = configuration.getString(ConfigurationKeys.POSTGRES_USERNAME);
//            String password = configuration.getString(ConfigurationKeys.POSTGRES_PASSWORD);



			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(GlobalConstants.POSTGRES_CONNECTION_URL);
			config.setUsername(GlobalConstants.POSTGRES_USERNAME);
			config.setPassword(GlobalConstants.POSTGRES_PASSWORD);


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









//    // mailgun configuration

//	private static Configuration configurationMailgun;
//
//
//	public static Configuration getMailgunConfiguration()
//	{
//
//		if(configurationMailgun==null)
//		{
//
//			configurationMailgun = new Configuration()
//					.domain(GlobalConstants.MAILGUN_DOMAIN)
//					.apiKey(GlobalConstants.MAILGUN_API_KEY)
//					.from(GlobalConstants.MAILGUN_NAME, GlobalConstants.MAILGUN_EMAIL);
//
//			return configurationMailgun;
//		}
//		else
//		{
//			return configurationMailgun;
//		}
//	}










	public static void sendEmail(String email_address, String name_of_receiver, String subject, String message)
	{
		Email email = EmailBuilder.startingBlank()
				.from(GlobalConstants.EMAIL_SENDER_NAME, GlobalConstants.EMAIL_ADDRESS_FOR_SENDER)
				.to(name_of_receiver,email_address)
				.withSubject(subject)
				.withPlainText(message)
				.buildEmail();

		getMailerInstance().sendMail(email,true);
	}







	public static Mailer inHouseMailer;


	public static Mailer getMailerInstance()
	{
		if(inHouseMailer==null)
		{
			inHouseMailer = MailerBuilder
					.withSMTPServer(GlobalConstants.SMTP_SERVER_URL, GlobalConstants.SMTP_PORT,
							GlobalConstants.SMTP_USERNAME, GlobalConstants.SMTP_PASSWORD)
					.buildMailer();

			return inHouseMailer;
		}
		else
		{
			return inHouseMailer;
		}
	}


}
