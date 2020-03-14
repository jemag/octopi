/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.exception.EntityNotFoundException;
import ca.ogsl.octopi.models.ClickStrategy;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;

import javax.validation.groups.Default;
import java.util.List;

public class ClickStrategyService {

  private GenericDao genericDao = new GenericDao();

  public List<ClickStrategy> listClickStrategies() {
    return this.genericDao.getAllEntities(ClickStrategy.class);
  }
  
  public ClickStrategy getClickStrategy(Integer id) {
    ClickStrategy clickStrategy = this.genericDao.getEntityFromId(id, ClickStrategy.class);
    if (clickStrategy == null) {
      throw new EntityNotFoundException("Not found");
    }
    return clickStrategy;
  }
  
  public ClickStrategy postCreateClickStrategy(ClickStrategy clickStrategy) {
    ValidationUtil.validateBean(clickStrategy, PostCheck.class, Default.class);
    return this.genericDao.persistEntity(clickStrategy);
  }
}
