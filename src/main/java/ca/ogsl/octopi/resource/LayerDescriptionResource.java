/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.models.LayerDescription;
import ca.ogsl.octopi.services.LayerDescriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;

@RestController
@RequestMapping("/api")
@Tag(name = "Layer Description", description = "Endpoint to retrieve layer description objects ")
public class LayerDescriptionResource {
  
  private LayerDescriptionService layerDescriptionService = new LayerDescriptionService();
  
  @GetMapping(value = "/layer-descriptions")
  @Operation(summary = "Get all layer descriptions")
  public Collection<LayerDescription> listLayerDescriptions() {
    return this.layerDescriptionService.listLayerDescriptions();
  }
  
  @GetMapping(value = "/layer-descriptions/{id}")
  @Operation(summary = "Find layer description by ID")
  public LayerDescription getLayerDescriptionForId(
      @PathVariable @Parameter(description = "ID of the layer description to be fetched", required = true) Integer id
  ) {
    return this.layerDescriptionService.getLayerDescriptionForId(id);
  }
  
  @RolesAllowed("ADMIN")
  @PostMapping(value = "/layer-descriptions")
  @Operation(summary = "Create a new layer description entry")
  public ResponseEntity<LayerDescription> postCreateLayerDescription(LayerDescription layerDescription) {
    LayerDescription databaseLayerDescription = this.layerDescriptionService
        .postCreateLayerDescription(layerDescription);
    return ResponseEntity.status(201).body(databaseLayerDescription);
  }
}
