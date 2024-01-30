package org.hibernate.bugs;

import java.util.Objects;

/**
 * @author Vadym Kovalchuk
 * @since 2024/01/30
 */
public class MyJsonField {
  private String field1;

  public String getField1() {
    return field1;
  }

  public void setField1(String field1) {
    this.field1 = field1;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MyJsonField myJson = (MyJsonField) o;
    return Objects.equals(field1, myJson.field1);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field1);
  }

  @Override
  public String toString() {
    return "MyJson{" +
        "field1='" + field1 + '\'' +
        '}';
  }
}
