package org.pan.voucher.rest.json;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.springframework.stereotype.Component;

/**
 * 	Json object mapper
 * 
 * @author Pance.Isajeski
 */
@Component
public class JacksonObjectMapper extends ObjectMapper {

	@SuppressWarnings("deprecation")
	public JacksonObjectMapper() {
		
		final AnnotationIntrospector introspector = new JacksonAnnotationIntrospector();
		super.getDeserializationConfig().setAnnotationIntrospector(introspector);
		super.getSerializationConfig().setAnnotationIntrospector(introspector);
		super.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
	}

} 
