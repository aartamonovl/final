package webmarket.controllers;

import webmarket.converters.CategoryConverter;
import webmarket.entities.Category;
import webmarket.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webmarket.core.CategoryDto;

import java.util.List;

/**
 * A controller for working with categories.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@Tag(name = "Categories", description = "Methods of working with product categories")
public class CategoriesController {
    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;

    /**
     * Request for a list of product categories.
     * http://localhost:5555/core/api/v1/categories
     * @return List
     */
    @GetMapping
    @Operation(
            summary = "Request for a list of product categories",
            responses = {
                    @ApiResponse(
                            description = "Successful response", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class))
                                    )
                    )
            }
    )
    public List<CategoryDto> allCategories(){
        List<Category> listCategory = categoryService.getAllCategories();
        List<CategoryDto> listCategoryDto = categoryConverter.entityToDto(listCategory);
        return listCategoryDto;
    }

}
