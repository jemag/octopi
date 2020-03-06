/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.Topic;
import ca.ogsl.octopi.models.TopicHierarchy;
import ca.ogsl.octopi.services.TopicService;
import ca.ogsl.octopi.util.AppConstants;
import java.util.List;
import javax.annotation.security.RolesAllowed;

@Path("topics")
@Api(tags = {"Topic"})
@Produces(MediaType.APPLICATION_JSON)
public class TopicResource {

  private TopicService topicService = new TopicService();

  @GET
  @ApiOperation(
      value = "Find topic by id",
      response = Topic.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "topic not found")
  })
  @Path("{id}")
  public Response getTopicForId(@PathParam("id") Integer id) throws Exception {
    Topic topic = this.topicService.getTopicForId(id);
    return Response.status(200).entity(topic).build();
  }

  @GET
  @Path("getTopicForCode")
  @ApiOperation(
      value = "Get topic by code and language code",
      response = Topic.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "topic not found")
  })
  public Response getTopicGroupForCode(
      @ApiParam(value = "Code of topic group to be fetched", required = true) @QueryParam("code") String code,
      @ApiParam(value = "Code of the language needed", required = true)
      @QueryParam("language-code") String languageCode) throws AppException {
    Topic topic = this.topicService.getTopicForCode(code, languageCode);
    return Response.status(200).entity(topic).build();
  }

  @GET
  @ApiOperation(
      value = "Get the list of topics",
      response = Topic.class,
      responseContainer = "List"
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  public Response getTopicList() {
    List<Topic> topics = this.topicService.getTopicList();
    return Response.status(200).entity(topics).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Create a new topic entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "resource created"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response postCreateTopic(Topic topic)
      throws AppException {
    Topic databaseTopic = this.topicService.postCreateTopic(topic);
    return Response.status(201).entity(databaseTopic).build();
  }

  @GET
  @Path("{id}/getTopicHierarchy")
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Get the whole hierarchy of categories for a specific topic",
      response = TopicHierarchy.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "sucessful operation"),
      @ApiResponse(code = 404, message = "Not found")
  })
  public Response getTopicHierarchy(@PathParam("id") Integer topicId) throws Exception {
    TopicHierarchy topicHierarchy = this.topicService.getTopicHierarchy(topicId);
    return Response.status(200).entity(topicHierarchy).build();
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Update a topic entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "successful operation"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response putUpdateTopic(@PathParam("id") Integer topicId, Topic topic)
      throws AppException {
    Topic oldTopic = this.topicService.retrieveTopic(topicId);
    if (oldTopic == null) {
      throw new AppException(400, 400,
          "Use post to create entity", AppConstants.PORTAL_URL);
    } else {
      this.topicService.putUpdateTopic(topic, oldTopic);
      return Response.status(200).build();
    }
  }
}
