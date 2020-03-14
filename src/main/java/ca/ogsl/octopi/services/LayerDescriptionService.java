/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.dao.LayerDescriptionDao;
import ca.ogsl.octopi.exception.EntityNotFoundException;
import ca.ogsl.octopi.exception.InvalidRequestException;
import ca.ogsl.octopi.models.LayerDescription;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;

import javax.validation.groups.Default;
import java.util.List;

public class LayerDescriptionService {
  
  private GenericDao genericDao = new GenericDao();
  private LayerDescriptionDao layerDescriptionDao = new LayerDescriptionDao();
  
  public List<LayerDescription> listLayerDescriptions() {
    return this.genericDao.getAllEntities(LayerDescription.class);
  }
  
  public LayerDescription getLayerDescriptionForId(Integer id) {
    LayerDescription layerDescription = this.genericDao.getEntityFromId(id, LayerDescription.class);
    if (layerDescription == null) {
      throw new EntityNotFoundException("Not found");
    }
    return layerDescription;
  }
  
  public void deleteLayerDescriptionForId(Integer id, String role) {
    this.genericDao.deleteEntityFromId(id, LayerDescription.class);
  }
  
  public LayerDescription postCreateLayerDescription(LayerDescription layerDescription) {
    ValidationUtil.validateBean(layerDescription, PostCheck.class, Default.class);
    checkIfLayerDescriptionExist(layerDescription);
    return this.genericDao.persistEntity(layerDescription);
  }
  
  private void checkIfLayerDescriptionExist(LayerDescription layerDescription) {
    LayerDescription currLayerDescription = this.layerDescriptionDao.getLayerDescriptionForLayerId(
        layerDescription.getLayerId());
    if (currLayerDescription != null) {
      throw new InvalidRequestException("Duplicate layerId. One layer cannot have multiple descriptions.");
    }
  }
}
