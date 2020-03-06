/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.Category;
import ca.ogsl.octopi.services.CategoryService;
import ca.ogsl.octopi.util.AppConstants;
import java.util.List;
import javax.annotation.security.RolesAllowed;

@Path("categories")
@Api(tags = {"Category"})
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {

  private CategoryService categoryService = new CategoryService();

  @GET
  @ApiOperation(
      value = "Get a list of all categories",
      response = Category.class,
      responseContainer = "List"
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  public Response listCategories() throws AppException {
    List<Category> categories = this.categoryService.listCategories();
    return Response.status(200).entity(categories).build();
  }

  @GET
  @Path("{id}")
  @ApiOperation(
      value = "Get category by ID",
      response = Category.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "category not found")
  })
  public Response getCategory(
      @ApiParam(value = "ID of category to be fetched", required = true) @PathParam("id") Integer id
  ) throws AppException {
    Category category = this.categoryService.getCategory(id);
    return Response.status(200).entity(category).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Create a new category entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "resource created"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response postCreateCategory(Category category)
      throws AppException {
    Category databaseCategory = this.categoryService.postCreateCategory(category);
    return Response.status(201).entity(databaseCategory).build();
  }


  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Update a category entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "successful operation"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response putUpdateCategory(@PathParam("id") Integer categoryId, Category category)
      throws AppException {
    Category oldCategory = this.categoryService.retrieveCategory(categoryId);
    if (oldCategory == null) {
      throw new AppException(400, 400,
          "Use post to create entity", AppConstants.PORTAL_URL);
    } else {
      this.categoryService.putUpdateCategory(category, oldCategory);
      return Response.status(200).build();
    }
  }

}
