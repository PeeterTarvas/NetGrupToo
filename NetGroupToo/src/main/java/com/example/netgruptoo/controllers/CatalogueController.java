package com.example.netgruptoo.controllers;


import com.example.netgruptoo.dtos.CatalogueDto;
import com.example.netgruptoo.services.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * This class holds all the endpoints of the back-end service
 * that have mainly catalogue related functionalities related to them.
 * The requests to this controller are need to be made like so http://localhost:8000/catalogue/..function param
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/catalogue")
public class CatalogueController {

    /**
     * Service that handles all the logic related to catalogue.
     */
    @Autowired
    private CatalogueService catalogueService;


    /**
     * This method is the endpoint method for creating new catalogues,
     * catalogueDto has the parameters from which the catalogue is created.
     * User will input the params of the catalogueDto in the front-end, and then it will be sent here
     * @param catalogueDto catalogueDto from which catalogue will be created
     * @return HttpStatus.Created if creating catalogue was successful
     */
    @PostMapping(value = "/create")
    public HttpStatus createCatalogue(@RequestBody CatalogueDto catalogueDto) {
        catalogueService.createCatalogueWithCatalogueDto(catalogueDto);
        return HttpStatus.CREATED;
    }


    /**
     * This is endpoint method for getting the root catalogue of the user.
     * If the user doesn't have an initial catalogue then this function will create it in catalogueService class.
     * Each person needs to have an initial catalogue because without it she/he cannot create other directories or products.
     * @param username of the User that requests its initial catalogue.
     * @return CatalogueDto of the catalogue that was created/initialized.
     */
    @GetMapping("/init")
    public CatalogueDto getInitialCatalogue(@RequestParam("username") String username) {
        return catalogueService.initCatalogue(username);
    }

    /**
     * This is endpoint method that returns all catalogues under a parent catalogue that holds those catalogues.
     * @param parentId of the catalogue whose children catalogue we want to access.
     * @return all the catalogues under the parent catalogue.
     */
    @GetMapping("/get/{parentId}/catalogues")
    public List<CatalogueDto> getCataloguesUnderParent(@PathVariable Long parentId) {
        return catalogueService.getAllCatalogueDtosByParentId(parentId);
    }

    /**
     * This is endpoint method for getting a catalogue by its name.
     * @param username of the user to whom the catalogue belongs to.
     * @param catalogueName of the catalogue that we want.
     * @return CatalogueDto of the catalogue that was requested.
     */
    @GetMapping("/get")
    public CatalogueDto getCatalogue(@RequestParam("username") String username,
                                     @RequestParam("catalogueName") String catalogueName) {
        return catalogueService.getCatalogueByNameAndUser(username, catalogueName);
    }


}
