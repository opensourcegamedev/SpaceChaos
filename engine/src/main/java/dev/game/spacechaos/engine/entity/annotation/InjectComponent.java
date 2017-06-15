package dev.game.spacechaos.engine.entity.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import dev.game.spacechaos.engine.entity.Entity;
import dev.game.spacechaos.engine.entity.EntityManager;
import dev.game.spacechaos.engine.entity.IComponent;
import dev.game.spacechaos.engine.exception.RequiredComponentNotFoundException;
import dev.game.spacechaos.engine.game.BaseGame;

/**
 * Identifies injectable {@linkplain IComponent component-fields} and classes
 * with those fields.
 * <p>
 * 
 * @see Entity#init(BaseGame, EntityManager)
 */
@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InjectComponent {

    /**
     * @return Indicates whether an annotated field/the field of the annotated
     *         type can be null. If set to true, an
     *         {@linkplain RequiredComponentNotFoundException} is thrown.
     *         Default value: true.
     */
    boolean nullable() default true;

    /**
     * @return Indicates whether the inherited fields of the annotated type
     *         should also get injected. Default value: true.
     */
    boolean injectInherited() default true;

}
