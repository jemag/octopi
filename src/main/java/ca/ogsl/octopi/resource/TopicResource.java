/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.exception.InvalidRequestException;
import ca.ogsl.octopi.models.Topic;
import ca.ogsl.octopi.models.TopicHierarchy;
import ca.ogsl.octopi.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;

@RestController
@RequestMapping("/api")
@Tag(name = "Topic", description = "Endpoint to retrieve topic objects ")
public class TopicResource {
  
  private TopicService topicService = new TopicService();
  
  @GetMapping(value = "/topics/{id}")
  @Operation(summary = "Find topic by id")
  public Topic getTopicForId(@PathVariable("id") Integer id) {
    return this.topicService.getTopicForId(id);
  }
  
  @GetMapping(value = "/topics/getTopicForCode")
  @Operation(summary = "Get topic by code and language code")
  public Topic getTopicGroupForCode(
      @Parameter(description = "Code of topic group to be fetched", required = true) @RequestParam("code") String code,
      @Parameter(description = "Code of the language needed", required = true)
      @RequestParam("language-code") String languageCode) {
    return this.topicService.getTopicForCode(code, languageCode);
  }
  
  @GetMapping(value = "/topics")
  @Operation(summary = "Get the list of topics")
  public Collection<Topic> getTopicList() {
    return this.topicService.getTopicList();
  }
  
  @RolesAllowed("ADMIN")
  @PostMapping(value = "/topics")
  @Operation(summary = "Create a new topic entry")
  public ResponseEntity<Topic> postCreateTopic(Topic topic) {
    Topic databaseTopic = this.topicService.postCreateTopic(topic);
    return ResponseEntity.status(201).body(databaseTopic);
  }
  
  @GetMapping(value = "/topics/{id}/getTopicHierarchy")
  @Operation(summary = "Get the whole hierarchy of categories for a specific topic")
  public TopicHierarchy getTopicHierarchy(@PathVariable("id") Integer topicId) throws Exception {
    return this.topicService.getTopicHierarchy(topicId);
  }
  
  @RolesAllowed("ADMIN")
  @PutMapping(value = "/topics/{id}")
  @Operation(summary = "Update a topic entry")
  public ResponseEntity putUpdateTopic(@PathVariable("id") Integer topicId, Topic topic) {
    Topic oldTopic = this.topicService.retrieveTopic(topicId);
    if (oldTopic == null) {
      throw new InvalidRequestException("Use post to create entity");
    } else {
      this.topicService.putUpdateTopic(topic, oldTopic);
      return ResponseEntity.status(200).build();
    }
  }
}
