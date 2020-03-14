/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.dao.TopicGroupDao;
import ca.ogsl.octopi.exception.EntityNotFoundException;
import ca.ogsl.octopi.exception.InvalidRequestException;
import ca.ogsl.octopi.models.TopicGroup;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;
import ca.ogsl.octopi.validation.tagging.PutCheck;

import javax.validation.groups.Default;
import java.util.List;

public class TopicGroupService {

  private GenericDao genericDao = new GenericDao();
  private TopicGroupDao topicGroupDao = new TopicGroupDao();
  
  public List<TopicGroup> listTopicGroups() {
    return this.topicGroupDao.listTopicGroups();
  }
  
  public TopicGroup getTopicGroup(Integer id) {
    TopicGroup topicGroup = this.topicGroupDao.getTopicGroup(id);
    if (topicGroup == null) {
      throw new EntityNotFoundException("Invalid topic group identifier");
    }
    return topicGroup;
  }
  
  public TopicGroup postCreateTopicGroup(TopicGroup topicGroup) {
    ValidationUtil.validateBean(topicGroup, PostCheck.class, Default.class);
    return this.genericDao.persistEntity(topicGroup);
  }

  public void putUpdateTopicGroup(TopicGroup topicGroup, TopicGroup oldTopicGroup) {
    ValidationUtil.validateBean(topicGroup, PutCheck.class, Default.class);
    checkIfUpdatingToExistingEntity(topicGroup, oldTopicGroup);
    this.topicGroupDao.fullUpdateTopicGroup(topicGroup, oldTopicGroup);
  }
  
  private void checkIfUpdatingToExistingEntity(TopicGroup topicGroup, TopicGroup oldTopicGroup) {
    if (!topicGroup.getId().equals(oldTopicGroup.getId())) {
      TopicGroup databaseTopicGroup = this.genericDao
          .getEntityFromId(topicGroup.getId(), TopicGroup.class);
      if (databaseTopicGroup != null) {
        throw new InvalidRequestException("Cannot update with id from another existing entity");
      }
    }
  }

  public TopicGroup retrieveTopicGroup(Integer topicGroupId) {
    return this.genericDao.getEntityFromId(topicGroupId, TopicGroup.class);
  }
  
  public TopicGroup getTopicGroupForCode(String code, String languageCode) {
    TopicGroup topicGroup = this.topicGroupDao.getTopicGroupFromCode(code, languageCode);
    if (topicGroup == null) {
      throw new EntityNotFoundException("Invalid topic group code");
    }
    return topicGroup;
  }
}
