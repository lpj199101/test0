package cn.tedu.sp11.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.constants.ZuulConstants;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import cn.tedu.web.util.JsonResult;

@Component
public class AccessFilter extends ZuulFilter {
	//判断针对当前这次请求,是否执行当前过滤代码
	@Override
	public boolean shouldFilter() {
		//只针对商品的访问进行过滤,用户和订单都不过滤
		//判断当前请求的服务id, 是不是 "item-service"
		RequestContext ctx = RequestContext.getCurrentContext();
		String id = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);//当前请求的服务id
		
		return id.equals("item-service");		
	}
	//过滤代码,在这里写权限判断代码
	//当前zuul版本中,返回值没有启用
	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String token = request.getParameter("token");
		if (token == null || token.trim().length()==0) {
			//设置阻止当前请求继续访问
			ctx.setSendZuulResponse(false);
			
			//设置响应数据, 降级响应
			ctx.setResponseStatusCode(200);
			ctx.setResponseBody(JsonResult.err("not login").code(JsonResult.NOT_LOGIN).toString());
		}
		
		return null;
	}
	//过滤器的类型:前置,后置,路由,错误
	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}
	//过滤器的顺序号
	@Override
	public int filterOrder() {
		/*
		 * zuul默认过滤器中,第5个过滤器,在context对象中,添加了 service-id
		 */
		return 6;
	}
}







