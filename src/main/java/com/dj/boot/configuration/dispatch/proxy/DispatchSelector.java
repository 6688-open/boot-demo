package com.dj.boot.configuration.dispatch.proxy;
/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.configuration.dispatch.proxy
 * @Date: 2020-07-17-10-41
 */
public class DispatchSelector {

    private static InheritableThreadLocal<String> SELECTOR;
    static {
        SELECTOR  = new InheritableThreadLocal<String>();
    }

    public static String selected() {
        return SELECTOR.get();
    }

    public static void select(String val) {
        SELECTOR.set(val);
    }

    public static void clear() {
        SELECTOR.remove();
    }

}
