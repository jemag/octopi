/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.exception.EntityNotFoundException;
import ca.ogsl.octopi.models.ClickFormatter;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;

import javax.validation.groups.Default;
import java.util.List;

public class ClickFormatterService {
  
  private GenericDao genericDao = new GenericDao();
  
  public List<ClickFormatter> listClickFormatters() {
    return this.genericDao.getAllEntities(ClickFormatter.class);
  }
  
  public ClickFormatter getClickFormatter(Integer id) {
    ClickFormatter clickFormatter = this.genericDao.getEntityFromId(id, ClickFormatter.class);
    if (clickFormatter == null) {
      throw new EntityNotFoundException("Not found");
    }
    return clickFormatter;
  }
  
  public ClickFormatter postCreateClickFormatter(ClickFormatter clickFormatter) {
    ValidationUtil.validateBean(clickFormatter, PostCheck.class, Default.class);
    return this.genericDao.persistEntity(clickFormatter);
    
  }
}
