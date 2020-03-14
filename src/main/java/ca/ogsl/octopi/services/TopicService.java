/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.dao.TopicDao;
import ca.ogsl.octopi.dao.TopicHierarchyDao;
import ca.ogsl.octopi.exception.EntityNotFoundException;
import ca.ogsl.octopi.exception.InvalidRequestException;
import ca.ogsl.octopi.models.Topic;
import ca.ogsl.octopi.models.TopicHierarchy;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;
import ca.ogsl.octopi.validation.tagging.PutCheck;

import javax.validation.groups.Default;
import java.util.List;

public class TopicService {

  private GenericDao genericDao = new GenericDao();
  private TopicDao topicDao = new TopicDao();
  private TopicHierarchyDao topicHierarchyDao = new TopicHierarchyDao();
  
  public Topic getTopicForId(Integer id) {
    Topic topic = this.genericDao.getEntityFromId(id, Topic.class);
    if (topic == null) {
      throw new EntityNotFoundException("Invalid topic identifier");
    }
    return topic;
  }
  
  public Topic postCreateTopic(Topic topic) {
    ValidationUtil.validateBean(topic, PostCheck.class, Default.class);
    return this.genericDao.persistEntity(topic);
  }

  public Topic retrieveTopic(Integer topicId) {
    return this.genericDao.getEntityFromId(topicId, Topic.class);
  }
  
  public void putUpdateTopic(Topic topic, Topic oldTopic) {
    ValidationUtil.validateBean(topic, PutCheck.class, Default.class);
    checkIfUpdatingToExistingEntity(topic, oldTopic);
    this.topicDao.fullUpdateTopic(topic, oldTopic);
  }

  private void checkIfUpdatingToExistingEntity(Topic topic, Topic oldTopic) {
    if (!topic.getId().equals(oldTopic.getId())) {
      Topic databaseTopic = this.genericDao.getEntityFromId(topic.getId(), Topic.class);
      if (databaseTopic != null) {
        throw new InvalidRequestException("Cannot update with id from another existing entity");
      }
    }
  }

  public TopicHierarchy getTopicHierarchy(Integer topicId) throws Exception {
    TopicHierarchy topicHierarchy = this.topicHierarchyDao.getTopicHierarchyById(topicId);
    if (topicHierarchy == null) {
      throw new EntityNotFoundException("Invalid topic identifier");
    }
    return topicHierarchy;
  }

  public List<Topic> getTopicList() {
    return this.genericDao.getAllEntities(Topic.class);
  }
  
  public Topic getTopicForCode(String code, String languageCode) {
    Topic topic = this.topicDao.getTopicFromCode(code, languageCode);
    if (topic == null) {
      throw new EntityNotFoundException("Not found");
    }
    return topic;
  }
}
