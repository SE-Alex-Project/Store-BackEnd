package Software.storeBackEnd.controller;

import Software.storeBackEnd.authentication.Authentication;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
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
        System.out.println(product_id);
        return null;
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
    public void deleteProduct(@RequestBody JSONObject product_id){

    }


}
