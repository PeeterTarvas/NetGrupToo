package com.example.netgruptoo.payload;


import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
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
