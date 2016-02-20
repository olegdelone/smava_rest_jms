/**
 * Created by Apache CXF WadlToJava code generator
**/
package de.smava.bank.client.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("account")
public interface AccountResource {

    @PUT
    @Consumes("application/xml")
    void add();

    @POST
    @Consumes("application/xml")
    void update();

    @GET
    @Produces("application/xml")
    Response getAll();

    @DELETE
    @Path("/{id}")
    void remove(@PathParam("id") long id);

    @GET
    @Produces("application/xml")
    @Path("/{id}")
    Response getId(@PathParam("id") long id);

}