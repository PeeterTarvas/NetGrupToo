package com.example.netgruptoo.payload;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogueDto {
    private String username;
    private String catalogueName;
    private Long parent;
    private Long catalogueId;
}
