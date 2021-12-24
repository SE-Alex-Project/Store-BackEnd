package Software.storeBackEnd.controller;

import Software.storeBackEnd.database.ProductDataBase;
import Software.storeBackEnd.entities.Product;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

    ProductDataBase productDataBase = new ProductDataBase();

    /*product json format
   {
   "name": "name",
   "price":"12.5",
   "category" : "product category",
   "description" : "hello products",
   "stores": ["ID:1","2","2","4"],
   "images": ["product image 1 (main image)", "product image 2" , "product image 3"]
   }
    */
    @PostMapping("/add")
    public String addProduct(@RequestBody JSONObject product){
        Product p =new Product(product);
        System.out.println(p);
        productDataBase.addProduct(p);
        return "true";
    }

    /*
    return product json object
     */
    @GetMapping("/get")
    public JSONObject getProduct(@RequestBody String product_id){
        return productDataBase.getProduct(product_id);
    }

    /*
        {"product" : [product 1 , product 2 , product 3]}
     */
    @PostMapping("/product_list")
    public JSONArray getProductList(@RequestBody int page){
        System.out.println(page);
        return productDataBase.getlist(page);
    }



    /*
    {
    "page" : "1",
    "category" : "category name"
    }
     */
    @PostMapping("/product_list_category")
    public JSONArray getCategoryList(@RequestBody JSONObject productCategory){
        return productDataBase.getListByCategory(Integer.parseInt(productCategory.getAsString("page")),productCategory.getAsString("category"));
    }

    /*
    * {"product_id":[id1 ,id2 ,id3,.....]}
    * */
    @SuppressWarnings("unchecked")
	@DeleteMapping("/delete")
    public void deleteProduct(@RequestBody JSONObject product_ids){
        ArrayList<String> products = (ArrayList<String>) product_ids.get("product_id");
        for (String s : products){
            productDataBase.deleteProduct(s);
        }
    }


}
