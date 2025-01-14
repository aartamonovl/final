package webmarket.endpoints;

import webmarket.entities.Product;
import webmarket.services.ProductService;
import webmarket.soap.products.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * The endpoint of the SOAP Web service for getting a category by name.
 */
@Endpoint
@RequiredArgsConstructor
public class ProductsEndpoint {
    private static final String NAMESPACE_URI = "http://www.shev.com/spring/ws/products";
    private final ProductService productService;

    /**
     * Getting the product by name.
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByNameRequest")
    @ResponsePayload
    public GetProductByNameResponse getProductByName(@RequestPayload GetProductByNameRequest request) {
        GetProductByNameResponse response = new GetProductByNameResponse();
        Product product = productService.getByName(request.getName());
        ProductSoap productSoap = new ProductSoap();
        productSoap.setId(product.getId());
        productSoap.setName(product.getName());
        productSoap.setPrice(product.getPrice());
        response.setProduct(productSoap);
        return response;
    }

    /**
     * Getting a list of products.
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllStudents(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        List<Product> listProducts = productService.findAll();
        for(Product product: listProducts){
            ProductSoap productSoap = new ProductSoap();
            productSoap.setId(product.getId());
            productSoap.setName(product.getName());
            productSoap.setPrice(product.getPrice());
            response.getProducts().add(productSoap);
        }
        return response;
    }
}
