/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.LayerInfo;
import ca.ogsl.octopi.services.LayerInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;

@RestController
@RequestMapping("/api")
@Tag(name = "Layer Info", description = "Endpoint to retrieve layer info objects ")
public class LayerInfoResource {
  
  private LayerInfoService layerInfoService = new LayerInfoService();
  
  @GetMapping(value = "/layer-info")
  @Operation(summary = "Get all layer information entries")
  public Collection<LayerInfo> listLayerInfo() {
    return this.layerInfoService.listLayerInfos();
  }
  
  @GetMapping(value = "/layer-info/{id}")
  @Operation(summary = "Find layer info by ID")
  public LayerInfo getLayerInfoForId(
      @PathVariable @Parameter(description = "ID of the layer info to be fetched", required = true) Integer id
  ) throws AppException {
    return this.layerInfoService.getLayerInfoForId(id);
  }
  
  @RolesAllowed("ADMIN")
  @PostMapping(value = "/layer-info/{id}")
  @Operation(summary = "Create a new layer info entry")
  public ResponseEntity<LayerInfo> postCreateLayerInfo(LayerInfo layerInfo)
      throws AppException {
    LayerInfo databaseLayerInfo = this.layerInfoService.postCreateLayerInfo(layerInfo);
    return ResponseEntity.status(201).body(databaseLayerInfo);
  }
}
