package webmarket.endpoints;

import webmarket.entities.Category;
import webmarket.services.CategoryService;
import webmarket.soap.categories.CategorySoap;
import webmarket.soap.categories.GetCategoryByTitleRequest;
import webmarket.soap.categories.GetCategoryByTitleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * The endpoint of the SOAP Web service for getting a category by name.
 */
@Endpoint
@RequiredArgsConstructor
public class CategoryEndpoint {
    private static final String NAMESPACE_URI = "http://www.shev.com/spring/ws/categories";
    private final CategoryService categoryService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCategoryByTitleRequest")
    @ResponsePayload
    @Transactional
    public GetCategoryByTitleResponse getCategoryByTitle(@RequestPayload GetCategoryByTitleRequest request) {
        GetCategoryByTitleResponse response = new GetCategoryByTitleResponse();
        CategorySoap categorySoap = new CategorySoap();
        Category category = categoryService.getCategoryByName(request.getName());
        categorySoap.setName(category.getName());
        response.setCategory(categorySoap);
        return response;
    }
}
