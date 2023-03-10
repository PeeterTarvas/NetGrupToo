package com.example.netgruptoo.dtos;


import lombok.*;

/**
 * This is a transfer object for Product class.
 */
@Builder
@Setter
@Getter
@AllArgsConstructor
public class ProductDto {
    private String name;
    private String serialNumber;
    private String pictureLink;
    private String productOwner;
    private String condition;
    private String description;
    private Long amount;

}
