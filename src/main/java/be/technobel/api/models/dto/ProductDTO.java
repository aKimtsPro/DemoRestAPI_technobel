package be.technobel.api.models.dto;

import be.technobel.api.models.entity.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {

    private Long id;
    private String brand;
    private String model;
    private double price;

    public static ProductDTO toDto(Product entity){

        if( entity == null )
            return null;

        return ProductDTO.builder()
                .id(entity.getId())
                .brand(entity.getBrand())
                .model(entity.getModel())
                .price(entity.getPrice())
                .build();

    }

}
