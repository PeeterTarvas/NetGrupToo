package com.example.netgruptoo.controllers;


import com.example.netgruptoo.payload.CatalogueDto;
import com.example.netgruptoo.services.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/catalogue")
public class CatalogueController {

    @Autowired
    private CatalogueService catalogueService;


    @PostMapping(value = "/create")
    public HttpStatus createCatalogue(@RequestBody CatalogueDto catalogueDto) {
        catalogueService.createCatalogueWithCatalogueDto(catalogueDto);
        return HttpStatus.CREATED;
    }


    @GetMapping("/init")
    public CatalogueDto getInitialCatalogue(@RequestParam("username") String username) {
        return catalogueService.initCatalogue(username);
    }

    @GetMapping("/get/{parentId}/catalogues")
    public List<CatalogueDto> getCataloguesUnderParent(@PathVariable Long parentId) {
        return catalogueService.getAllCatalogueDtosByParentId(parentId);
    }

    @GetMapping("/get")
    public CatalogueDto getCatalogue(@RequestParam("username") String username,  @RequestParam("catalogueName") String catalogueName) {
        return catalogueService.getCatalogueByNameAndUser(username, catalogueName);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HttpMessageNotReadableException e) {
        System.out.println(e.getMessage());
    }

}
