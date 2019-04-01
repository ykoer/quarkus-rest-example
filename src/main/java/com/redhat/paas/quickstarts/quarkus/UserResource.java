package com.redhat.paas.quickstarts.quarkus;

import com.redhat.paas.quickstarts.quarkus.model.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.Set;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @GET
    public Set<User> list() {
        return userService.getAllUsers();
    }

    @GET
    @Path("{username}")
    public Response get(@PathParam("username") String userName) {

        Optional<User> user = userService.getUser(userName);
        if(user.isPresent()) {
            return Response
                    .status(Response.Status.OK)
                    .entity(user.get())
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @POST
    public void add(User user) {
        userService.saveUser(user);
    }

    @PUT
    public void update(User user) {
        userService.updateUser(user);
    }

    @DELETE
    @Path("{username}")
    public void delete(@PathParam("username") String userName) {
        userService.deleteUser(userName);
    }

    @DELETE
    public void deleteAll() {
        userService.deleteAllUsers();
    }
}
