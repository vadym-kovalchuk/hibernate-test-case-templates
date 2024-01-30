package org.hibernate.bugs;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author Vadym Kovalchuk
 * @since 2024/01/30
 */
@Entity
public class MyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Convert(converter = MyJsonConverter.class)
  private MyJsonField myJsonField;

  public MyJsonField getMyJsonField() {
    return myJsonField;
  }

  public void setMyJsonField(MyJsonField myJson) {
    this.myJsonField = myJson;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MyEntity myEntity = (MyEntity) o;
    return Objects.equals(myJsonField, myEntity.myJsonField);
  }

  @Override
  public int hashCode() {
    return Objects.hash(myJsonField);
  }

  @Override
  public String toString() {
    return "MyEntity{" +
        "id=" + id +
        ", myJsonField=" + myJsonField +
        '}';
  }
}
