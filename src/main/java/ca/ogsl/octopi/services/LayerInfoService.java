/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.dao.LayerInfoDao;
import ca.ogsl.octopi.exception.EntityNotFoundException;
import ca.ogsl.octopi.exception.InvalidRequestException;
import ca.ogsl.octopi.models.Layer;
import ca.ogsl.octopi.models.LayerInfo;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;

import javax.validation.groups.Default;
import java.util.List;

public class LayerInfoService {

  private GenericDao genericDao = new GenericDao();
  private LayerInfoDao layerInfoDao = new LayerInfoDao();
  
  public LayerInfo getLayerInfoForId(Integer id) {
    LayerInfo layerInfo = this.genericDao.getEntityFromId(id, LayerInfo.class);
    if (layerInfo == null) {
      throw new EntityNotFoundException("Not found");
    }
    return layerInfo;
  }
  
  public LayerInfo postCreateLayerInfo(LayerInfo layerInfo) {
    ValidationUtil.validateBean(layerInfo, PostCheck.class, Default.class);
    Layer targetLayer = this.genericDao.getEntityFromId(layerInfo.getLayerId(),
        Layer.class);
    if (targetLayer == null) {
      throw new InvalidRequestException("Specified layer does not exist. Layer info must target an existing layer");
    }
    return this.genericDao.persistEntity(layerInfo);
  }

  public List<LayerInfo> postCreateMultipleLayerInfos(List<LayerInfo> layerInfos) {
    return this.layerInfoDao.createMultipleLayerInfos(layerInfos);
  }
  
  public void deleteLayerInfoForId(Integer id) {
    this.genericDao.deleteEntityFromId(id, LayerInfo.class);
  }

  public List<LayerInfo> listLayerInfos() {
    return this.genericDao.getAllEntities(LayerInfo.class);
  }
}
