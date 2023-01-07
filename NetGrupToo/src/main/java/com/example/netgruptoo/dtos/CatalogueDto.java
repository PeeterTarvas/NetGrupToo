package com.example.netgruptoo.dtos;

import lombok.*;

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
