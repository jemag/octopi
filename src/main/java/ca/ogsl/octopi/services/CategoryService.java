/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.CategoryDao;
import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.exception.EntityNotFoundException;
import ca.ogsl.octopi.exception.InvalidRequestException;
import ca.ogsl.octopi.models.Category;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;
import ca.ogsl.octopi.validation.tagging.PutCheck;

import javax.validation.groups.Default;
import java.util.List;

public class CategoryService {
  
  private CategoryDao categoryDao = new CategoryDao();
  private GenericDao genericDao = new GenericDao();
  
  public List<Category> listCategories() {
    return this.genericDao.getAllEntities(Category.class);
  }
  
  public Category getCategory(Integer id) {
    Category category = this.genericDao.getEntityFromId(id, Category.class);
    if (category == null) {
      throw new EntityNotFoundException("Invalid category identifier");
    }
    return category;
  }
  
  public Category postCreateCategory(Category category) {
    ValidationUtil.validateBean(category, PostCheck.class, Default.class);
    return this.genericDao.persistEntity(category);
  }
  
  public void putUpdateCategory(Category category, Category oldCategory) {
    ValidationUtil.validateBean(category, PutCheck.class, Default.class);
    checkIfUpdatingToExistingEntity(category, oldCategory);
    this.categoryDao.fullUpdateCategory(category, oldCategory);
  }
  
  private void checkIfUpdatingToExistingEntity(Category category, Category oldCategory) {
    if (!category.getId().equals(oldCategory.getId())) {
      Category databaseCategory = this.genericDao.getEntityFromId(category.getId(), Category.class);
      if (databaseCategory != null) {
        throw new InvalidRequestException("Cannot update with id from another existing entity");
      }
    }
  }
  
  public Category retrieveCategory(Integer categoryId) {
    return this.genericDao.getEntityFromId(categoryId, Category.class);
  }
}
