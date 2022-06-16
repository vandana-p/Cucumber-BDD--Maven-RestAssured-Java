package utilities;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.MultiPartSpecification;

public class RequestFilter extends RequestLoggingFilter {
	private static Logger logger = LoggerFactory.getLogger(RequestFilter.class);

	public Response filter(FilterableRequestSpecification filterableRequestSpecification,

            FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {
		
			 String uri = filterableRequestSpecification.getURI();
		
			 logger.info("Http Method:-" +filterableRequestSpecification.getMethod());
		     
		     List<MultiPartSpecification> multiPartParams = filterableRequestSpecification.getMultiPartParams();
		
		     return filterContext.next(filterableRequestSpecification, filterableResponseSpecification);

}

}
