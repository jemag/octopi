/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.models;

import ca.ogsl.octopi.validation.tagging.PostCheck;
import ca.ogsl.octopi.validation.tagging.PutCheck;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "client_presentation")
@Schema(
    name = "Client Presentation",
    description = "The information necessary for presenting a map layer to a user in a particular"
        + " way. Includes styling information and legend information"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientPresentation {

  @Id
  @GenericGenerator(name = "IdOrGenerate", strategy = "ca.ogsl.octopi.dao.IdOrGenerateGenerator")
  @GeneratedValue(generator = "IdOrGenerate")
  @Column(name = "id")
  @Null(groups = PostCheck.class)
  @NotNull(groups = PutCheck.class)
  @Schema(
      required = true
  )
  private Integer id;

  @Basic
  @Column(name = "style_def")
  @Schema(
      description = "String representation of a JSON collection storing information about how to style a layer"
  )
  @JsonRawValue
  private String styleDef;

  @Basic
  @Column(name = "legend_url")
  @URL
  @Schema(
      description = "The URL of an image of the legend for the current presentation"
  )
  private String legendUrl;

  @Basic
  @Column(name = "legend_label")
  @SafeHtml
  @Length(max = 75)
  @Schema(
      description = "A label to accompany the legend_url"
  )
  private String legendLabel;

  @Basic
  @Column(name = "layer_id")
  @NotNull
  @Schema(
      description = "The ID of the layer for this style",
      required = true
  )
  private Integer layerId;
  @Basic
  @Column(name = "is_default")
  @Schema(
      description = "Specifies whether or not this client presentation is the default one. "
          + "Only one client presentation may be the default one"
  )
  private Boolean isDefault;
  @Basic
  @Column(name = "named_style")
  @Schema(
      description = "A named style to be included when queryying a WMS URL"
  )
  private String namedStyle;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getStyleDef() {
    return styleDef;
  }

  public void setStyleDef(String styleDef) {
    this.styleDef = styleDef;
  }

  public String getLegendUrl() {
    return legendUrl;
  }

  public void setLegendUrl(String legendUrl) {
    this.legendUrl = legendUrl;
  }

  public String getLegendLabel() {
    return legendLabel;
  }

  public void setLegendLabel(String legendLabel) {
    this.legendLabel = legendLabel;
  }

  public Integer getLayerId() {
    return layerId;
  }

  public void setLayerId(Integer layerId) {
    this.layerId = layerId;
  }

  @JsonProperty(value = "isDefault")
  public Boolean isDefault() {
    return isDefault;
  }

  @JsonProperty(value = "isDefault")
  public void setDefault(Boolean aDefault) {
    isDefault = aDefault;
  }

  public String getNamedStyle() {
    return namedStyle;
  }

  public void setNamedStyle(String namedStyle) {
    this.namedStyle = namedStyle;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientPresentation that = (ClientPresentation) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(styleDef, that.styleDef) &&
        Objects.equals(legendUrl, that.legendUrl) &&
        Objects.equals(isDefault, that.isDefault) &&
        Objects.equals(namedStyle, that.namedStyle) &&
        Objects.equals(legendLabel, that.legendLabel);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, styleDef, legendUrl, legendLabel, isDefault, namedStyle);
  }
}
