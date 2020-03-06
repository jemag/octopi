/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.ClickFormatter;
import ca.ogsl.octopi.services.ClickFormatterService;
import java.util.List;
import javax.annotation.security.RolesAllowed;

@Api(tags = {"Click Formatter"})
@Produces(MediaType.APPLICATION_JSON)
public class ClickFormatterResource {

  private ClickFormatterService clickFormatterService = new ClickFormatterService();

  @GET
  @ApiOperation(
      value = "Get all click formatters",
      response = ClickFormatter.class,
      responseContainer = "List"
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  public Response listClickFormatters() throws AppException {
    List<ClickFormatter> clickFormatters = this.clickFormatterService.listClickFormatters();
    return Response.status(200).entity(clickFormatters).build();
  }

  @GET
  @Path("{id}")
  @ApiOperation(
      value = "Find Click Formatter by ID",
      response = ClickFormatter.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "Click Formatter not found")
  })
  public Response getClickFormatterById(
      @ApiParam(value = "ID of the Click Formatter to be fetched", required = true) @PathParam("id")
          Integer id) throws AppException {
    ClickFormatter clickFormatter = this.clickFormatterService.getClickFormatter(id);
    return Response.status(200).entity(clickFormatter).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Create a new click formatter entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "resource created"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response postCreateClickFormatter(ClickFormatter clickFormatter)
      throws AppException {
    ClickFormatter databaseCF = this.clickFormatterService.postCreateClickFormatter(clickFormatter);
    return Response.status(201).entity(databaseCF).build();
  }
}
