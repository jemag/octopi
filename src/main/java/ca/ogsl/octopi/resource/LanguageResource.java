/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.Language;
import ca.ogsl.octopi.services.LanguageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api")
@Tag(name = "Language", description = "Endpoint to retrieve language objects ")
public class LanguageResource {
  
  private LanguageService languageService = new LanguageService();
  
  @GetMapping(value = "/languages")
  @Operation(summary = "Get all languages")
  public Collection<Language> listLanguages() throws AppException {
    return this.languageService.listLanguages();
  }
  
  @GetMapping(value = "/languages/{code}")
  @Operation(summary = "Find language by code")
  public Language getLanguageForId(
      @PathVariable @Parameter(description = "Code of language to be fetched", required = true) String code
  )
      throws AppException {
    return this.languageService.getLanguage(code);
  }
  
}
