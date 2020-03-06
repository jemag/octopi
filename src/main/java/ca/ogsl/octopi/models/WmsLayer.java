/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Schema(
    name = "WMS layer",
    description = "A child inheriting from Layer with specialized attributes for WMS."
)
@Table(name = "wms_layer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WmsLayer extends Layer {

  @Basic
  @Column(name = "identifier")
  @NotBlank
  @Schema(
      required = true,
      description = "A layer identifier to be included when querying the WMS URL",
      example = "EMODnetGeology:tsunami_pt_250k"
  )
  private String identifier;

  @Basic
  @Column(name = "format")
  @Schema(
      description = "A format string to be included when querying the WMS URL.",
      example = "image/png"
  )
  private String format;

  @Basic
  @Column(name = "named_style")
  @Schema(
      description = "A named style to be included when querying the WMS URL."
  )
  private String namedStyle;

  @Basic
  @Column(name = "crs")
  @Schema(
      description = "The name of the CRS which should be used for the layer.",
      example = "EPSG:4326"
  )
  private String crs;

  @Basic
  @Column(name = "version")
  @Schema(
      description = "The version number to be included when querying the WMS URL."
  )
  private String version;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getNamedStyle() {
    return namedStyle;
  }

  public void setNamedStyle(String namedStyle) {
    this.namedStyle = namedStyle;
  }

  public String getCrs() {
    return crs;
  }

  public void setCrs(String crs) {
    this.crs = crs;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WmsLayer wmsLayer = (WmsLayer) o;
    return Objects.equals(identifier, wmsLayer.identifier) &&
        Objects.equals(format, wmsLayer.format) &&
        Objects.equals(namedStyle, wmsLayer.namedStyle) &&
        Objects.equals(crs, wmsLayer.crs) &&
        Objects.equals(version, wmsLayer.version);
  }

  @Override
  public int hashCode() {

    return Objects.hash(identifier, format, namedStyle, crs, version);
  }
}
