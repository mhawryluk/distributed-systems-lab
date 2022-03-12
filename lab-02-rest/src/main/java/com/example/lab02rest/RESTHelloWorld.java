package com.example.lab02rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Path("/hello")
public class RESTHelloWorld {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello(){
        return "Hello Jersey";
    }

    @GET
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello(){
        return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHTMLHello(){
        return "<html> " +
                "   <title> Hello Jersey </title> " +
                "   <body>" +
                "       <h1> Hello Jersey </h1>" +
                "   </body>" +
                "</html>";
    }

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(getBaseURI());

        // Fluent interfaces
        System.out.println(target.path("rest").path("hello").request()
                .accept(MediaType.TEXT_PLAIN).get(Response.class).toString());

        // Get plain text
        System.out.println(target.path("rest").path("hello").request()
                .accept(MediaType.TEXT_PLAIN).get(String.class));

        // Get XML
        System.out.println(target.path("rest").path("hello").request()
                .accept(MediaType.TEXT_XML).get(String.class));


        // Get HTML
        System.out.println(target.path("rest").path("hello").request()
                .accept(MediaType.TEXT_HTML).get(String.class));
    }
    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/lab").build();
    }

}
