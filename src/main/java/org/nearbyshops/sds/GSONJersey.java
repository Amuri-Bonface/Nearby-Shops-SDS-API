package org.nearbyshops.sds;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.DateFormat;

/*
*
 * Created by sumeet on 20/8/16.
*/


@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GSONJersey implements MessageBodyWriter<Object>,
        MessageBodyReader<Object> {


    private static final String UTF_8 = "UTF-8";

    private Gson gson;

    //Customize the gson behavior here
    private Gson getGson() {
        if (gson == null) {
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gson = gsonBuilder
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            //                    .setDateFormat("yyyy-MM-dd hh:mm:ss.S")

//            .disableHtmlEscaping()

//            .setDateFormat("EEEEE, dd MMM yyyy HH:mm:ss ZZZZ zzz")

//                    .setDateFormat(DateFormat.FULL,DateFormat.FULL)


//            System.out.println("GSON COnverter");

/*
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .setPrettyPrinting()
                    .serializeNulls()
*/

        }
        return gson;
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
//        System.out.println("GSON COnverter");
        return true;
    }

    @Override
    public Object readFrom(Class<Object> type, Type genericType,
                           Annotation[] annotations, MediaType mediaType,
                           MultivaluedMap<String, String> httpHeaders, InputStream entityStream) {

//        System.out.println("GSON COnverter");

        InputStreamReader streamReader = null;
        try {
            streamReader = new InputStreamReader(entityStream, UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            Type jsonType;
            if (type.equals(genericType)) {
                jsonType = type;
            } else {
                jsonType = genericType;
            }
            return getGson().fromJson(streamReader, jsonType);
        } finally {
            try {
                streamReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType,
                               Annotation[] annotations, MediaType mediaType) {

//        System.out.println("GSON COnverter");

        return true;
    }

    @Override
    public long getSize(Object object, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Object object, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream) throws IOException,
            WebApplicationException {

//        System.out.println("GSON COnverter");

        OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8);
        try {
            Type jsonType;
            if (type.equals(genericType)) {
                jsonType = type;
            } else {
                jsonType = genericType;
            }
            getGson().toJson(object, jsonType, writer);
        } finally {
            writer.close();
        }
    }
}
