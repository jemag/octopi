/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.ClientPresentation;
import ca.ogsl.octopi.services.ClientPresentationService;
import java.util.List;
import javax.annotation.security.RolesAllowed;

@Path("client-presentations")
@Api(tags = {"Client Presentation"})
@Produces(MediaType.APPLICATION_JSON)
public class ClientPresentationResource {

  private ClientPresentationService clientPresentationService = new ClientPresentationService();

  @GET
  @ApiOperation(
      value = "Get all client presentations",
      response = ClientPresentation.class,
      responseContainer = "List"
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  public Response listClientPresentations() throws AppException {
    List<ClientPresentation> clientPresentations = this.clientPresentationService
        .listClientPresentations();
    return Response.status(200).entity(clientPresentations).build();
  }

  @GET
  @Path("{id}")
  @ApiOperation(
      value = "Find client presentation by ID",
      response = ClientPresentation.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "client presentation not found")
  })
  public Response getClientPresentationForId(
      @ApiParam(value = "ID of the client presentation to be fetched",
          required = true) @PathParam("id") Integer id) throws AppException {
    ClientPresentation clientPresentation = this.clientPresentationService
        .getClientPresentationForId(id);
    return Response.status(200).entity(clientPresentation).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Create a new client presentation entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "resource created"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response postCreateClientPresentation(ClientPresentation clientPresentation)
      throws AppException {
    ClientPresentation databaseCP = this.clientPresentationService.
        postCreateClientPresentation(clientPresentation);
    return Response.status(201).entity(databaseCP).build();
  }
}
