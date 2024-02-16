package am.itspace.eshop.controller;

import am.itspace.eshop.entity.Product;
import am.itspace.eshop.service.CategoryService;
import am.itspace.eshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products/{id}")
    public String productSinglePage(@PathVariable("id") int id, ModelMap modelMap){
        Product byId = productService.findById(id);
        if(byId == null){
            return "redirect:/";
        }
        modelMap.addAttribute("product", byId);
        modelMap.addAttribute("categories", categoryService.findAll());
        return "user/singleProduct";
    }
}
