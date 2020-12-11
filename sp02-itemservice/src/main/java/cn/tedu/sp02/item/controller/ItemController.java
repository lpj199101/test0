package cn.tedu.sp02.item.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.service.ItemService;
import cn.tedu.web.util.JsonResult;
import lombok.extern.slf4j.Slf4j;

@RestController //@Controller + @ResponseBody
@Slf4j
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@Value("${server.port}")
	private int port;
	
	/*http协议
	 * 协议头: asdsadsf 
	 * 协议体：parm1=1&param2=2&param3=3   名值对
	 * 
	 * 接收： void f(Strig parm1,int parm2,String parm3)
	 */	
	@GetMapping("/{orderId}") //@RequestMapping(path="/{orderId}", method=GET)
	public JsonResult<List<Item>> getItems(@PathVariable String orderId) throws Exception {
		log.info("port="+port+", orderId="+orderId);
				
		 ///--设置随机延迟
		if(Math.random()<0.6) { 
			long t = new Random().nextInt(5000);
			log.info("item-service-"+port+" - 暂停 "+t);
			Thread.sleep(t);
		}
		
		List<Item> list = itemService.getItems(orderId);
		JsonResult<List<Item>> r = JsonResult.ok(list).msg("port="+port);
		return r;
	}
	
	/*http协议
	 * 协议头: asdsadsf 
	 * 协议体：[{"id":1,"name":LPJ},{},{}]
	 * 
	 * 接收：    @RequestBody 
	 */	
	@PostMapping("/decreaseNumber") //相当于@RequestMapping(path="/{decreaseNumber}", method=POST)     
	public JsonResult decreaseNumber(@RequestBody List<Item> items) {
		log.info("执行减少商品库存");
		itemService.decreaseNumbers(items);
		return JsonResult.ok();
	}
}








