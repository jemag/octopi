/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.exception.InvalidRequestException;
import ca.ogsl.octopi.models.Category;
import ca.ogsl.octopi.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;

@RestController
@RequestMapping("/api")
@Tag(name = "Category", description = "Endpoint to retrieve category objects ")
public class CategoryResource {
  
  private CategoryService categoryService = new CategoryService();
  
  @GetMapping(value = "/categories")
  @Operation(summary = "Get a list of all categories")
  public Collection<Category> listCategories() {
    return this.categoryService.listCategories();
  }
  
  @GetMapping(value = "/categories/{id}")
  @Operation(summary = "Get a category by ID")
  public Category getCategory(
      @PathVariable @Parameter(description = "ID of category to be fetched", required = true) Integer id
  ) {
    return this.categoryService.getCategory(id);
  }
  
  @RolesAllowed("ADMIN")
  @PostMapping(value = "/categories")
  @Operation(summary = "Create a new category entry")
  public ResponseEntity<Category> postCreateCategory(Category category) {
    Category c = this.categoryService.postCreateCategory(category);
    return new ResponseEntity<>(c, HttpStatus.CREATED);
  }
  
  @RolesAllowed("ADMIN")
  @PutMapping(value = "/categories/{id}")
  @Operation(summary = "Update a category entry")
  public ResponseEntity putUpdateCategory(@PathVariable @Parameter Integer categoryId, Category category) {
    Category oldCategory = this.categoryService.retrieveCategory(categoryId);
    if (oldCategory == null) {
      throw new InvalidRequestException("Use post to create entity");
    } else {
      this.categoryService.putUpdateCategory(category, oldCategory);
      return ResponseEntity.status(200).build();
    }
  }
  
}
