package Software.storeBackEnd.controller;

import Software.storeBackEnd.database.ProductDataBase;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/product")
public class ProductController {

    ProductDataBase productDataBase = new ProductDataBase();

    /*product json format
   {
   "name": product name,
   "price": product price,
   "category" : product category
   "images": [product image 1 (main image), product image 2 , product image 3]
   }
    */
    @PostMapping("/add")
    public String addProduct(@RequestBody JSONObject product){
        //System.out.println(logInJson.get("password"));
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
    @GetMapping("/product_list")
    public JSONObject getProductList(@RequestBody String list_num){
        System.out.println(list_num);
        return null;
    }

    /*
    * {"product_id":[id1 ,id2 ,id3,.....]}
    * */
    @DeleteMapping("/delete")
    public void deleteProduct(@RequestBody JSONObject product_ids){
        ArrayList<String> products = (ArrayList<String>) product_ids.get("product_id");
        for (String s : products){
            productDataBase.deleteProduct(s);
        }
    }


}
