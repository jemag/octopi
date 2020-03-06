/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Schema(
    name = "Bing layer",
    description = "A child inheriting from Layer with specialized attributes for Bing maps."
)
@Table(name = "bing_layer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BingLayer extends Layer {

  @Basic
  @Column(name = "imagery_set")
  @Schema(
      description = "Identifies the type of Bing maps",
      example = "Aerial"
  )
  private String imagerySet;

  @Basic
  @Column(name = "key")
  @Schema(
      description = "Key to use the Bing map"
  )
  private String key;

  public String getImagerySet() {
    return imagerySet;
  }

  public void setImagerySet(String imagerySet) {
    this.imagerySet = imagerySet;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

}
