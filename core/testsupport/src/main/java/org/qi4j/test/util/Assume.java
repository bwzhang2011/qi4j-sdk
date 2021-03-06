/*
 * Copyright 2014 Paul Merlin.
 *
 * Licensed  under the  Apache License,  Version 2.0  (the "License");
 * you may not use  this file  except in  compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed  under the  License is distributed on an "AS IS" BASIS,
 * WITHOUT  WARRANTIES OR CONDITIONS  OF ANY KIND, either  express  or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.test.util;

import java.awt.GraphicsEnvironment;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * A set of methods useful for stating assumptions about the conditions in which a test is meaningful.
 */
public class Assume
    extends org.junit.Assume
{

    /**
     * If called on a IBM JDK, the test will halt and be ignored.
     */
    public static void assumeNoIbmJdk()
    {
        assumeFalse( System.getProperty( "java.vendor" ).contains( "IBM" ) );
    }

    /**
     * If called on a headless runtime, the test will halt and be ignored.
     */
    public static void assumeDisplayPresent()
    {
        assumeFalse( GraphicsEnvironment.getLocalGraphicsEnvironment().isHeadlessInstance() );
        String display = System.getenv( "DISPLAY" );
        assumeThat( display, is( notNullValue() ) );
        assumeTrue( display.length() > 0 );
    }

}
