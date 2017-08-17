package io.Emerald.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Header annotation used to identify plugin jars.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginMeta {

    // The name of the plugin.
    String pluginName() default "";
    // The authors of the plugin.
    String[] authors() default "";
    // The version of the plugin.
    String version() default "";
    // The plugin's ID. (this should be handled automatically somehow)
    String pluginID() default "";
    // The base level permission identifier this plugin will use. (ie. for the permission "test.hello.world" the identifier is "test")
    String permissionIdentifier() default "";
    // Determines if this plugin requires a connected database to function.
    boolean requireDatabase() default false;
}
