/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.models;

import ca.ogsl.octopi.validation.tagging.PostCheck;
import ca.ogsl.octopi.validation.tagging.PutCheck;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "category_child")
@Schema(name = "Category Relation", description = "Relation between a parent and a child category")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryRelation {

  @Id
  @GenericGenerator(name = "IdOrGenerate", strategy = "ca.ogsl.octopi.dao.IdOrGenerateGenerator")
  @GeneratedValue(generator = "IdOrGenerate")
  @Column(name = "id")
  @Null(groups = PostCheck.class)
  @NotNull(groups = PutCheck.class)
  @Schema(required = true)
  private Integer id;

  @NotNull
  @Column(name = "parent_id")
  @Schema(
      required = true,
      description = "Identifier of the parent category"
  )
  private Integer parentId;

  @NotNull
  @Column(name = "child_id")
  @Schema(
      required = true,
      description = "Identifier of the child category"
  )
  private Integer childId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public Integer getChildId() {
    return childId;
  }

  public void setChildId(Integer childId) {
    this.childId = childId;
  }
}
