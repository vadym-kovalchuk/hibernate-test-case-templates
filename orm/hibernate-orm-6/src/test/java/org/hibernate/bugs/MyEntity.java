package org.hibernate.bugs;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  @Convert(converter = MyJsonFieldConverter.class)
  private MyJsonField myJsonField;

  public MyJsonField getMyJsonField() {
    return myJsonField;
  }

  public void setMyJsonField(MyJsonField myJsonField) {
    this.myJsonField = myJsonField;
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
