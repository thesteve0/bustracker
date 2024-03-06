package org.techraven;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.transit.realtime.GtfsRealtime;
import com.google.transit.realtime.GtfsRealtime.FeedEntity;
import com.google.transit.realtime.GtfsRealtime.VehiclePosition;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Path("/")
public class main {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello RESTEasy";
    }

    @Path("/me")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String me() {
        return "Steve";
    }

    @Path("/transit")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String vehicles() {
        URL url;

        GtfsRealtime.FeedMessage feed;
        try {
            //url = new URL("http://s3.amazonaws.com/commtrans-realtime-prod/alerts.pb");
            url = new URL("http://s3.amazonaws.com/commtrans-realtime-prod/vehiclepositions.pb");
            //url = new URL("http://s3.amazonaws.com/commtrans-realtime-prod/tripupdates.pb");
            feed = GtfsRealtime.FeedMessage.parseFrom(url.openStream());
            List<FeedEntity> Entities = feed.getEntityList();
            // http://dev.opentripplanner.org/javadoc/0.19.0/com/google/transit/realtime/GtfsRealtime.FeedEntity.html
            for (FeedEntity entity : Entities) {
                 VehiclePosition bus = entity.getVehicle();
                 if (bus.hasCurrentStatus()) {
                     System.out.println(bus.getCurrentStatus().getNumber());
                }
                 if (bus.hasPosition()) {
                     System.out.println(bus.getPosition());

                 }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(feed);

    }
}
