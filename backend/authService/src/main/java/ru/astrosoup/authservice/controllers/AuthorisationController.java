package ru.astrosoup.authservice.controllers;

import jakarta.inject.Inject;


import jakarta.ws.rs.*;

import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import org.antlr.v4.runtime.misc.Pair;
import ru.astrosoup.authservice.DTOs.LoginDto;

import ru.astrosoup.authservice.annotations.AuthorisationBlocked;
import ru.astrosoup.authservice.exceptions.InvalidJwtException;
import ru.astrosoup.authservice.exceptions.JwtGenerationException;
import ru.astrosoup.authservice.exceptions.LoginIsNotValidException;
import ru.astrosoup.authservice.exceptions.UserAlreadyRegisteredException;
import ru.astrosoup.authservice.security.JwtService;
import ru.astrosoup.authservice.services.AuthorisationService;

@Path("/")
public class AuthorisationController {

    @Inject
    AuthorisationService authorisationService;

    @Path("/register") @POST
    public Response register(LoginDto loginDto) {
        try {
            Pair<String, String> jwts = authorisationService.register(loginDto);
            return Response.ok("Registered successfully")
                    .header("Authorization", "Bearer " + jwts.a)
                    .cookie(makeRefreshCookie(jwts.b, JwtService.TokenLiveSpan.LONG.getTtl()))
                    .build();
        } catch (JwtGenerationException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (UserAlreadyRegisteredException e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @Path("/login") @POST
    public Response login(LoginDto loginDto) {
        try {
            Pair<String, String> jwts = authorisationService.login(loginDto);
            return Response.ok("Logged in successfully")
                    .header("Authorization", "Bearer " + jwts.a)
                    .cookie(makeRefreshCookie(jwts.b, JwtService.TokenLiveSpan.LONG.getTtl()))
                    .build();
        } catch (LoginIsNotValidException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (JwtGenerationException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("/refresh") @GET
    public Response refresh(@CookieParam("refresh-jwt") Cookie cookie) {
        try {
            Pair<String, String> jwts = authorisationService.refresh(cookie.getValue());
            return Response.ok("Refreshed successfully")
                    .header("Authorization", "Bearer " + jwts.a)
                    .cookie(makeRefreshCookie(jwts.b, JwtService.TokenLiveSpan.LONG.getTtl()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @Path("/logout") @GET
    @AuthorisationBlocked
    public Response logout(@HeaderParam("Authorization") String authHeader, @CookieParam("refresh-jwt") Cookie cookie) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String longJwt = cookie.getValue();

        try {
            authorisationService.logout(longJwt);
        } catch (InvalidJwtException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok("Logged out successfully").cookie(makeRefreshCookie("", 0)).build();
    }

    private NewCookie makeRefreshCookie(String value, int maxAge) {
        return new NewCookie.Builder("refresh-jwt")
                .httpOnly(true)
                .maxAge(maxAge)
                .secure(true)
                .sameSite(NewCookie.SameSite.LAX)
                .value(value)
                .build();
    }

    @Path("/checkauth") @GET
    @AuthorisationBlocked
    public Response checkauth() {
        return Response.ok("Pong!").build();
    }


    @Path("/ping") @GET
    public  Response ping() {
        return Response.ok("Pong!").build();
    }
}
