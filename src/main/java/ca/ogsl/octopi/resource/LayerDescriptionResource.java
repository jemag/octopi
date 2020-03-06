/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.LayerDescription;
import ca.ogsl.octopi.services.LayerDescriptionService;
import java.util.List;
import javax.annotation.security.RolesAllowed;

@Path("layer-descriptions")
@Api(tags = {"Layer Description"})
@Produces(MediaType.APPLICATION_JSON)
public class LayerDescriptionResource {

  private LayerDescriptionService layerDescriptionService = new LayerDescriptionService();

  @GET
  @ApiOperation(
      value = "Get all layer descriptions",
      response = LayerDescription.class,
      responseContainer = "List"
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  public Response listLayerDescriptions() throws AppException {
    List<LayerDescription> layerDescriptions = this.layerDescriptionService
        .listLayerDescriptions();
    return Response.status(200).entity(layerDescriptions).build();
  }

  @GET
  @Path("{id}")
  @ApiOperation(
      value = "Find layer description by ID",
      response = LayerDescription.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "layer description not found")
  })
  public Response getLayerDescriptionForId(
      @ApiParam(value = "ID of the layer description to be fetched", required = true) @PathParam("id") Integer id
  ) throws
      AppException {
    LayerDescription layerDescription = this.layerDescriptionService
        .getLayerDescriptionForId(id);
    return Response.status(200).entity(layerDescription).build();
  }

  @DELETE
  @Path("{id}")
  @ApiOperation(
      value = "Delete layer description based on ID. LEGACY",
      hidden = true
  )
  public Response deleteLayerDescriptionForId(@PathParam("id") Integer id,
      @HeaderParam("role") String role) throws AppException {
    this.layerDescriptionService.deleteLayerDescriptionForId(id, role);
    return Response.status(204).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Create a new layer description entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "resource created"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response postCreateLayerDescription(LayerDescription layerDescription)
      throws AppException {
    LayerDescription databaseLayerDescription = this.layerDescriptionService
        .postCreateLayerDescription(layerDescription);
    return Response.status(201).entity(databaseLayerDescription).build();
  }
}
