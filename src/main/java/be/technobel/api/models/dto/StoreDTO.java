package be.technobel.api.models.dto;

import be.technobel.api.models.entity.Product;
import be.technobel.api.models.entity.Store;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class StoreDTO {

    private Long id;
    private String name;
    private String address;
    private LocalTime opensAt;
    private LocalTime closesAt;
    private Set<ProductDTO> products;

    public static StoreDTO toDto(Store entity){

        if( entity == null )
            return null;

        return StoreDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .opensAt(entity.getOpensAt())
                .closesAt(entity.getClosesAt())
                .products(
                        entity.getAvailableProducts().stream()
                                .map(ProductDTO::toDto)
                                .collect(Collectors.toSet())
                )
                .build();

    }

}
