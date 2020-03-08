/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.ClickFormatter;
import ca.ogsl.octopi.services.ClickFormatterService;
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
@Tag(name = "Click Formatter", description = "Endpoint to retrieve click formatter objects ")
public class ClickFormatterResource {
  
  private ClickFormatterService clickFormatterService = new ClickFormatterService();
  
  @GetMapping(value = "/click-formatters")
  @Operation(summary = "Retrieve all click formatters")
  public Collection<ClickFormatter> listClickFormatters() throws AppException {
    return this.clickFormatterService.listClickFormatters();
  }
  
  @GetMapping(value = "/click-formatters/{id}")
  @Operation(summary = "Get a click formatter by ID")
  public ClickFormatter getClickFormatterById(
      @PathVariable @Parameter(description = "ID of the Click Formatter to be fetched", required = true) Integer id) throws AppException {
    return this.clickFormatterService.getClickFormatter(id);
  }
  
  @RolesAllowed("ADMIN")
  @PostMapping(value = "/click-formatters")
  @Operation(summary = "Create a new click formatter entry")
  public ResponseEntity<ClickFormatter> postCreateClickFormatter(ClickFormatter clickFormatter)
      throws AppException {
    ClickFormatter cF = this.clickFormatterService.postCreateClickFormatter(clickFormatter);
    return new ResponseEntity<>(cF, HttpStatus.CREATED);
  }
}
