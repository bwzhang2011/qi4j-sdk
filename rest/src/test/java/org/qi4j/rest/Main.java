/*
 * Copyright (c) 2008, Rickard Öberg. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.qi4j.rest;

import org.qi4j.api.common.Visibility;
import org.qi4j.api.structure.Application;
import org.qi4j.bootstrap.Assembler;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.Energy4Java;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.bootstrap.ModuleName;
import org.qi4j.library.http.JettyServiceAssembler;
import org.qi4j.library.rdf.entity.EntityStateSerializer;
import org.qi4j.library.rdf.entity.EntityTypeSerializer;
import org.qi4j.rest.assembly.RestAssembler;
import org.qi4j.spi.structure.ApplicationSPI;

/**
 * JAVADOC
 */
public class Main
{
    private ApplicationSPI application;

    public static void main( String[] args )
        throws Exception
    {
        new Main();
    }

    public Main() throws Exception
    {
        Energy4Java qi4j = new Energy4Java();
        qi4j.newApplication( new MainAssembler());
        application = qi4j.newApplication( new MainAssembler());
        application.activate();
    }

    public ApplicationSPI application()
    {
        return application;
    }
}

class RDFAssembler
    implements Assembler
{
    public void assemble( ModuleAssembly module ) throws AssemblyException
    {
        module.addObjects( EntityStateSerializer.class ).visibleIn( Visibility.layer );
        module.addObjects( EntityTypeSerializer.class ).visibleIn( Visibility.layer );
    }
}