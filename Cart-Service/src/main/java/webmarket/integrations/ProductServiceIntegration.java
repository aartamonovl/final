package webmarket.integrations;

import lombok.Data;
import org.springframework.stereotype.Component;
import webmarket.core.ProductDto;

import java.util.Optional;

/**
 * Responsible for integration with the Core service.
 */
@Data
@Component
public class ProductServiceIntegration {

    /**
     * Feign Client Product Service.
     */
    private final ClientFeignProductService productService;

    /**
     * Sends a request to the Core service requests the product by its Id.
     * @param id
     * @return
     */
    public Optional <ProductDto> findById(Long id){
        ProductDto productDto = productService.findById(id);
        return Optional.of(productDto);
    }
}
