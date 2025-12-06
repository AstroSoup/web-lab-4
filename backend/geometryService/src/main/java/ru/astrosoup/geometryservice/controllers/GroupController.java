package ru.astrosoup.geometryservice.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.astrosoup.geometryservice.DTOs.GroupRequest;
import ru.astrosoup.geometryservice.DTOs.GroupResponse;
import ru.astrosoup.geometryservice.DTOs.JwtDto;
import ru.astrosoup.geometryservice.annotations.AuthorisationBlocked;
import ru.astrosoup.geometryservice.exceptions.InvalidGroupRequestException;
import ru.astrosoup.geometryservice.services.GroupService;

import java.util.List;

@Path("/group")
@AuthorisationBlocked
public class GroupController {

    @Inject
    private GroupService groupService;

    @Context
    private HttpServletRequest request;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGroup(GroupRequest group) {
        group.setUser(getUserFromJwt());
        GroupResponse response = groupService.createGroup(group);
        return Response.ok(response).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGroups() {

        List<GroupResponse> response = groupService.getGroups(getUserFromJwt());
        return Response.ok(response).build();
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGroup(GroupRequest group) {
        group.setUser(getUserFromJwt());
        GroupResponse response = groupService.updateGroup(group);
        return Response.ok(response).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGroup(GroupRequest group) {
        try{
            group.setUser(getUserFromJwt());
            groupService.deleteGroup(group);
            return Response.ok().build();
        } catch (InvalidGroupRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    private JwtDto getUserFromJwt() {
        return (JwtDto) request.getAttribute("user");
    }
}
