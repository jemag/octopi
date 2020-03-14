/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.models.ClickStrategy;
import ca.ogsl.octopi.services.ClickStrategyService;
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
@Tag(name = "Click Strategy", description = "Endpoint to retrieve click strategy objects ")
public class ClickStrategyResource {
  
  private ClickStrategyService clickStrategyService = new ClickStrategyService();
  
  @GetMapping(value = "/click-strategies")
  @Operation(summary = "Get all click strategies")
  public Collection<ClickStrategy> listClickStrategies() {
    return this.clickStrategyService.listClickStrategies();
  }
  
  @GetMapping(value = "/click-strategies/{id}")
  @Operation(summary = "Find click strategy by ID")
  public ClickStrategy getClickStrategyById(
      @PathVariable @Parameter(description = "ID of the Click Strategy to be fetched", required = true)
          Integer id) {
    return this.clickStrategyService.getClickStrategy(id);
  }
  
  @RolesAllowed("ADMIN")
  @PostMapping(value = "/click-strategies")
  @Operation(summary = "Create a new click strategy entry")
  public ResponseEntity<ClickStrategy> postCreateClickStrategy(ClickStrategy clickStrategy) {
    ClickStrategy cS = this.clickStrategyService.postCreateClickStrategy(clickStrategy);
    return new ResponseEntity<>(cS, HttpStatus.CREATED);
  }
  
}
