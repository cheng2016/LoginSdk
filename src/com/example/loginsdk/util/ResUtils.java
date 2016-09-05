package com.example.loginsdk.util;

/** 
 * 根据资源的名字获取其ID值 
 * @author mining 
 * 
 */  
public class ResUtils {
	private static String packageName;

    public ResUtils() {
    }

    public static void setPkgName(String var0) {
        L.e("set package name :" + var0);
        if(packageName == null) {
        	packageName = var0;
        }
    }

    public static String getPkgName() {
        return packageName;
    }

    public static int getValue(String var0, String var1) {
        return c(var0, var1);
    }

    public static int getId(String var0) {
        return c("id", var0);
    }

    public static int getLayout(String var0) {
        return c("layout", var0);
    }

    public static int getString(String var0) {
        return c("string", var0);
    }

    public static int getDrawable(String var0) {
        return c("drawable", var0);
    }

    public static int getColor(String var0) {
        return c("color", var0);
    }

    public static int getStyle(String var0) {
        return c("style", var0);
    }

    private static int c(String var0, String var1) {
        try {
            Class r = null;
            r = Class.forName(packageName + ".R");
            Class[] classes = r.getClasses();
            Class desireClass = null;

            for (int i = 0; i < classes.length; ++i) {
                if (classes[i].getName().split("\\$")[1].equals(var0)) {
                    desireClass = classes[i];
                    break;
                }
            }
            return desireClass.getField(var1).getInt(desireClass);

//            return Class.forName(packageName + ".R" + var0).getField(var1).getInt(var1);
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
        } catch (NoSuchFieldException var3) {
            var3.printStackTrace();
        } catch (IllegalAccessException var4) {
            var4.printStackTrace();
        } catch (IllegalArgumentException var5) {
            var5.printStackTrace();
        }

        return 0;
    }
}