package ru.astrosoup.geometryservice.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.astrosoup.geometryservice.DTOs.JwtDto;
import ru.astrosoup.geometryservice.DTOs.AreaHitDto;
import ru.astrosoup.geometryservice.DTOs.AreaHitRequest;
import ru.astrosoup.geometryservice.DTOs.AreaHitResponse;
import ru.astrosoup.geometryservice.annotations.AuthorisationBlocked;
import ru.astrosoup.geometryservice.exceptions.InvalidHitRequestException;
import ru.astrosoup.geometryservice.exceptions.UserDoesNotExistException;
import ru.astrosoup.geometryservice.services.HitService;

import java.util.List;
import java.util.logging.Logger;

@Path("/hit")
@AuthorisationBlocked
public class HitController {

    private static final Logger logger = Logger.getLogger(HitController.class.getName());

    @Inject
    HitService hitService;

    @Context
    HttpServletRequest request;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResults() {
        try {

            JwtDto user = (JwtDto) request.getAttribute("user");
            List<AreaHitResponse> hits = hitService.getHits(user);

            return Response.ok(hits).build();
        } catch (UserDoesNotExistException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkHit(AreaHitRequest areaHitRequest) {
        try {
            JwtDto user = (JwtDto) request.getAttribute("user");
            AreaHitResponse hit = hitService.addHit(new AreaHitDto(user, areaHitRequest));
            return Response.ok(hit).build();
        } catch (InvalidHitRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (UserDoesNotExistException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteHit(AreaHitRequest hit) {
        try {
            JwtDto user = (JwtDto) request.getAttribute("user");
            hit.setUser(user);
            hitService.delete(hit);
            return Response.ok("Hit is deleted successfully.").build();
        } catch (InvalidHitRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }
}
