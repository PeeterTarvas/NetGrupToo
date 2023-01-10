package com.example.netgruptoo.services;

import com.example.netgruptoo.dbos.Catalogue;
import com.example.netgruptoo.dbos.CatalogueCatalogueReference;
import com.example.netgruptoo.dtos.CatalogueDto;
import com.example.netgruptoo.repositories.CatalogueCatalogueReferenceRepository;
import com.example.netgruptoo.repositories.CatalogueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a service class for all functionalities related to Catalogue class.
 */
@Service
public class CatalogueService {

    /**
     * Database repository where Catalogues are.
     */
    @Autowired
    private CatalogueRepository catalogueRepository;

    /**
     * Repository where the Catalogue child parent relations are stored in.
     */
    @Autowired
    private CatalogueCatalogueReferenceRepository catalogueCatalogueReferenceRepository;

    /**
     * This function is for creating a catalogue from catalogueDto,
     * this method also creates the reference from the catalogue to it's parent in the database if it is not a root catalogue.
     * @param catalogueDto from which the catalogue will be created from.
     */
    public void createCatalogueWithCatalogueDto(CatalogueDto catalogueDto) {
        Catalogue catalogue = createCatalogueForUser(catalogueDto.getUsername(), catalogueDto.getCatalogueName());
        catalogueRepository.saveAndFlush(catalogue);
        if (catalogueDto.getParent() != null) {
            CatalogueCatalogueReference catalogueCatalogueReference = createCatalogueCatalogueReference(catalogue, catalogueDto.getParent());
            catalogueCatalogueReferenceRepository.saveAndFlush(catalogueCatalogueReference);
        }
    }

    /**
     * This method is for getting all the catalogues under their parent catalogue.
     * @param parentId of the parent catalogue whose child catalogues we want.
     * @return all the catalogues who are under this parent catalogue whose id is the parentId.
     */
    public List<CatalogueDto> getAllCatalogueDtosByParentId(Long parentId) {
        List<Catalogue> catalogueList = catalogueRepository.findAllByParentId(parentId);
        List<CatalogueDto> catalogueDtoList = new ArrayList<>();
        catalogueList.forEach(element -> catalogueDtoList.add(createCatalogueDtoFromCatalogue(element, parentId)));
        return catalogueDtoList;
    }

    /**
     * This method is for getting a catalogue for the user by the catalogue name.
     * @param user's username.
     * @param catalogueName of the catalogue we want to get.
     * @return the catalogue of the user with that name.
     */
    public CatalogueDto getCatalogueByNameAndUser(String user, String catalogueName) {
        Catalogue catalogue = catalogueRepository.findByCatalogue_nameAndCatalogue_owner(catalogueName, user);
        return createCatalogueDtoFromCatalogue(catalogue, catalogueCatalogueReferenceRepository.findParentId(catalogue.getCatalogue_id()));
    }

    /**
     * This method is for initializing the users first catalogue,
     * if the user doesn't have an initial catalogue then it will be created for her/him.
     * @param username whose initial catalogue we want to get.
     * @return the users initial catalogue as CatalogueDto
     */
    public CatalogueDto initCatalogue(String username) {
        Catalogue catalogue = catalogueRepository.findInitalUserCatalogue(username);
        if (catalogue == null) {
            catalogue = createCatalogueForUser(username, "Home");
            catalogueRepository.saveAndFlush(catalogue);
        }
        return createCatalogueDtoFromCatalogue(catalogue, null);
    }

    /**
     * This is a helper method for creating a CatalogueDto from a catalogue, and it's parent.
     * @param catalogue from which the CatalogueDto will be created from.
     * @param parent param that will be passed to the catalogueDto.
     * @return the created CatalogueDto.
     */
    private CatalogueDto createCatalogueDtoFromCatalogue(Catalogue catalogue, Long parent) {
        return CatalogueDto.builder()
                .catalogueName(catalogue.getCatalogue_name())
                .username(catalogue.getCatalogue_owner())
                .catalogueId(catalogue.getCatalogue_id())
                .parent(parent)
                .build();
    }

    /**
     * This method is a helper method for creating a catalogue for a user.
     * @param username of the user that we will create the catalogue for.
     * @param catalogueName of the catalogue that will be created.
     * @return the created Catalogue.
     */
    private Catalogue createCatalogueForUser(String username, String catalogueName) {
        Long newCatalogueId = catalogueRepository.findNextId();
        return Catalogue.builder()
                .catalogue_id(newCatalogueId)
                .catalogue_name(catalogueName)
                .catalogue_owner(username)
                .build();
    }

    /**
     * This is a helper method for creating a reference between two catalogues,
     * mainly the parent, and it's child catalogue.
     * @param newCatalogue the catalogue who is the child element ot the reference.
     * @param parent the parent catalogue/
     * @return a new reference between two catalogues.
     */
    private CatalogueCatalogueReference createCatalogueCatalogueReference(Catalogue newCatalogue, Long parent) {
        return CatalogueCatalogueReference.builder()
                .parent_catalogue_id(parent)
                .child_catalogue_id(newCatalogue.getCatalogue_id())
                .build();
    }

}
