/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kodnito.restapi.rest;

import com.kodnito.restapi.dao.TodoDAO;
import com.kodnito.restapi.entity.Todo;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author hayricicek
 */
@RequestScoped
@Path("todos")
public class TodoEndpoint {

    @Inject
    TodoDAO todoDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Todo> todos = todoDAO.getAll();

        return Response.status(Status.OK)
                .entity(new GenericEntity<List<Todo>>(todos) {
                }).build();
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTodo(@PathParam("id") Long id) {
        Todo todo = todoDAO.findById(id);

        return Response.ok(todo).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTodo(@PathParam("id") Long id, Todo todo) {
        Todo updateTodo = todoDAO.findById(id);

        updateTodo.setTask(todo.getTask());
        updateTodo.setDescription(todo.getDescription());
        todoDAO.update(updateTodo);

        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTodo(Todo todo, @Context UriInfo uriInfo) {
        Long todoId = todoDAO.create(todo);

        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Long.toString(todoId));

        return Response.created(builder.build()).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTodo(@PathParam("id") Long id) {
        Todo getTodo = todoDAO.findById(id);

        todoDAO.delete(getTodo);

        return Response.ok().build();
    }

}
