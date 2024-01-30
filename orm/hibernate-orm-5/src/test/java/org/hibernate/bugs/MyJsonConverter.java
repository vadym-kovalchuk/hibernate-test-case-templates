package org.hibernate.bugs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.AttributeConverter;

/**
 * @author Vadym Kovalchuk
 * @since 2024/01/30
 */
public class MyJsonConverter implements AttributeConverter<MyJsonField, String> {

  @Override
  public String convertToDatabaseColumn(MyJsonField myJsonField) {
    try {
      return new ObjectMapper().writeValueAsString(myJsonField);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public MyJsonField convertToEntityAttribute(String dbValue) {
    try {
      return new ObjectMapper().readValue(dbValue, MyJsonField.class);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
