package cn.tedu.sp06.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RibbonController {
	@Autowired
	private RestTemplate rt;

	@GetMapping("/item-service/{orderId}")
	public JsonResult<List<Item>> getItems(@PathVariable String orderId) {
		//使用 RestTemplate 调用后台的商品服务
		//return rt.getForObject("http://localhost:8001/{1}", JsonResult.class, orderId);
		
		
		//这里服务器路径用 service-id 代替，ribbon 会向服务的多台集群服务器分发请求
		return rt.getForObject("http://item-service/{1}", JsonResult.class, orderId);
	}

	@PostMapping("/item-service/decreaseNumber")
	public JsonResult decreaseNumber(@RequestBody List<Item> items) {
		//return rt.postForObject("http://localhost:8001/decreaseNumber", items, JsonResult.class);
		return rt.postForObject("http://item-service/decreaseNumber", items, JsonResult.class);
	}

	/////////////////////////////////////////

	@GetMapping("/user-service/{userId}")
	public JsonResult<User> getUser(@PathVariable Integer userId) {
		//return rt.getForObject("http://localhost:8101/{1}", JsonResult.class, userId);
		return rt.getForObject("http://user-service/{1}", JsonResult.class, userId);
	}

	@GetMapping("/user-service/{userId}/score") 
	public JsonResult addScore(@PathVariable Integer userId, Integer score) {
		//return rt.getForObject("http://localhost:8101/{1}/score?score={2}", JsonResult.class, userId, score);
		return rt.getForObject("http://user-service/{1}/score?score={2}", JsonResult.class, userId, score);
	}

	/////////////////////////////////////////

	@GetMapping("/order-service/{orderId}")
	public JsonResult<Order> getOrder(@PathVariable String orderId) {
		//return rt.getForObject("http://localhost:8201/{1}", JsonResult.class, orderId);
		return rt.getForObject("http://order-service/{1}", JsonResult.class, orderId);
	}

	@GetMapping("/order-service")
	public JsonResult addOrder() {
		//return rt.getForObject("http://localhost:8201/", JsonResult.class);
		return rt.getForObject("http://order-service/", JsonResult.class);
	}
}









