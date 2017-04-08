package dev.game.spacechaos.engine.entity.annotation;

import dev.game.spacechaos.engine.entity.IComponent;

/**
 * Created by Justin on 08.04.2017.
 */
public @interface RequiredComponents {

    Class<? extends IComponent>[] components() default {};

}
