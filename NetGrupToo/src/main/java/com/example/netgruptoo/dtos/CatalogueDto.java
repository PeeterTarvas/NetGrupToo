package com.example.netgruptoo.dtos;

import lombok.*;

/**
 * This is a transfer object for Catalogue class.
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
public class CatalogueDto {
    private String username;
    private String catalogueName;
    private Long parent;
    private Long catalogueId;
}
