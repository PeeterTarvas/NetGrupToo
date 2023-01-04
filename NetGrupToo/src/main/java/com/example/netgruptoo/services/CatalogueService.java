package com.example.netgruptoo.services;

import com.example.netgruptoo.models.Catalogue;
import com.example.netgruptoo.models.CatalogueCatalogueReference;
import com.example.netgruptoo.payload.CatalogueDto;
import com.example.netgruptoo.repositories.CatalogueCatalogueReferenceRepository;
import com.example.netgruptoo.repositories.CatalogueProductReferenceRepository;
import com.example.netgruptoo.repositories.CatalogueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CatalogueService {

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Autowired
    private CatalogueCatalogueReferenceRepository catalogueCatalogueReferenceRepository;


    public void createCatalogueWithCatalogueDto(CatalogueDto catalogueDto) {
        Catalogue catalogue = createCatalogueForUser(catalogueDto.getUsername(), catalogueDto.getCatalogueName());
        catalogueRepository.saveAndFlush(catalogue);
        if (catalogueDto.getParent() != null) {
            CatalogueCatalogueReference catalogueCatalogueReference = createCatalogueCatalogueReference(catalogue, catalogueDto.getParent());
            catalogueCatalogueReferenceRepository.saveAndFlush(catalogueCatalogueReference);
        }
    }
    public List<CatalogueDto> getAllCatalogueDtosByParentId(Long parentId) {
        List<Catalogue> catalogueList = catalogueRepository.findAllByParentId(parentId);
        List<CatalogueDto> catalogueDtoList = new ArrayList<>();
        catalogueList.forEach(element -> catalogueDtoList.add(createCatalogueDtoFromCatalogue(element, parentId)));
        return catalogueDtoList;
    }

    public CatalogueDto getCatalogueByNameAndUser(String user, String catalogueName) {
        Catalogue catalogue = catalogueRepository.findByCatalogue_nameAndCatalogue_owner(catalogueName, user);
        return createCatalogueDtoFromCatalogue(catalogue, null);
    }

    public CatalogueDto initCatalogue(String username) {
        Catalogue catalogue = catalogueRepository.findInitalUserCatalogue(username);
        if (catalogue == null) {
            catalogue = createCatalogueForUser(username, "Home");
            catalogueRepository.saveAndFlush(catalogue);
        }
        return createCatalogueDtoFromCatalogue(catalogue, null);
    }
    private CatalogueDto createCatalogueDtoFromCatalogue(Catalogue catalogue, Long parent) {
        return CatalogueDto.builder()
                .catalogueName(catalogue.getCatalogue_name())
                .username(catalogue.getCatalogue_owner())
                .catalogueId(catalogue.getCatalogue_id())
                .parent(parent)
                .build();
    }

    private Catalogue createCatalogueForUser(String username, String catalogueName) {
        Long newCatalogueId = catalogueRepository.findNextId();
        return Catalogue.builder()
                .catalogue_id(newCatalogueId)
                .catalogue_name(catalogueName)
                .catalogue_owner(username)
                .build();
    }

    private CatalogueCatalogueReference createCatalogueCatalogueReference(Catalogue newCatalogue, Long parent) {
        return CatalogueCatalogueReference.builder()
                .parent_catalogue_id(parent)
                .child_catalogue_id(newCatalogue.getCatalogue_id())
                .build();
    }

}
