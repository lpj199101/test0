package cn.tedu.sp11.fallback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import cn.tedu.web.util.JsonResult;

@Component
public class OrderFallback implements FallbackProvider{
	//设置哪个服务,应用当前类种的降级代码
	@Override
	public String getRoute() {
		return "order-service";
		//return "*";  //所有服务都应用当前降级类
		//return null; //所有服务都应用当前降级类
	}

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		return response();
	}

	private ClientHttpResponse response() {
		return new ClientHttpResponse() {
			@Override
			public HttpHeaders getHeaders() {
				// Content-Type: application/json
				HttpHeaders h = new HttpHeaders();
				h.setContentType(MediaType.APPLICATION_JSON);
				return h;
			}
			@Override
			public InputStream getBody() throws IOException {
				// {code=401, msg="访问商品服务失败", data:null}
				String json = JsonResult.err("访问订单服务失败").toString();
				return new ByteArrayInputStream(json.getBytes("UTF-8"));
			}
			@Override
			public String getStatusText() throws IOException {
				return HttpStatus.OK.getReasonPhrase();
			}
			@Override
			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.OK;
			}
			@Override
			public int getRawStatusCode() throws IOException {
				return HttpStatus.OK.value();
			}
			@Override
			public void close() {
			}
		};
	}
}





