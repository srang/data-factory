package com.github.srang.datafactory.resources;

/*-
 * #%L
 * Data Factory
 * %%
 * Copyright (C) 2017 srang
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.Objects;

public class LastObject {
  String other;
  String blah;
  Integer id;

  public String getOther() {
    return other;
  }

  public void setOther(String other) {
    this.other = other;
  }

  public String getBlah() {
    return blah;
  }

  public void setBlah(String blah) {
    this.blah = blah;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "LastObject{" +
        "other='" + other + '\'' +
        ", blah='" + blah + '\'' +
        ", id=" + id +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LastObject that = (LastObject) o;
    return Objects.equals(other, that.other) &&
        Objects.equals(blah, that.blah) &&
        Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(other, blah, id);
  }
}
