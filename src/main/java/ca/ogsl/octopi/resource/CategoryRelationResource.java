/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.exception.InvalidRequestException;
import ca.ogsl.octopi.models.CategoryRelation;
import ca.ogsl.octopi.services.CategoryRelationService;
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
@Tag(name = "Category Relation", description = "Endpoint to retrieve category relations objects ")
public class CategoryRelationResource {
  
  private CategoryRelationService categoryRelationService = new CategoryRelationService();
  
  @GetMapping(value = "/category-relations")
  @Operation(summary = "Get a list of all category relations")
  public Collection<CategoryRelation> listCategories() {
    return this.categoryRelationService.listCategoryRelations();
  }
  
  @GetMapping(value = "/category-relations/{id}")
  @Operation(summary = "Get category relation by ID")
  public CategoryRelation getCategoryRelation(
      @PathVariable @Parameter(description = "ID of category relation to be fetched", required = true) Integer id
  ) {
    return this.categoryRelationService.getCategoryRelation(id);
  }
  
  @RolesAllowed("ADMIN")
  @PostMapping(value = "/category-relations")
  @Operation(summary = "Create a new category relation entry")
  public ResponseEntity<CategoryRelation> postCreateCategoryRelation(CategoryRelation categoryRelation) {
    CategoryRelation cR = this.categoryRelationService
        .postCreateCategoryRelation(categoryRelation);
    return new ResponseEntity<>(cR, HttpStatus.CREATED);
  }
  
  @RolesAllowed("ADMIN")
  @PutMapping(value = "/category-relations/{categoryRelationId}")
  @Operation(summary = "Update a category relation entry")
  public ResponseEntity putUpdateCategoryRelation(@PathVariable @Parameter Integer categoryRelationId,
                                                  CategoryRelation categoryRelation) {
    CategoryRelation oldCategoryRelation = this.categoryRelationService
        .retrieveCategoryRelation(categoryRelationId);
    if (oldCategoryRelation == null) {
      throw new InvalidRequestException("Use post to create entity");
    } else {
      this.categoryRelationService.putUpdateCategoryRelation(categoryRelation, oldCategoryRelation);
      return ResponseEntity.status(200).build();
    }
  }
}
