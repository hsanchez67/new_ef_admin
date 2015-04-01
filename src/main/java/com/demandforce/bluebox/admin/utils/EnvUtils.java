package com.demandforce.bluebox.admin.utils;

import org.apache.commons.lang.StringUtils;

import com.demandforce.bluebox.admin.config.AdminConfig;

public class EnvUtils {
    private static final String environment = PropertiesUtils.ENV.getProperty("env");
    private static Env env;
    private static final String PROTOCOL = "https://";
    private static final String PROTOCOL2 = "http://";    
    
    public static boolean isLocal() {
        if (StringUtils.equalsIgnoreCase(environment, "AdminPortal_Local")) {
            return true;
        }
        return false;
    }

    public static boolean isDev() {
        if (StringUtils.equalsIgnoreCase(environment, "AdminPortal_Dev")) {
            return true;
        }
        return false;
    }

    public static boolean isStaging() {
        if (StringUtils.equalsIgnoreCase(environment, "AdminPortal_Staging")) {
            return true;
        }
        return false;
    }

    public static boolean isE2eStg(){
        if(StringUtils.equalsIgnoreCase(environment,"AdminPortal_E2ESTG")){
            return true;
        }
        return false;
    }

    public static boolean isProduction(){
        if(StringUtils.equalsIgnoreCase(environment,"AdminPortal_Production")){
            return true;
        }
        return false;
    }
    public static Env currentEnv() {
        if (env == null) {
            if (isDev()) {
                env = Env.DEV;
            } else if (isLocal()) {
            	env = Env.LOCAL;
            } else if (isStaging()) {            
                env = Env.STAGING;
            } else if(isE2eStg()) {
                env = Env.E2ESTG;
            } else{
                env=env.PRODUCTION;
            }
        }
        return env;
    }

    public enum Env {
    	LOCAL {
            @Override
            public String getTitle() {
                return "LOCAL";
            }

            @Override
            public String getD3Location() {
                return PROTOCOL2 + AdminConfig.getInstance().get("env.bp.dev.local");
            }

        },
        DEV {
            @Override
            public String getTitle() {
                return "DEV";
            }

            @Override
            public String getD3Location() {
                return PROTOCOL + AdminConfig.getInstance().get("env.bp.dev.url");
            }

        },
        STAGING {
            @Override
            public String getTitle() {
                return "STAGING";
            }

            @Override
            public String getD3Location() {
                return PROTOCOL + AdminConfig.getInstance().get("env.bp.staging.url");
            }
        },
        E2ESTG{
            @Override
            public String getTitle() {
                return "E2ESTG";
            }

            @Override
            public String getD3Location() {
                return PROTOCOL + AdminConfig.getInstance().get("env.bp.e2tstg.url");
            }
        },
        PRODUCTION {
            @Override
            public String getTitle() {
                return "PRODUCTION";
            }

            @Override
            public String getD3Location() {
                return PROTOCOL + "www.demandforced3.com";
            }
        };

        public abstract String getTitle();

        public abstract String getD3Location();

        @Override
        public String toString() {
            return getTitle();
        }
    }
}
