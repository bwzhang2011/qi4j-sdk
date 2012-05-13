/*
 * Copyright (c) 2011, Rickard Öberg. All Rights Reserved.
 * Copyright (c) 2012, Paul Merlin. All Rights Reserved.
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
package org.qi4j.library.sql.assembly;

import java.net.ConnectException;

import javax.sql.DataSource;

import org.qi4j.api.service.importer.ServiceInstanceImporter;
import org.qi4j.bootstrap.Assembler;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.ModuleAssembly;
import static org.qi4j.functional.Specifications.not;
import org.qi4j.library.circuitbreaker.CircuitBreaker;
import static org.qi4j.library.circuitbreaker.CircuitBreakers.in;
import static org.qi4j.library.circuitbreaker.CircuitBreakers.rootCause;

/**
 * Use this Assembler to register a javax.sql.DataSource.
 */
public class DataSourceAssembler
        implements Assembler
{

    private String dataSourceServiceId;

    private String dataSourceId;

    public DataSourceAssembler( String dataSourceServiceId, String dataSourceId )
    {
        this.dataSourceServiceId = dataSourceServiceId;
        this.dataSourceId = dataSourceId;
    }

    @Override
    public void assemble( ModuleAssembly module )
            throws AssemblyException
    {
        module.importedServices( DataSource.class ).
                importedBy( ServiceInstanceImporter.class ).
                setMetaInfo( dataSourceServiceId ).
                setMetaInfo( new CircuitBreaker( 5, 1000 * 60 * 5, not( rootCause( in( ConnectException.class ) ) ) ) ).
                identifiedBy( dataSourceId );
    }

}