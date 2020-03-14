/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.exception.EntityNotFoundException;
import ca.ogsl.octopi.models.Language;

import java.util.List;

public class LanguageService {
  
  private GenericDao genericDao = new GenericDao();
  
  public List<Language> listLanguages() {
    return this.genericDao.getAllEntities(Language.class);
  }
  
  public Language getLanguage(String code) {
    Language language = this.genericDao.getEntityFromCode(code, Language.class);
    if (language == null) {
      throw new EntityNotFoundException("Not found");
    }
    return language;
  }
}
