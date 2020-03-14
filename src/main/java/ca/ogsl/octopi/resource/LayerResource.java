/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.models.*;
import ca.ogsl.octopi.services.LayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;

@RestController
@RequestMapping("/api")
@Tag(name = "layers", description = "Endpoint to retrieve layer objects ")
public class LayerResource {
  
  private LayerService layerService = new LayerService();
  
  @GetMapping("/layers")
  @Operation(summary = "Returns a list objects with the base type: Layer. Each layer will be a child type(such as " +
      "WMSLayer or WFSLayer)")
//    @Cacheable(value = "stations", condition = "#stationCode == null")
  public Collection<Layer> listLayers() {
    return this.layerService.listLayers();
  }
  
  @GetMapping("/layers/{id}")
  @Operation(summary = "Find a layer by ID")
  public Layer getLayerForId(
      @PathVariable @Parameter(description = "ID of layer to be fetched", required = true) Integer id
  ) {
    return this.layerService.getLayerForId(id);
  }
  
  @GetMapping("/layers/getLayerForCode")
  @Operation(summary = "Find a layer by code and language code")
  public Layer getLayerForCode(
      @Parameter(description = "Code of layer to be fetched", required = true) @RequestParam("code") String code,
      @Parameter(description = "Code of the language needed", required = true)
      @RequestParam("language-code") String languageCode) {
    return this.layerService.getLayerForCode(code, languageCode);
  }
  
  @PostMapping("layers")
  @Operation(summary = "Create a new layer entry")
  @RolesAllowed("ADMIN")
  public ResponseEntity<Layer> postCreateLayer(Layer layer) {
    Layer l = this.layerService.postCreateLayer(layer);
    return new ResponseEntity<>(l, HttpStatus.CREATED);
  }
  
  @GetMapping(value = "/layers/{layerId}/getLayerInformation", produces = MediaType.TEXT_HTML_VALUE)
  @Operation(summary = "Get an HTML segment for displaying the information about a layer based on ID")
  public String getLayerInformation(
      @PathVariable @Parameter(description = "ID of layer to be fetched", required = true) Integer layerId
  ) {
    return this.layerService.getLayerInformation(layerId);
  }
  
  @GetMapping(value = "/layers/{layerId}/client-presentations")
  @Operation(summary = "Gets all of the client presentations associated with a given layer")
  public Collection<ClientPresentation> listClientPresentations(
      @PathVariable @Parameter(description = "ID of layer to be fetched", required = true) Integer layerId
  ) {
    return this.layerService.listClientPresentations(layerId);
  }
  
  @GetMapping(value = "/layers/{layerId}/layer-descriptions")
  @Operation(summary = "Gets the description for a specific layer based on layer ID")
  public LayerDescription getLayerDescription(
      @PathVariable @Parameter(description = "ID of the layer", required = true) Integer layerId
  ) {
    return this.layerService.getLayerDescription(layerId);
  }
  
  @GetMapping(value = "/layers/{layerId}/layer-info")
  @Operation(summary = "Gets the a collection of layerInfo entries for a layer based on layer ID")
  public Collection<LayerInfo> getLayerInfo(
      @PathVariable @Parameter(description = "ID of the layer") Integer layerId
  ) {
    return this.layerService.getLayerInfo(layerId);
  }
  
  @GetMapping(value = "/layers/{layerId}/click-strategies")
  @Operation(summary = "Gets the Click Strategy for a specific layer based on layer ID")
  public ClickStrategy getClickStrategy(
      @PathVariable @Parameter(description = "ID of the layer", required = true) Integer layerId
  ) {
    return this.layerService.getClickStrategy(layerId);
  }
  
  @GetMapping(value = "/layers/{layerId}/click-formatters")
  @Operation(summary = "Gets the Click Formatter for a specific layer based on layer ID")
  public ClickFormatter getClickFormatter(
      @PathVariable @Parameter(description = "ID of the layer", required = true) Integer layerId
  ) {
    return this.layerService.getClickFormatter(layerId);
  }
}
