package am.itspace.eshop.service;

import am.itspace.eshop.entity.Product;
import am.itspace.eshop.entity.ProductPicture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductPictureService {

    ProductPicture save(ProductPicture productPicture);

    void saveAll(Product product, List<MultipartFile> multipartFiles);

    List<ProductPicture> findAllByProduct(Product product);
}
