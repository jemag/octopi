/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.CategoryRelationDao;
import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.exception.EntityNotFoundException;
import ca.ogsl.octopi.exception.InvalidRequestException;
import ca.ogsl.octopi.models.CategoryRelation;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;
import ca.ogsl.octopi.validation.tagging.PutCheck;

import javax.validation.groups.Default;
import java.util.List;

public class CategoryRelationService {
  
  private GenericDao genericDao = new GenericDao();
  private CategoryRelationDao categoryRelationDao = new CategoryRelationDao();
  
  public List<CategoryRelation> listCategoryRelations() {
    return this.genericDao.getAllEntities(CategoryRelation.class);
  }
  
  public CategoryRelation getCategoryRelation(Integer id) {
    CategoryRelation categoryRelation = this.genericDao.getEntityFromId(id, CategoryRelation.class);
    if (categoryRelation == null) {
      throw new EntityNotFoundException("Not found");
    }
    return categoryRelation;
  }
  
  public CategoryRelation postCreateCategoryRelation(CategoryRelation categoryRelation) {
    ValidationUtil.validateBean(categoryRelation, PostCheck.class, Default.class);
    return this.genericDao.persistEntity(categoryRelation);
  }
  
  public CategoryRelation retrieveCategoryRelation(Integer categoryRelationId) {
    return this.genericDao.getEntityFromId(categoryRelationId, CategoryRelation.class);
  }
  
  public void putUpdateCategoryRelation(CategoryRelation categoryRelation,
                                        CategoryRelation oldCategoryRelation) {
    ValidationUtil.validateBean(categoryRelation, PutCheck.class, Default.class);
    checkIfUpdatingToExistingEntity(categoryRelation, oldCategoryRelation);
    this.categoryRelationDao.fullUpdateTopic(categoryRelation, oldCategoryRelation);
  }
  
  private void checkIfUpdatingToExistingEntity(CategoryRelation categoryRelation,
                                               CategoryRelation oldCategoryRelation) {
    if (!categoryRelation.getId().equals(oldCategoryRelation.getId())) {
      CategoryRelation databaseTopic = this.genericDao
          .getEntityFromId(categoryRelation.getId(), CategoryRelation.class);
      if (databaseTopic != null) {
        throw new InvalidRequestException("Cannot update with id from another existing entity");
      }
    }
  }
}
