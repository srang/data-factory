package com.github.srang.datafactory;

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

/**
 * <p>MalformedFilterException class.</p>
 *
 * @author srang
 */
public class MalformedFilterException extends Exception {
  /**
   * <p>Constructor for MalformedFilterException.</p>
   *
   * @param message a {@link java.lang.String} object.
   */
  public MalformedFilterException(String message) {
    super(message);
  }
  /**
   * <p>Constructor for MalformedFilterException.</p>
   *
   * @param message a {@link java.lang.String} object.
   * @param ex a {@link java.lang.Exception} object.
   */
  public MalformedFilterException(String message, Exception ex) {
    super(message,ex);
  }
}
