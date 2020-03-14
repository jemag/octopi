/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.*;
import ca.ogsl.octopi.exception.EntityNotFoundException;
import ca.ogsl.octopi.models.*;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;

import javax.validation.groups.Default;
import java.util.List;

/**
 * Created by desjardisna on 2017-02-28.
 */
public class LayerService {
  
  private final LayerInfoDao layerInfoDao = new LayerInfoDao();
  private GenericDao genericDao = new GenericDao();
  private LayerDao layerDao = new LayerDao();
  private ClientPresentationDao clientPresentationDao = new ClientPresentationDao();
  private LayerDescriptionDao layerDescriptionDao = new LayerDescriptionDao();
  
  public List<Layer> listLayers() {
    return this.genericDao.getAllEntities(Layer.class);
  }
  
  public Layer postCreateLayer(Layer layer) {
    ValidationUtil.validateBean(layer, PostCheck.class, Default.class);
    return this.genericDao.persistEntity(layer);
  }
  
  public Layer getLayerForId(Integer id) {
    Layer layer = this.genericDao.getEntityFromId(id, Layer.class);
    if (layer == null) {
      throw new EntityNotFoundException("Not found");
    }
    return layer;
  }
  
  public Layer getlayerForCode(String code) {
    return this.genericDao.getEntityFromCode(code, Layer.class);
  }
  
  public String getLayerInformation(Integer layerId) {
    LayerDescription layerDescription;
    List<LayerInfo> layerInfos;
    layerDescription = this.layerDescriptionDao.getLayerDescriptionForLayerId(layerId);
    String layerTitle = this.layerDao.getLayerTitle(layerId);
    if (layerDescription == null) {
      throw new EntityNotFoundException("No layer information for given layer");
    }
    layerInfos = layerInfoDao.getLayerInfosForLayerIdOrdered(layerId);
    return this.buildLayerInformationHtml(layerDescription, layerTitle, layerInfos);
  }
  
  private String buildLayerInformationHtml(LayerDescription layerDescription, String title,
                                           List<LayerInfo> layerInfos) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<div class='layerInfo'>");
    stringBuilder.append("<h4 class='layerDescriptionTitle'>");
    stringBuilder.append(title).append("</h4>");
    stringBuilder.append("<div class='layerDescription'>");
    stringBuilder.append(layerDescription.getDescription());
    stringBuilder.append("</div>");
    stringBuilder.append(
        "<div class='layerInformationsContainer'><h5 class='layerInformationsTitle'>Informations</h5>");
    stringBuilder.append("<table class='layerInformations'><tbody>");
    for (LayerInfo layerInfo : layerInfos) {
      stringBuilder.append("<tr class='layerInformation'><td class='layerInformationTdLeft'>")
          .append(layerInfo.getLabel()).append("</td><td class='layerInformationTdRight'>");
      if (layerInfo.getUrl() != null && !layerInfo.getUrl().equals("")) {
        stringBuilder.append("<a class='layerInformationLink' target='_blank' href='")
            .append(layerInfo.getUrl()).append("'>")
            .append(layerInfo.getValue()).append("</a></td>");
      } else {
        stringBuilder.append(layerInfo.getValue()).append("</td>");
      }
      stringBuilder.append("</tr>");
    }
    stringBuilder.append("</tbody></table>");
    stringBuilder.append("</div>");
    stringBuilder.append("</div>");
    return stringBuilder.toString();
  }
  
  public List<ClientPresentation> listClientPresentations(int layerId) {
    return this.clientPresentationDao.listClientPresentations(layerId);
  }
  
  public LayerDescription getLayerDescription(Integer layerId) {
    LayerDescription layerDescription = this.layerDescriptionDao
        .getLayerDescriptionForLayerId(layerId);
    if (layerDescription == null) {
      throw new EntityNotFoundException("Not found");
    }
    return layerDescription;
  }
  
  public List<LayerInfo> getLayerInfo(Integer layerId) {
    return this.layerInfoDao.getLayerInfosForLayerIdOrdered(layerId);
  }
  
  public Layer getLayerForCode(String code, String languageCode) {
    Layer layer = this.layerDao.getLayerFromCode(code, languageCode);
    if (layer == null) {
      throw new EntityNotFoundException("Not found");
    }
    return layer;
  }
  
  public ClickStrategy getClickStrategy(Integer layerId) {
    Layer layer = this.genericDao.getEntityFromId(layerId, Layer.class);
    if (layer == null) {
      throw new EntityNotFoundException("Not found");
    }
    ClickStrategy clickStrategy = this.genericDao
        .getEntityFromId(layer.getClickStrategyId(), ClickStrategy.class);
    if (clickStrategy == null) {
      throw new EntityNotFoundException("Not found");
    }
    return clickStrategy;
  }
  
  public ClickFormatter getClickFormatter(Integer layerId) {
    Layer layer = this.genericDao.getEntityFromId(layerId, Layer.class);
    if (layer == null) {
      throw new EntityNotFoundException("Not found");
    }
    ClickFormatter clickFormatter = this.genericDao
        .getEntityFromId(layer.getClickFormatterId(), ClickFormatter.class);
    if (clickFormatter == null) {
      throw new EntityNotFoundException("Not found");
    }
    return clickFormatter;
  }
}
