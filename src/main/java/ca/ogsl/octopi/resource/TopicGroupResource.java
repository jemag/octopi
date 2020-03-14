/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.exception.InvalidRequestException;
import ca.ogsl.octopi.models.TopicGroup;
import ca.ogsl.octopi.services.TopicGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;

@RestController
@RequestMapping("/api")
@Tag(name = "Topic Group", description = "Endpoint to retrieve topic group objects ")
public class TopicGroupResource {

  private TopicGroupService topicGroupService = new TopicGroupService();
  
  @GetMapping(value = "/topic-groups")
  @Operation(summary = "Get a list of all topic groups")
  public Collection<TopicGroup> listTopicGroups() {
    return this.topicGroupService.listTopicGroups();
  }
  
  @GetMapping(value = "/topic-groups/{id}")
  @Operation(summary = "Get a topic group by ID")
  public TopicGroup getTopicGroup(
      @PathVariable @Parameter(description = "ID of topic group to be fetched", required = true) Integer id
  ) {
    return this.topicGroupService.getTopicGroup(id);
  }
  
  @GetMapping(value = "/topic-groups/getTopicGroupForCode")
  @Operation(summary = "Get topic group by code and language code")
  public TopicGroup getTopicGroupForCode(
      @PathVariable @Parameter(description = "Code of topic group to be fetched", required = true) String code,
      @PathVariable @Parameter(description = "Code of the language needed", required = true)
      @RequestParam("language-code") String languageCode) {
    return this.topicGroupService.getTopicGroupForCode(code, languageCode);
  }

  @RolesAllowed("ADMIN")
  @PostMapping(value = "/topic-groups")
  @Operation(summary = "Create a new topic group entry")
  public ResponseEntity<TopicGroup> postCreateTopicGroup(TopicGroup topicGroup) {
    TopicGroup databaseTopicGroup = this.topicGroupService.postCreateTopicGroup(topicGroup);
    return ResponseEntity.status(201).body(databaseTopicGroup);
  }

  @PutMapping(value = "/topic-groups/{id}")
  @Operation(summary = "Update a topic group entry")
  @RolesAllowed("ADMIN")
  public ResponseEntity putTopicGroup(@PathVariable("id") Integer topicGroupId, TopicGroup topicGroup) {
    TopicGroup oldTopicGroup = this.topicGroupService.retrieveTopicGroup(topicGroupId);
    if (oldTopicGroup == null) {
      throw new InvalidRequestException("Use post to create entity");
    } else {
      this.topicGroupService.putUpdateTopicGroup(topicGroup, oldTopicGroup);
      return ResponseEntity.status(200).build();
    }
  }
}
