/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.models.ClientPresentation;
import ca.ogsl.octopi.services.ClientPresentationService;
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
@Tag(name = "Client Presentation", description = "Endpoint to retrieve client presentation objects ")
public class ClientPresentationResource {
  
  private ClientPresentationService clientPresentationService = new ClientPresentationService();
  
  @GetMapping(value = "/client-presentations")
  @Operation(summary = "Get all client presentations")
  public Collection<ClientPresentation> listClientPresentations() {
    return this.clientPresentationService.listClientPresentations();
  }
  
  @GetMapping(value = "/client-presentations/{id}")
  @Operation(summary = "Find client presentation by ID")
  public ClientPresentation getClientPresentationForId(
      @PathVariable @Parameter(description = "ID of the client presentation to be fetched",
          required = true) Integer id) {
    return this.clientPresentationService.getClientPresentationForId(id);
  }
  
  @RolesAllowed("ADMIN")
  @PostMapping(value = "/client-presentations")
  @Operation(summary = "Create a new client presentation entry")
  public ResponseEntity<ClientPresentation> postCreateClientPresentation(ClientPresentation clientPresentation) {
    ClientPresentation databaseCP = this.clientPresentationService.
        postCreateClientPresentation(clientPresentation);
    return new ResponseEntity<>(databaseCP, HttpStatus.CREATED);
  }
}
