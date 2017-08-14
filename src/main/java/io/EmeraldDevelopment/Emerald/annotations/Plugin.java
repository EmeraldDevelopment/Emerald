package io.EmeraldDevelopment.Emerald.annotations;

/**
 * Header annotation used to identify plugin jars.
 */
public @interface Plugin {

    // The name of the plugin.
    String pluginName();
    // The authors of the plugin.
    String[] authors() default "";
    // The version of the plugin.
    String version() default "";
    // The plugin's ID. (this should be handled automatically somehow)
    String pluginID();
    // The base level permission identifier this plugin will use. (ie. for the permission "test.hello.world" the identifier is "test")
    String permissionIdentifier() default "";
    // Determines if this plugin requires a connected database to function.
    boolean requireDatabase() default false;
}
