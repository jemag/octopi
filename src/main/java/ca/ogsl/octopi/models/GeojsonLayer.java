/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Schema(
    name = "GeoJSON layer",
    description = "A child inheriting from Layer with specialized attributes for GeoJSON."
)
@Table(name = "geojson_layer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeojsonLayer extends Layer {

}
