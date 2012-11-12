package org.pan.voucher.rest.json;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class CustomJacksonDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser paramJsonParser,DeserializationContext paramDeserializationContext) throws IOException, JsonProcessingException {
		
		String dateString = paramJsonParser.getText();
		Long longString = Long.valueOf(dateString);
		
		return new Date(longString);
	}

}
