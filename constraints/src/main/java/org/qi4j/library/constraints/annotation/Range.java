package org.qi4j.library.constraints.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.qi4j.api.constraint.ConstraintDeclaration;
import org.qi4j.api.constraint.Constraints;
import org.qi4j.library.constraints.RangeConstraint;

@ConstraintDeclaration
@Retention( RetentionPolicy.RUNTIME )
@Constraints( RangeConstraint.class )
public @interface Range
{
    double min();

    double max();
}
