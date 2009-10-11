/*
 * Copyright (c) 2009, Rickard Öberg. All Rights Reserved.
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

package org.qi4j.runtime.unitofwork;

import static org.junit.Assert.*;
import org.junit.Test;
import org.qi4j.api.common.UseDefaults;
import static org.qi4j.api.common.Visibility.*;
import org.qi4j.api.entity.EntityComposite;
import org.qi4j.api.entity.association.Association;
import org.qi4j.api.entity.association.ManyAssociation;
import org.qi4j.api.injection.scope.Service;
import org.qi4j.api.injection.scope.Structure;
import org.qi4j.api.mixin.Mixins;
import org.qi4j.api.property.Property;
import org.qi4j.api.service.ServiceComposite;
import org.qi4j.api.structure.Module;
import org.qi4j.api.unitofwork.EntityTypeNotFoundException;
import org.qi4j.api.unitofwork.UnitOfWork;
import org.qi4j.api.unitofwork.UnitOfWorkFactory;
import org.qi4j.bootstrap.ApplicationAssembler;
import org.qi4j.bootstrap.ApplicationAssembly;
import org.qi4j.bootstrap.ApplicationAssemblyFactory;
import org.qi4j.bootstrap.Assembler;
import org.qi4j.bootstrap.AssemblyException;
import org.qi4j.bootstrap.Energy4Java;
import org.qi4j.bootstrap.ModuleAssembly;
import org.qi4j.spi.structure.ApplicationSPI;
import org.qi4j.test.EntityTestAssembler;

/**
 * JAVADOC
 */
public class PrivateEntityUnitOfWorkTest
{
    @Service
    ProductRepositoryService repo;

    @Test
    public void givenAppWithPrivateEntityWhenUnitOfWorkCanSeeItThenCanCommit()
        throws Exception
    {
        System.setProperty( "qi4j.compacttrace", "off" );

        Energy4Java is = new Energy4Java();
        ApplicationSPI app = is.newApplication( new ApplicationAssembler()
        {
            public ApplicationAssembly assemble( ApplicationAssemblyFactory applicationFactory )
                throws AssemblyException
            {
                return applicationFactory.newApplicationAssembly( new Assembler[][][]{
                    {
                        {
                            new Assembler()
                            {
                                public void assemble( ModuleAssembly module )
                                    throws AssemblyException
                                {
                                    module.addObjects( PrivateEntityUnitOfWorkTest.class );
                                }
                            }
                        },
                        {
                            new Assembler()
                            {
                                public void assemble( ModuleAssembly module )
                                    throws AssemblyException
                                {
                                    module.addEntities( ProductEntity.class );
                                    module.addServices( ProductRepositoryService.class ).visibleIn( layer );
                                    new EntityTestAssembler().assemble( module );
                                }
                            }
                        }
                    }
                } );
            }
        } );
        app.activate();

        Module module = app.findModule( "Layer 1", "Module 1" );
        module.objectBuilderFactory().newObjectBuilder( PrivateEntityUnitOfWorkTest.class ).injectTo( this );

        UnitOfWork unitOfWork = module.unitOfWorkFactory().newUnitOfWork();

        try
        {
            unitOfWork.newEntity( ProductEntity.class );
            fail( "Should not be able to create product here" );
        }
        catch( EntityTypeNotFoundException e )
        {
            // Ok
        }

        String id;
        try
        {
            id = repo.newProduct().identity().get();
            unitOfWork.complete();
        }
        catch( Exception e )
        {
            unitOfWork.discard();
            throw e;
        }

        unitOfWork = module.unitOfWorkFactory().newUnitOfWork();
        try
        {
            ProductEntity product = repo.findProduct( id );
            product.price().set( 100 );
            unitOfWork.complete();
        }
        catch( Exception e )
        {
            unitOfWork.discard();
            throw e;
        }
    }

    @Mixins( ProductRepositoryService.ProductRepositoryMixin.class )
    interface ProductRepositoryService
        extends ServiceComposite
    {
        ProductEntity newProduct();

        ProductEntity findProduct( String id );

        abstract class ProductRepositoryMixin
            implements ProductRepositoryService
        {
            @Structure
            UnitOfWorkFactory uowf;

            public ProductEntity newProduct()
            {
                UnitOfWork uow = uowf.currentUnitOfWork();
                return uow.newEntity( ProductEntity.class );
            }

            public ProductEntity findProduct( String id )
            {
                UnitOfWork uow = uowf.currentUnitOfWork();
                return uow.get( ProductEntity.class, id );
            }
        }
    }

    @Mixins( { AccountMixin.class } )
    public interface AccountComposite
        extends Account, EntityComposite
    {
    }

    public interface Account
    {
        Property<Integer> balance();

        void add( int amount );

        void remove( int amount );
    }

    public static abstract class AccountMixin
        implements Account
    {
        public void add( int amount )
        {
            balance().set( balance().get() + amount );
        }

        public void remove( int amount )
        {
            balance().set( balance().get() - amount );
        }
    }

    public interface Customer
    {
        Association<Account> account();

        Property<String> name();
    }

    public interface CustomerComposite
        extends Customer, EntityComposite
    {
    }

    public interface LineItem
    {
        Association<Product> product();
    }

    public interface LineItemComposite
        extends LineItem, EntityComposite
    {
    }

    public interface Name
        extends Property<String>
    {
    }

    public interface Order
    {
        Association<Customer> customer();

        ManyAssociation<LineItem> lineItems();
    }

    public interface OrderComposite
        extends Order, EntityComposite
    {
    }

    public interface Product
    {
        @UseDefaults
        Property<String> name();

        @UseDefaults
        Property<Integer> price();
    }

    public interface ProductEntity
        extends Product, EntityComposite
    {
    }
}