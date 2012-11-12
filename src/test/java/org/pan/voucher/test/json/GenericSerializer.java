package org.pan.voucher.test.json;

import org.codehaus.jackson.schema.JsonSchema;
import org.codehaus.jackson.type.TypeReference;

/**
 * 
 * @author Pance.Isajeski
 *
 */
public interface GenericSerializer {

    public String toJson(Object object);
    
	public <T> T fromJson(String serialization, Class<T> clazz);
	
	public <T> T fromJson(String serialization, TypeReference<T> typeRef);
	
	public <T> JsonSchema getJsonSchema(Class<T> clazz);

}
